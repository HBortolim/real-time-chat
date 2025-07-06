package com.henrique.hbortolim.validation.validator;

import com.henrique.hbortolim.util.ValidationUtils;
import com.henrique.hbortolim.validation.annotation.ValidUsername;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        // No additional configuration needed for username validation
    }
    
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return ValidationUtils.isValidUsername(username);
    }
} 