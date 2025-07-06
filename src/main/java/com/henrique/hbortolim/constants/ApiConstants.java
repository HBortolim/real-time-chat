package com.henrique.hbortolim.constants;

public final class ApiConstants {
    
    private ApiConstants() {
        // Utility class
    }
    
    public static final class Endpoints {
        public static final String API_PREFIX = "/api";
        public static final String AUTH_PREFIX = API_PREFIX + "/auth";
        public static final String CHAT_PREFIX = API_PREFIX + "/chats";
        public static final String USER_PREFIX = API_PREFIX + "/users";
        
        private Endpoints() {}
    }
    
    public static final class Messages {
        public static final String USER_NOT_FOUND = "User not found";
        public static final String CHAT_NOT_FOUND = "Chat not found";
        public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
        public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
        
        private Messages() {}
    }
    
    public static final class Validation {
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final int MAX_PASSWORD_LENGTH = 50;
        public static final int MIN_USERNAME_LENGTH = 3;
        public static final int MAX_USERNAME_LENGTH = 20;
        public static final int MAX_CHAT_NAME_LENGTH = 100;
        
        private Validation() {}
    }
} 