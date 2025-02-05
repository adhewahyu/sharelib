package com.dan.sharelib.util;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class CommonUtil {

    public CommonUtil(){}

    public static Date getCurrentDate() {
        return Date.from(Instant.now());
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

}
