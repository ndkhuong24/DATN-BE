package com.example.backend.core.commons;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtil {

    public static Date formatDate(Date date){
        long startTimeMillis = date.getTime();
        Instant instant = Instant.ofEpochMilli(startTimeMillis);
        LocalDateTime newDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).minusHours(7);

        return Date.from(newDateTime.atZone(ZoneOffset.UTC).toInstant());
    }
}
