package com.dan.sharelib.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    public static Long getEpochMillisFromLocalDateTime(LocalDateTime localDateTime) {
        if(ObjectUtils.isNotEmpty(localDateTime)) {
            return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        }
        return null;
    }

}
