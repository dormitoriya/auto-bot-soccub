package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dormitory.autobotsoccub.exception.UnknownButtonException;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Button {

    REGISTER("Register", "btn_register"),
    UNREGISTER("Unregister", "btn_unregister"),
    START("StartGame", "btn_start"),
    STOP("StopGame", "btn_stop"),
    GOAL("Goal", "btn_goal"),
    AUTO_GOAL("Auto Goal", "btn_auto_goal"),
    SUBSTITUTION("Substitution", "btn_substitution"),
    REVERT("Revert", "btn_revert");

    private String text;
    private String callBackQuery;

    public static Button of(String text, String callBackQuery) {
        return Arrays.stream(values())
                .filter(btn -> buttonDataContains(btn, text, callBackQuery))
                .findFirst()
                .orElseThrow(() -> new UnknownButtonException(text, callBackQuery));
    }

    private static boolean buttonDataContains(Button btn, String text, String callBackQuery) {
        return btn.text.equals(text) && btn.callBackQuery.equals(callBackQuery);
    }
}
