package com.skyvault.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AircraftModelValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAircraftModel {

    String message() default "Aircraft model validation failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
