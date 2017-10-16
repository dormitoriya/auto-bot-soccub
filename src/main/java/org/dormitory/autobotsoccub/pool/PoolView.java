package org.dormitory.autobotsoccub.pool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;


@Builder
@FieldDefaults(makeFinal=true, level= PRIVATE)
@AllArgsConstructor
public class PoolView<T, V> {
    int maxSize;
    @Getter
    Map<T, V> pool;
    @Getter
    Instant expiresAt;

    public boolean isEmpty() {
        return pool.isEmpty();
    }

    public boolean isFull() {
        return pool.size() == maxSize;
    }
}
