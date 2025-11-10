package com.poc.ex.validation.annotation;

import com.poc.ex.validation.annotation.validator.BirthDateBeforeHireDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateBeforeHireDateValidator.class)
@Documented
public @interface BirthDateBeforeHireDate {
    String message() default "Birth date must be before hire date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
