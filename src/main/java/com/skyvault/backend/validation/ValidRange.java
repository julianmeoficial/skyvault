package com.skyvault.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RangeValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidRange.List.class)
public @interface ValidRange {

    String message() default "Invalid range: minimum value cannot be greater than maximum value";

    String minField() default "min";

    String maxField() default "max";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ValidRange[] value();
    }
}
