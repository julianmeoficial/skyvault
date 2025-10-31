package com.skyvault.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaginationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPagination {

    String message() default "Invalid pagination parameters";

    int maxPageSize() default 100;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
