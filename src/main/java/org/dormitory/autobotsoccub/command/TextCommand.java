package org.dormitory.autobotsoccub.command;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Optional;
import java.util.regex.Pattern;

public interface TextCommand extends Command {

    Pattern getCommandPattern();

    @Override
    default boolean accepts(Update update) {
        return Optional.ofNullable(update.getMessage())
                .map(Message::getText)
                .filter(StringUtils::isNotBlank)
                .map(text -> getCommandPattern().matcher(text).matches())
                .orElse(false);
    }
}
