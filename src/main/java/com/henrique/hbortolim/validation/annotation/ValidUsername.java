package com.henrique.hbortolim.validation.annotation;

import com.henrique.hbortolim.validation.validator.UsernameValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    
    String message() default "Username must be 3-20 characters and contain only letters, numbers, and underscores";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
} 