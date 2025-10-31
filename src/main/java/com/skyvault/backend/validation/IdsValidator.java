package com.skyvault.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdsValidator implements ConstraintValidator<ValidIds, List<Long>> {

    private int max;
    private boolean allowEmpty;

    @Override
    public void initialize(ValidIds constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        // Si es null, let @NotNull handle it
        if (values == null) {
            return true;
        }

        // Si está vacío y no se permite, inválido
        if (values.isEmpty() && !allowEmpty) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID list cannot be empty")
                    .addConstraintViolation();
            return false;
        }

        // Si está vacío y se permite, válido
        if (values.isEmpty() && allowEmpty) {
            return true;
        }

        // Validar que no exceda el máximo
        if (values.size() > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Too many IDs. Maximum allowed: " + max)
                    .addConstraintViolation();
            return false;
        }

        // Validar que no haya IDs nulos
        if (values.contains(null)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID list cannot contain null values")
                    .addConstraintViolation();
            return false;
        }

        // Validar que no haya IDs negativos o cero
        if (values.stream().anyMatch(id -> id <= 0)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("All IDs must be positive numbers")
                    .addConstraintViolation();
            return false;
        }

        // Validar que no haya IDs duplicados
        Set<Long> uniqueIds = new HashSet<>(values);
        if (uniqueIds.size() != values.size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID list cannot contain duplicates")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
