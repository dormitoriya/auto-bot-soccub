package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.bot.SoccubBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotContext {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    @Bean
    public SoccubBot soccubBot() {
        return new SoccubBot(name, token);
    }
}
