package com.hignnote.payments.enums;

public enum FileType {
    TXT,PDF,XLS,NONE;

    public static FileType byValue(String value) {
        for (FileType type : values()) {
            if (type.name().equals(value.toUpperCase())) {
                return type;
            }
        }
        return NONE;
    }
}
