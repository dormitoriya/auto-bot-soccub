package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.command.keyboard.Keyboard;
import org.dormitory.autobotsoccub.command.keyboard.RegisterKeyboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyboardContext {

    @Bean
    public Keyboard registerKeyboard() {
        return new RegisterKeyboard();
    }
}
