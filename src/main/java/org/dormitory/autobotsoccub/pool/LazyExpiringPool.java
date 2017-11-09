package org.dormitory.autobotsoccub.pool;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.dormitory.autobotsoccub.tools.concurrent.LockSupport;
import org.dormitory.autobotsoccub.tools.concurrent.ReadWriteLockSupport;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.ImmutableMap.copyOf;
import static java.time.Instant.now;


@Slf4j
public class LazyExpiringPool<K, V> implements ExpiringPool<K, V> {

    private final Duration ttl;
    private final int maxSize;
    private final Map<K, V> container;
    private final LockSupport locker;

    private volatile Instant lastModification = now();

    @Builder
    public LazyExpiringPool(int maxSize, Duration ttl) {
        this.ttl = ttl;
        this.maxSize = maxSize;
        this.container = new ConcurrentHashMap<>(maxSize);
        locker = new ReadWriteLockSupport();
    }

    @Override
    public PoolView<K, V> get() {
        locker.execWithWriteLock(this::clearIfExpiredAndNotEmpty);
        return locker.callWithReadLock(this::internalGet);
    }

    @Override
    public PoolView<K, V> put(K key, V value) {
        return locker.callWithWriteLock(() -> {
            clearIfExpiredAndNotEmpty();
            internalPut(key, value);
            PoolView<K, V> result = internalGet();
//            clearIfFull();
            return result;
        });
    }

    @Override
    public PoolView<K, V> putIfAbsent(K key, V value) {
        return locker.callWithWriteLock(() -> {
            clearIfExpiredAndNotEmpty();
            internalPutIfAbsent(key, value);
            PoolView<K, V> result = internalGet();
//            clearIfFull();
            return result;
        });
    }

    @Override
    public PoolView<K, V> remove(K key) {
        locker.execWithWriteLock(() -> {
            clearIfExpiredAndNotEmpty();
            internalRemove(key);
        });
        return locker.callWithReadLock(this::internalGet);
    }

    private void internalRemove(K key) {
        container.remove(key);
    }

    private void clearIfFull() {
        if (full()) {
            log.debug("Clearing full pool:{}", container);
            container.clear();
            lastModification = now();
        }
    }

    private void internalPut(K key, V value) {
        container.put(key, value);
        lastModification = now();
    }

    private void internalPutIfAbsent(K key, V value) {
        if (!container.containsKey(key)) {
            container.put(key, value);
            lastModification = now();
        }
    }

    private PoolView<K, V> internalGet() {
        return PoolView.<K, V>builder()
                .pool(copyOf(container))
                .maxSize(maxSize)
                .expiresAt(expiresAt())
                .build();
    }

    private void clearIfExpiredAndNotEmpty() {
        if (notEmpty() && expired()) {
            log.debug("Clearing expired pool:{}", container);
            container.clear();
            lastModification = now();
        }
    }

    private Instant expiresAt() {
        return lastModification.plus(ttl);
    }

    private boolean notEmpty() {
        return !container.isEmpty();
    }

    private boolean full() {
        return container.size() == maxSize;
    }

    private boolean expired() {
        return lastModification.plus(ttl).isBefore(now());
    }
}
