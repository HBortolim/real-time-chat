package com.henrique.hbortolim.constants;

public final class SecurityConstants {
    
    private SecurityConstants() {
        // Utility class
    }
    
    public static final class JWT {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds
        public static final long REFRESH_EXPIRATION_TIME = 604800000; // 7 days in milliseconds
        
        private JWT() {}
    }
    
    public static final class Roles {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String MODERATOR = "MODERATOR";
        
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
        
        private Roles() {}
    }
    
    public static final class Permissions {
        public static final String READ = "READ";
        public static final String WRITE = "WRITE";
        public static final String DELETE = "DELETE";
        public static final String ADMIN = "ADMIN";
        
        private Permissions() {}
    }
    
    public static final class PublicEndpoints {
        public static final String[] AUTH_WHITELIST = {
            "/api/auth/**",
            "/ws/**",
            "/app/**",
            "/topic/**",
            "/actuator/health",
            "/swagger-ui/**",
            "/v3/api-docs/**"
        };
        
        private PublicEndpoints() {}
    }
} 