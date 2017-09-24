package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.bot.SoccubBot;
import org.dormitory.autobotsoccub.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotContext {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    @Autowired
    private List<Command> commands;

    @Bean
    public SoccubBot soccubBot() {
        return new SoccubBot(name, token, commands);
    }
}
