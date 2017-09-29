package org.dormitory.autobotsoccub.exception;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class UnknownButtonException extends RuntimeException {

    private static final String MESSAGE = "Unknown button with text:[%s] and callbackQuery:[%s]";

    public UnknownButtonException(String text, String callbackQuery) {
        super(String.format(MESSAGE, defaultString(text), defaultString(callbackQuery)));
    }
}
