package com.dan.sharelib.enums;

import java.util.HashMap;
import java.util.Map;

public enum Message {
    MSG_OK("0000", "Data submitted successfully"),
    DATA_FOUND("0001", "Here is your data"),
    EMAIL_REQUIRED("4000", "Email is required"),
    INVALID_SALUTATION("4001", "Invalid salutation"),
    MSG_NOK("9000","Failed to submit data"),
    DATA_NOT_FOUND("9001", "Data not found"),
    UNKNOWN_TASK("9002", "Unknown task"),
    ACTION_NOT_FOUND("9003", "Action not found"),
    MODULE_NOT_FOUND("9004", "Module not found"),
    SOMETHING_WENT_WRONG("9999", "Something went wrong");

    private final String value;
    private final String msg;
    private static final Map<String,Object> map = new HashMap();

    Message(String value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (Message message : Message.values()) {
            map.put(message.value, message);
        }
    }

    public static Message valueOf(int errorMessage) {
        return (Message) map.get(errorMessage);
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
