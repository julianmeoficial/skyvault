package com.skyvault.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Parámetros para búsqueda de aeronaves similares")
public class SimilarAircraftRequestDto {

    @NotNull(message = "Aircraft ID is required")
    @Schema(description = "ID de la aeronave de referencia",
            example = "15",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long aircraftId;

    @Min(value = 1, message = "Passenger tolerance must be at least 1")
    @Max(value = 500, message = "Passenger tolerance cannot exceed 500")
    @Schema(description = "Tolerancia en número de pasajeros",
            example = "50",
            defaultValue = "50")
    private Integer passengerTolerance = 50;

    @Min(value = 100, message = "Range tolerance must be at least 100")
    @Max(value = 5000, message = "Range tolerance cannot exceed 5000")
    @Schema(description = "Tolerancia en alcance (km)",
            example = "1000",
            defaultValue = "1000")
    private Integer rangeTolerance = 1000;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 20, message = "Limit cannot exceed 20")
    @Schema(description = "Número máximo de aeronaves similares",
            example = "5",
            defaultValue = "5")
    private Integer limit = 5;

    @Schema(description = "Solo aeronaves activas",
            example = "true",
            defaultValue = "true")
    private Boolean onlyActive = true;

    // Constructors
    public SimilarAircraftRequestDto() {}

    public SimilarAircraftRequestDto(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    // Getters and Setters
    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    public Integer getPassengerTolerance() {
        return passengerTolerance;
    }

    public void setPassengerTolerance(Integer passengerTolerance) {
        this.passengerTolerance = passengerTolerance;
    }

    public Integer getRangeTolerance() {
        return rangeTolerance;
    }

    public void setRangeTolerance(Integer rangeTolerance) {
        this.rangeTolerance = rangeTolerance;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(Boolean onlyActive) {
        this.onlyActive = onlyActive;
    }
}
