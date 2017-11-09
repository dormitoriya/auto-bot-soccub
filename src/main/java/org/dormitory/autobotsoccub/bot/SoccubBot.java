package org.dormitory.autobotsoccub.bot;

import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dormitory.autobotsoccub.bot.chat.ChatData;
import org.dormitory.autobotsoccub.bot.chat.ChatThread;
import org.dormitory.autobotsoccub.command.Command;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.dormitory.autobotsoccub.utils.Utils.*;

@Slf4j
@AllArgsConstructor
public class SoccubBot extends TelegramLongPollingBot {

    @Getter private static final EventBus eventBus = new EventBus();
    private static Map<Long, ChatThread> chats = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private String name;
    private String token;
    private List<Command> commands;

    @PostConstruct
    public void init() {
        commands.forEach(eventBus::register);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Got update: {}", update);

        ChatThread chatThread = findOrCreateProcessor(update);
        chatThread.updateChatData(update);
        executorService.submit(chatThread);
    }

    private ChatThread findOrCreateProcessor(Update update) {
        return isGroupChat(update)
                ? findOrCreateProcessorForGroupChat(getChatId(update))
                : findOrCreateProcessorForUserChat(update);
    }

    private ChatThread findOrCreateProcessorForGroupChat(Long chatId) {
        return chats.containsKey(chatId)
                ? chats.get(chatId)
                : createAndPutChatProcessor(chatId);
    }

    private ChatThread findOrCreateProcessorForUserChat(Update update) {
        return chats.values().stream()
                .filter(processor -> processor.getChatData().containsUserFromRegisterPoolByUpdate(update))
                .findFirst()
                .orElse(null); //TODO throw new notSupportedCommandException();
    }

    private ChatThread createAndPutChatProcessor(Long chatId) {
        ChatThread chatThread = ChatThread.builder()
                .chatData(new ChatData(chatId))
                .build();

        chats.put(chatId, chatThread);
        return chatThread;
    }
}
