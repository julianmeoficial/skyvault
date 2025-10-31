package com.skyvault.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class RangeValidator implements ConstraintValidator<ValidRange, Object> {

    private String minField;
    private String maxField;

    @Override
    public void initialize(ValidRange constraintAnnotation) {
        this.minField = constraintAnnotation.minField();
        this.maxField = constraintAnnotation.maxField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapper wrapper = new BeanWrapperImpl(value);

        Object minValue = wrapper.getPropertyValue(minField);
        Object maxValue = wrapper.getPropertyValue(maxField);

        // Si cualquiera de los valores es null, la validación pasa
        if (minValue == null || maxValue == null) {
            return true;
        }

        // Comparar valores si ambos son Comparable
        if (minValue instanceof Comparable && maxValue instanceof Comparable) {
            @SuppressWarnings("unchecked")
            Comparable<Object> min = (Comparable<Object>) minValue;

            return min.compareTo(maxValue) <= 0;
        }

        return true;
    }
}
