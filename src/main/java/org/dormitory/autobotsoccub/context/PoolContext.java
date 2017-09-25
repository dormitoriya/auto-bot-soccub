package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.user.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.api.objects.User;

import java.util.Map;

@Configuration
public class PoolContext {

    @Value("${expirationTime ?: 15}")
    private int expirationTime;

    private Map<Integer, User> userPool;

    @Bean
    public UserPool userPool() {
        return new UserPool(expirationTime, userPool);
    }
}