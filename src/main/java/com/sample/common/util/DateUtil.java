package com.sample.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String getFormattedString(String format) {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(format));
    }

}
