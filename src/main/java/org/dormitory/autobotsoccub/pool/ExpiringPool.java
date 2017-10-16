package org.dormitory.autobotsoccub.pool;

public interface ExpiringPool<K, V> {

    PoolView<K, V> get();

    PoolView<K, V> put(K key, V value);

    PoolView<K, V> remove(K key);
}
