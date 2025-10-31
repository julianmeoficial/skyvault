package com.skyvault.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PaginationValidator implements ConstraintValidator<ValidPagination, Object> {

    private int maxPageSize;

    @Override
    public void initialize(ValidPagination constraintAnnotation) {
        this.maxPageSize = constraintAnnotation.maxPageSize();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapper wrapper = new BeanWrapperImpl(value);
        boolean isValid = true;

        // Validar page (debe ser >= 0)
        Object pageValue = wrapper.getPropertyValue("page");
        if (pageValue instanceof Integer page && page < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Page number must be >= 0")
                    .addPropertyNode("page")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validar size (debe estar entre 1 y maxPageSize)
        Object sizeValue = wrapper.getPropertyValue("size");
        if (sizeValue instanceof Integer size) {
            if (size <= 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Page size must be > 0")
                        .addPropertyNode("size")
                        .addConstraintViolation();
                isValid = false;
            } else if (size > maxPageSize) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Page size cannot exceed " + maxPageSize)
                        .addPropertyNode("size")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        return isValid;
    }
}
