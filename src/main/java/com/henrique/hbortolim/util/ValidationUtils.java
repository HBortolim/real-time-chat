package com.henrique.hbortolim.util;

import com.henrique.hbortolim.constants.ApiConstants;
import java.util.regex.Pattern;

public final class ValidationUtils {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    
    private ValidationUtils() {
        // Utility class
    }
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidUsername(String username) {
        return username != null && 
               username.length() >= ApiConstants.Validation.MIN_USERNAME_LENGTH &&
               username.length() <= ApiConstants.Validation.MAX_USERNAME_LENGTH &&
               USERNAME_PATTERN.matcher(username).matches();
    }
    
    public static boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= ApiConstants.Validation.MIN_PASSWORD_LENGTH &&
               password.length() <= ApiConstants.Validation.MAX_PASSWORD_LENGTH;
    }
    
    public static boolean isValidChatName(String chatName) {
        return chatName != null && 
               !chatName.trim().isEmpty() &&
               chatName.length() <= ApiConstants.Validation.MAX_CHAT_NAME_LENGTH;
    }
} 