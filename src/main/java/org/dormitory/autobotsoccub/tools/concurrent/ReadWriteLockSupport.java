package org.dormitory.autobotsoccub.tools.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ReadWriteLockSupport implements LockSupport {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    @Override
    public <T> T callWithReadLock(Supplier<T> callable) {
        return callWithLock(readLock, callable);
    }

    @Override
    public void execWithWriteLock(Runnable action) {
        execWithLock(writeLock, action);
    }

    @Override
    public <T> T callWithWriteLock(Supplier<T> callable) {
        return callWithLock(writeLock, callable);
    }

    private void execWithLock(Lock lock, Runnable action) {
        lock.lock();
        try {
            action.run();
        } finally {
            lock.unlock();
        }
    }

    private <T> T callWithLock(Lock lock, Supplier<T> resultSupplier) {
        lock.lock();
        try {
            return resultSupplier.get();
        } finally {
            lock.unlock();
        }
    }

}
