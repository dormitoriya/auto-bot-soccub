package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.command.Command;
import org.dormitory.autobotsoccub.command.keyboard.KeyboardFactory;
import org.dormitory.autobotsoccub.command.keyboard.ButtonFactory;
import org.dormitory.autobotsoccub.command.querycommand.gameprocess.*;
import org.dormitory.autobotsoccub.command.querycommand.managementgame.RegisterCommand;
import org.dormitory.autobotsoccub.command.querycommand.managementgame.UnregisterCommand;
import org.dormitory.autobotsoccub.command.querycommand.txtcommand.HelloCommand;
import org.dormitory.autobotsoccub.command.querycommand.txtcommand.ShowPoolCommand;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
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
    public Command helloCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToGeneralChatProcessor) {
        return new HelloCommand(keyboardFactory, sendToGeneralChatProcessor);
    }

    @Bean
    public Command showPoolCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToGeneralChatProcessor) {
        return new ShowPoolCommand(keyboardFactory, sendToGeneralChatProcessor);
    }

    @Bean
    public Command startGameCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToPrivateChatsProcessor) {
        return new StartGameCommand(keyboardFactory, sendToPrivateChatsProcessor);
    }

    @Bean
    public Command stopGameCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToAllChatsProcessor) {
        return new StopGameCommand(keyboardFactory, sendToAllChatsProcessor);
    }

    @Bean
    public Command scoreCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToPrivateChatsProcessor) {
        return new ScoreCommand(keyboardFactory, sendToPrivateChatsProcessor);
    }

    @Bean
    public Command autoScoreCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToPrivateChatsProcessor) {
        return new AutoScoreCommand(keyboardFactory, sendToPrivateChatsProcessor);
    }

    @Bean
    public Command changePositionsInTeamCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToPrivateChatsProcessor) {
        return new ChangePositionsInTeamCommand(keyboardFactory, sendToPrivateChatsProcessor);
    }

    @Bean
    public Command revertCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToPrivateChatsProcessor) {
        return new RevertCommand(keyboardFactory, sendToPrivateChatsProcessor);
    }

    @Bean
    public Command registerCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToGeneralChatProcessor) {
        return new RegisterCommand(keyboardFactory, sendToGeneralChatProcessor);
    }

    @Bean
    public Command unregisterCommand(KeyboardFactory keyboardFactory, SendingStrategy sendToGeneralChatProcessor) {
        return new UnregisterCommand(keyboardFactory, sendToGeneralChatProcessor);
    }
}