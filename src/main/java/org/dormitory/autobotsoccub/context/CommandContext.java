package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.keyboard.ButtonFactory;
import org.dormitory.autobotsoccub.command.querycommand.RegisterCommand;
import org.dormitory.autobotsoccub.command.txtcommand.HelloCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandContext {

    @Bean
    public ButtonFactory buttonFactory() {
        return new ButtonFactory();
    }

    @Bean
    public KeyboardFactory keyboardFactory(ButtonFactory buttonFactory) {
        return new KeyboardFactory(buttonFactory);
    }

    @Bean
    public Command helloCommand(KeyboardFactory keyboardFactory) {
        return new HelloCommand(keyboardFactory);
    }

    @Bean
    public Command registerCommand(KeyboardFactory keyboardFactory) {
        return new RegisterCommand(keyboardFactory);
    }
}
