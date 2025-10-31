package com.skyvault.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class SortFieldValidator implements ConstraintValidator<ValidSortField, String> {

    private List<String> allowedFields;
    private boolean required;

    @Override
    public void initialize(ValidSortField constraintAnnotation) {
        this.allowedFields = Arrays.asList(constraintAnnotation.allowedFields());
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si el campo no es requerido y es null/vacío, es válido
        if (!required && (value == null || value.trim().isEmpty())) {
            return true;
        }

        // Si es requerido y es null/vacío, es inválido
        if (required && (value == null || value.trim().isEmpty())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Sort field is required")
                    .addConstraintViolation();
            return false;
        }

        // Si no hay campos permitidos definidos, cualquier valor es válido
        if (allowedFields.isEmpty()) {
            return true;
        }

        // Validar que el campo esté en la lista de permitidos
        return allowedFields.contains(value);
    }
}
