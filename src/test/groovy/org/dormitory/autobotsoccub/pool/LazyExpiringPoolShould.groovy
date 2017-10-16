package org.dormitory.autobotsoccub.pool

import org.telegram.telegrambots.api.objects.User
import spock.lang.Specification

import java.time.Duration
import java.time.Instant
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger

import static java.util.concurrent.Executors.newFixedThreadPool
import static org.dormitory.autobotsoccub.util.MeasurablePollingConditions.with
import static org.dormitory.autobotsoccub.util.TestDataProvider.buildUser
import static org.dormitory.autobotsoccub.util.TestTimeUtils.isNotAfter
import static org.dormitory.autobotsoccub.util.TestTimeUtils.isNotBefore

class LazyExpiringPoolShould extends Specification {

    static MAX_POOL_SIZE = 4
    static POOL_TTL = Duration.ofSeconds(5)
    static RANDOM = new Random()
    static USER_ID_COUNTER = new AtomicInteger()

    LazyExpiringPool<Integer, User> pool

    def setup() {
        pool = LazyExpiringPool.<Integer, User>builder()
                .maxSize(MAX_POOL_SIZE)
                .ttl(POOL_TTL)
                .build()
    }

    def 'return correct pool view after putting an item'() {
        when:
        def instantBeforePut = Instant.now()
        def poolView = buildUserAndPutToPool(1)
        def instantAfterPut = Instant.now()

        then:
        poolContainsUserIds(poolView, [1])
        !poolView.full
        !poolView.empty
        isNotBefore(poolView.expiresAt, instantBeforePut + POOL_TTL)
        isNotAfter(poolView.expiresAt, instantAfterPut + POOL_TTL)
    }

    def 'correctly remove from pool without updating expiration'() {
        given:
        buildUserAndPutToPool(1)
        buildUserAndPutToPool(2)
        def instantBeforeLastPut = Instant.now()
        buildUserAndPutToPool(3)
        def instantAfterLastPut = Instant.now()

        when:
        def poolViewAfterRemoval = pool.remove(2)

        then:
        poolContainsUserIds(poolViewAfterRemoval, [1, 3])
        isNotBefore(poolViewAfterRemoval.expiresAt, instantBeforeLastPut + POOL_TTL)
        isNotAfter(poolViewAfterRemoval.expiresAt, instantAfterLastPut + POOL_TTL)
    }

    def 'not update expiration after reading from pool'() {
        given:
        buildUserAndPutToPool()

        when:
        sleep(delay.getSeconds() * 1000)

        and:
        pool.get()

        then:
        with([timeout: timeout]).eventuallyTakes(expectedResultingDuration, {
            assert pool.get().empty
        })

        where:
        delay = POOL_TTL.dividedBy(2)
        timeout = POOL_TTL.getSeconds()
        expectedResultingDuration = POOL_TTL - delay
    }

    def 'put to pool until it clears'() {
        when:
        def poolViewAfterPutFirst = buildUserAndPutToPool(1)
        def poolViewAfterPutSecond = buildUserAndPutToPool(2)
        def poolViewAfterPutThird = buildUserAndPutToPool(3)
        def poolViewAfterPutLast = buildUserAndPutToPool(4)
        def poolViewAfterPutExtra = buildUserAndPutToPool(5)

        then:
        poolContainsUserIds(poolViewAfterPutFirst, [1])
        poolContainsUserIds(poolViewAfterPutSecond, [1, 2])
        poolContainsUserIds(poolViewAfterPutThird, [1, 2, 3])
        poolContainsUserIds(poolViewAfterPutLast, [1, 2, 3, 4])

        and:
        poolContainsUserIds(poolViewAfterPutExtra, [5])
    }

    def 'correctly expire pool'() {
        when:
        buildUserAndPutToPool()
        buildUserAndPutToPool()

        then:
        with([timeout: timeout]).eventuallyTakes(POOL_TTL, {
            assert pool.get().empty
        })

        where:
        timeout = POOL_TTL.getSeconds()
    }

    def 'not allow to complete expired pool'() {
        given:
        1.upto(MAX_POOL_SIZE - 1) {
            buildUserAndPutToPool()
        }
        assert !pool.get().full

        when:
        waitForPoolExpiration()

        then:
        def poolViewAfterPutAfterExpired = buildUserAndPutToPool(userId)
        poolContainsUserIds(poolViewAfterPutAfterExpired, [userId])

        where:
        userId = 42
    }

    def 'correctly fill pool from multiple threads'() {
        when:
        def results = (1..concurrentRequests)
            .collect({ threadPool.submit({ -> buildUserAndPutToPool()} as Callable) })
            .collect { it.get() }

        then:
        results.size() == concurrentRequests
        results.count { it.full } == expectedFullPoolCount

        when: def lastPool = pool.get()
        then: !lastPool.full && lastPool.pool.size() == notFullPoolSize

        cleanup:
        threadPool.shutdown()

        where:
        expectedFullPoolCount = 5
        notFullPoolSize = RANDOM.nextInt(MAX_POOL_SIZE)
        concurrentRequests = expectedFullPoolCount * MAX_POOL_SIZE + notFullPoolSize
        threadPoolSize = 10
        threadPool = newFixedThreadPool(threadPoolSize)
    }

    static boolean poolContainsUserIds(PoolView<Integer, User> poolView, Collection<Integer> userIds) {
        assert poolView.pool.size() == userIds.size()
        poolView.pool.keySet().containsAll(userIds)
    }

    static void waitForPoolExpiration() {
        sleep(POOL_TTL.getSeconds() * 1000)
    }

    PoolView<Integer, User> buildUserAndPutToPool(int id = USER_ID_COUNTER.incrementAndGet()) {
        def user = buildUser([id: id])
        pool.put(user.id, user)
    }
}
