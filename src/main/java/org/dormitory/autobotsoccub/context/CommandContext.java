package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.Keyboard;
import org.dormitory.autobotsoccub.command.querycommand.RegisterCommand;
import org.dormitory.autobotsoccub.command.txtcommand.HelloCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandContext {

    @Bean
    public Command helloCommand(Keyboard registerKeyboard) {
        return new HelloCommand(registerKeyboard);
    }

    @Bean
    public Command registerCommand(Keyboard registerKeyboard) {
        return new RegisterCommand(registerKeyboard);
    }
}
