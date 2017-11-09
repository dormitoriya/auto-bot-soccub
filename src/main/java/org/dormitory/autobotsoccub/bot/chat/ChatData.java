package org.dormitory.autobotsoccub.bot.chat;

import lombok.Data;
import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.dormitory.autobotsoccub.engine.GameEngine;
import org.dormitory.autobotsoccub.engine.GameEngineOperations;
import org.dormitory.autobotsoccub.pool.ExpiringPool;
import org.dormitory.autobotsoccub.pool.LazyExpiringPool;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.*;

@Data
public class ChatData {

    private final Long chatId;
    private User user;
    private String commandName;
    private CommandResult commandResult;
    private ExpiringPool<Integer, User> registerExpiringUserPool;
    private Map<Integer, User> gameProcessUserPool;
    private GameEngineOperations gameEngineOperations;

    public ChatData(Long chatId) {
        this.chatId = chatId;
        registerExpiringUserPool = new LazyExpiringPool<>(2, Duration.ofMinutes(15));
        gameProcessUserPool = new ConcurrentHashMap<>();
        gameEngineOperations = new GameEngine();
    }

    public void putUserToRegisterPool(User user) {
        registerExpiringUserPool.put(user.getId(), user);
    }

    public void removeUserFromRegisterPool(Integer userId) {
        registerExpiringUserPool.remove(userId);
    }

    public boolean containsUserFromRegisterPoolByUpdate(Update update) {
        return registerExpiringUserPool.get().getPool().values().stream()
                .anyMatch(user -> user.getId().equals(getUserFromUpdate(update).getId()));
    }

    void seCommandNameAndUserFromUpdate(Update update) {
        this.user = getUserFromUpdate(update);
        this.commandName = getCommandNameFromUpdate(update);
    }

    private User getUserFromUpdate(Update update) {
        return nonNull(update.getCallbackQuery())
                ? update.getCallbackQuery().getFrom()
                : update.getMessage().getFrom();
    }

    private String getCommandNameFromUpdate(Update update) {
        return nonNull(update.getCallbackQuery())
                ? update.getCallbackQuery().getData()
                : update.getMessage().getText();
    }
}
