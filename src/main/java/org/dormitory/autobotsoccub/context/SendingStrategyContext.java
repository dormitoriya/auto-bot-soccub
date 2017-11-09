package org.dormitory.autobotsoccub.context;

import org.dormitory.autobotsoccub.sender.MessageSender;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendToAllChatsProcessor;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendToGeneralChatProcessor;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendToPrivateChatsProcessor;
import org.dormitory.autobotsoccub.sender.sendingstrategy.SendingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendingStrategyContext {

    @Bean
    public SendingStrategy sendToGeneralChatProcessor(MessageSender messageSender) {
        return new SendToGeneralChatProcessor(messageSender); }

    @Bean
    public SendingStrategy sendToAllChatsProcessor(MessageSender messageSender) {
        return new SendToAllChatsProcessor(messageSender); }

    @Bean
    public SendingStrategy sendToPrivateChatsProcessor(MessageSender messageSender) {
        return new SendToPrivateChatsProcessor(messageSender); }
}
