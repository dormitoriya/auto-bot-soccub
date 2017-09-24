package org.dormitory.autobotsoccub.command;

import org.dormitory.autobotsoccub.command.result.CommandResult;
import org.telegram.telegrambots.api.objects.Update;

public interface Command {

    boolean accepts(Update update);

    CommandResult execute(Update update);
}
