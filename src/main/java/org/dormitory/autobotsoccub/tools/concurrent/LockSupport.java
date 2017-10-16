package org.dormitory.autobotsoccub.tools.concurrent;

import java.util.function.Supplier;

public interface LockSupport {

    <T> T callWithReadLock(Supplier<T> callable);

    void execWithWriteLock(Runnable action);

    <T> T callWithWriteLock(Supplier<T> callable);
}
