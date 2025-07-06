package com.henrique.hbortolim.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MapperUtils {
    
    private MapperUtils() {
        // Utility class
    }
    
    public static <T, R> List<R> mapList(Collection<T> source, Function<T, R> mapper) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        
        return source.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
    
    public static <T, R> R mapNullSafe(T source, Function<T, R> mapper) {
        return source != null ? mapper.apply(source) : null;
    }
    
    public static <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
    
    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    public static String defaultIfBlank(String value, String defaultValue) {
        String trimmed = trimToNull(value);
        return trimmed != null ? trimmed : defaultValue;
    }
    
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
    
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    public static <T> void updateIfNotNull(T value, Runnable updater) {
        if (value != null) {
            updater.run();
        }
    }
    
    public static <T> void updateStringIfNotBlank(String value, Runnable updater) {
        if (isNotBlank(value)) {
            updater.run();
        }
    }
} 