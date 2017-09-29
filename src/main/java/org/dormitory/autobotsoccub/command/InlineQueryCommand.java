package org.dormitory.autobotsoccub.command;

import org.apache.commons.lang3.StringUtils;
import org.dormitory.autobotsoccub.command.keyboard.Button;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Optional;

public interface InlineQueryCommand extends Command {

    Button getCommandButton();

    @Override
    default boolean accepts(Update update) {
        return Optional.ofNullable(update.getCallbackQuery())
                .map(CallbackQuery::getData)
                .map(this::acceptsCallbackQuery)
                .orElse(false);
    }

    default Boolean acceptsCallbackQuery(String queryData) {
        return StringUtils.equals(queryData, getCommandButton().getCallBackQuery());
    }
}
