package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.engine.GameEngine;
import org.dormitory.autobotsoccub.engine.GameEngineOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameContext {

    @Bean
    public GameEngineOperations gameEngine() {
        return new GameEngine();
    }
}
