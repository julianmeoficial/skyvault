package com.skyvault.backend.validation;

import com.skyvault.backend.dto.request.AircraftCreateDto;
import com.skyvault.backend.dto.request.AircraftUpdateDto;
import com.skyvault.backend.util.NumberUtils;
import com.skyvault.backend.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AircraftModelValidator implements ConstraintValidator<ValidAircraftModel, Object> {

    @Override
    public void initialize(ValidAircraftModel constraintAnnotation) {
        // Inicialización si es necesaria
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull maneja nulls
        }

        context.disableDefaultConstraintViolation();
        boolean isValid = true;

        // CORREGIR: Usar los DTOs correctos
        if (value instanceof AircraftCreateDto createDto) {
            isValid = validateAircraftModel(createDto, context);
        } else if (value instanceof AircraftUpdateDto updateDto) {
            isValid = validateAircraftModel(updateDto, context);
        }

        return isValid;
    }

    private boolean validateAircraftModel(Object dto, ConstraintValidatorContext context) {
        boolean isValid = true;

        // Extraer campos comunes
        String name = null;
        String model = null;
        Integer typicalPassengers = null;
        Integer maxPassengers = null;
        Integer rangeKm = null;
        Integer cruiseSpeedKnots = null;
        Integer introductionYear = null;

        // CORREGIR: Usar los DTOs correctos
        if (dto instanceof AircraftCreateDto createDto) {
            name = createDto.getName();
            model = createDto.getModel();
            typicalPassengers = createDto.getTypicalPassengers();
            maxPassengers = createDto.getMaxPassengers();
            rangeKm = createDto.getRangeKm();
            cruiseSpeedKnots = createDto.getCruiseSpeedKnots();
            introductionYear = createDto.getIntroductionYear();
        } else if (dto instanceof AircraftUpdateDto updateDto) {
            name = updateDto.getName();
            model = updateDto.getModel();
            typicalPassengers = updateDto.getTypicalPassengers();
            maxPassengers = updateDto.getMaxPassengers();
            rangeKm = updateDto.getRangeKm();
            cruiseSpeedKnots = updateDto.getCruiseSpeedKnots();
            introductionYear = updateDto.getIntroductionYear();
        }

        // Validar que name y model no sean iguales
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(model) && name.equals(model)) {
            context.buildConstraintViolationWithTemplate("Name and model cannot be identical")
                    .addPropertyNode("model")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validar que typical passengers <= max passengers
        if (typicalPassengers != null && maxPassengers != null && typicalPassengers > maxPassengers) {
            context.buildConstraintViolationWithTemplate("Typical passengers cannot exceed maximum passengers")
                    .addPropertyNode("typicalPassengers")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validar rangos realistas
        if (rangeKm != null && (rangeKm < 100 || rangeKm > 50000)) {
            context.buildConstraintViolationWithTemplate("Range must be between 100 and 50,000 km")
                    .addPropertyNode("rangeKm")
                    .addConstraintViolation();
            isValid = false;
        }

        if (cruiseSpeedKnots != null && (cruiseSpeedKnots < 100 || cruiseSpeedKnots > 1000)) {
            context.buildConstraintViolationWithTemplate("Cruise speed must be between 100 and 1,000 knots")
                    .addPropertyNode("cruiseSpeedKnots")
                    .addConstraintViolation();
            isValid = false;
        }

        if (maxPassengers != null && (maxPassengers < 1 || maxPassengers > 1000)) {
            context.buildConstraintViolationWithTemplate("Maximum passengers must be between 1 and 1,000")
                    .addPropertyNode("maxPassengers")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validar año de introducción
        int currentYear = java.time.Year.now().getValue();
        if (introductionYear != null && (introductionYear < 1900 || introductionYear > currentYear + 5)) {
            context.buildConstraintViolationWithTemplate("Introduction year must be between 1900 and " + (currentYear + 5))
                    .addPropertyNode("introductionYear")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
