package com.dan.sharelib.enums;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    NEW(0, "New"),
    ACTIVE(1, "Active"),
    EDIT(2, "Edit"),
    DELETED(3, "Deleted"),
    INACTIVE(4, "Inactive");

    private final Integer value;
    private final String msg;
    private static final Map<Integer,Object> map = new HashMap();

    Status(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    static {
        for (Status message : Status.values()) {
            map.put(message.value, message);
        }
    }

    public static Status valueOf(int statusValue) {
        return (Status) map.get(statusValue);
    }

    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
