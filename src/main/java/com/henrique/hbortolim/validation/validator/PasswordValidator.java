package com.henrique.hbortolim.validation.validator;

import com.henrique.hbortolim.util.ValidationUtils;
import com.henrique.hbortolim.validation.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    
    private boolean requireSpecialChar = false;
    private boolean requireUpperCase = false;
    private boolean requireLowerCase = false;
    private boolean requireDigit = false;
    
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.requireSpecialChar = constraintAnnotation.requireSpecialChar();
        this.requireUpperCase = constraintAnnotation.requireUpperCase();
        this.requireLowerCase = constraintAnnotation.requireLowerCase();
        this.requireDigit = constraintAnnotation.requireDigit();
    }
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (!ValidationUtils.isValidPassword(password)) {
            return false;
        }
        
        if (requireUpperCase && !password.chars().anyMatch(Character::isUpperCase)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter")
                .addConstraintViolation();
            return false;
        }
        
        if (requireLowerCase && !password.chars().anyMatch(Character::isLowerCase)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain at least one lowercase letter")
                .addConstraintViolation();
            return false;
        }
        
        if (requireDigit && !password.chars().anyMatch(Character::isDigit)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain at least one digit")
                .addConstraintViolation();
            return false;
        }
        
        if (requireSpecialChar && !password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(ch) >= 0)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain at least one special character")
                .addConstraintViolation();
            return false;
        }
        
        return true;
    }
} 