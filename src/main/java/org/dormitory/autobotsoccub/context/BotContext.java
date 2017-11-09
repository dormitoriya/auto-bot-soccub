package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.bot.SoccubBot;
import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.sender.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotContext {

    @Value("${bot.name}")
    private String name;

    @Value("${bot.token}")
    private String token;

    @Bean
    public SoccubBot soccubBot(List<Command> commands) {
        return new SoccubBot(name, token, commands);
    }

    @Bean
    public MessageSender messageSender(ConfigurableApplicationContext ctx) {
        return new MessageSender(ctx);
    }
}
