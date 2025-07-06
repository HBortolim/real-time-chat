package com.henrique.hbortolim.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateUtils {
    
    public static final DateTimeFormatter API_DATE_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    
    public static final DateTimeFormatter DISPLAY_DATE_FORMAT = 
        DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    public static final DateTimeFormatter DISPLAY_DATETIME_FORMAT = 
        DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH:mm");
    
    private DateUtils() {
        // Utility class
    }
    
    public static ZonedDateTime now() {
        return ZonedDateTime.now();
    }
    
    public static String formatForApi(ZonedDateTime dateTime) {
        return dateTime != null ? dateTime.format(API_DATE_FORMAT) : null;
    }
    
    public static String formatForDisplay(ZonedDateTime dateTime) {
        return dateTime != null ? dateTime.format(DISPLAY_DATE_FORMAT) : null;
    }
    
    public static String formatForDisplayWithTime(ZonedDateTime dateTime) {
        return dateTime != null ? dateTime.format(DISPLAY_DATETIME_FORMAT) : null;
    }
    
    public static boolean isToday(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        ZonedDateTime today = ZonedDateTime.now();
        return dateTime.toLocalDate().isEqual(today.toLocalDate());
    }
    
    public static boolean isWithinLastHour(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        ZonedDateTime oneHourAgo = ZonedDateTime.now().minus(1, ChronoUnit.HOURS);
        return dateTime.isAfter(oneHourAgo);
    }
    
    public static long minutesBetween(ZonedDateTime start, ZonedDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(start, end);
    }
    
    public static long daysBetween(ZonedDateTime start, ZonedDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
    }
    
    public static String getRelativeTimeString(ZonedDateTime dateTime) {
        if (dateTime == null) {
            return "Unknown";
        }
        
        ZonedDateTime now = ZonedDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        
        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " minute" + (minutes == 1 ? "" : "s") + " ago";
        } else if (minutes < 1440) { // Less than 24 hours
            long hours = minutes / 60;
            return hours + " hour" + (hours == 1 ? "" : "s") + " ago";
        } else {
            long days = minutes / 1440;
            return days + " day" + (days == 1 ? "" : "s") + " ago";
        }
    }
} 