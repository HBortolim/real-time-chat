package com.henrique.hbortolim.validation.annotation;

import com.henrique.hbortolim.validation.validator.PasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    
    String message() default "Password must be between 8 and 50 characters";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    boolean requireSpecialChar() default false;
    
    boolean requireUpperCase() default false;
    
    boolean requireLowerCase() default false;
    
    boolean requireDigit() default false;
} 