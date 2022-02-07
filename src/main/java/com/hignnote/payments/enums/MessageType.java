package com.hignnote.payments.enums;

import lombok.AllArgsConstructor;


public enum MessageType {
    REQUEST("0100"),
    RESPONSE("0110"),
    UNKNOWN("");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static MessageType byValue(String value) {
        for (MessageType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
