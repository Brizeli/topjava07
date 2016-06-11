package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate MIN_DATE = LocalDate.of(0,1,1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000,1,1);

    public static boolean isBetween(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate parseLocalDate(String date, LocalDate def) {
        return date.isEmpty()?def:LocalDate.parse(date);
    }

    public static LocalTime parseLocalTime(String time, LocalTime def) {
        return time.isEmpty()?def:LocalTime.parse(time);
    }
}
