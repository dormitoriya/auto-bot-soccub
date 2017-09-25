package org.dormitory.autobotsoccub.user;

import org.telegram.telegrambots.api.objects.User;

import java.util.Map;
import java.util.stream.Collectors;

public class UserUtils {

    private UserUtils() {}

    public static String getPoolList(Map<Integer, User> pool) {
        return "User pool:\n" + pool.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(UserUtils::getUserName)
                .collect(Collectors.joining());
    }

    public static String getUserName(User user) {
        return user.getFirstName() + (user.getLastName() != null ? " " + user.getLastName() : "") + "\n";
    }
}