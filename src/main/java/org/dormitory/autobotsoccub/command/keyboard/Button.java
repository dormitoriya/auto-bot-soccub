package org.dormitory.autobotsoccub.command.keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Button {

    REGISTER("Register", "btn_register"),
    UNREGISTER("Unregister", "btn_unregister"),
    START("StartGame", "btn_start");

    private String text;
    private String callBackQuery;
}
