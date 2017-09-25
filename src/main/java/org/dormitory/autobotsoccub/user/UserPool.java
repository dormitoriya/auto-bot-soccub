package org.dormitory.autobotsoccub.user;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class UserPool {

    private static final int POOL_SIZE = 4;
    public static long POOL_REQUEST_TIMEOUT = TimeUnit.MINUTES.toMillis(15);
    private long lastPoolUpdate;

    @Getter private Map<Integer, User> userPool = new HashMap<>();

    public void addUserToPool(User user) {
        if (isPoolExpired()) {
            clearPool();
        }
        if (!isFullPool() && !containsUserFromPool(user.getId())) {
            userPool.put(user.getId(), user);
            lastPoolUpdate = System.currentTimeMillis();
        }
    }

    public boolean isFullPool() {
        return userPool.size() == POOL_SIZE;
    }

    public boolean containsUserFromPool(int userId) {
        return userPool.containsKey(userId);
    }

    public void removeUserFromPool(User user) {
        userPool.remove(user.getId());
    }

    public void clearPool() {
        userPool.clear();
    }

    public boolean isPoolExpired() {
        return userPool.size() > 0 && (System.currentTimeMillis() - lastPoolUpdate > POOL_REQUEST_TIMEOUT);
    }

    @Override
    public String toString() {
        return "User pool:\n" + userPool.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(this::getUserName)
                .collect(Collectors.joining());
    }

    private String getUserName(User user) {
            return user.getFirstName() + (user.getLastName() != null ? " " + user.getLastName() : "") + "\n";
    }
}