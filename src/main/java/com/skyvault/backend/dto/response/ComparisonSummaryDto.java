package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resumen estadístico de la comparación")
public class ComparisonSummaryDto {

    @Schema(description = "Aeronave con mayor capacidad de pasajeros")
    private AircraftSummaryDto largestCapacity;

    @Schema(description = "Aeronave con mayor alcance")
    private AircraftSummaryDto longestRange;

    @Schema(description = "Aeronave más rápida")
    private AircraftSummaryDto fastest;

    @Schema(description = "Aeronave más eficiente en combustible")
    private AircraftSummaryDto mostFuelEfficient;

    @Schema(description = "Aeronave más nueva")
    private AircraftSummaryDto newest;

    @Schema(description = "Aeronave más antigua")
    private AircraftSummaryDto oldest;

    @Schema(description = "Promedio de pasajeros", example = "185.5")
    private Double averagePassengers;

    @Schema(description = "Promedio de alcance en km", example = "6245.2")
    private Double averageRange;

    @Schema(description = "Promedio de velocidad en nudos", example = "456.7")
    private Double averageSpeed;

    @Schema(description = "Diferencia máxima en pasajeros", example = "195")
    private Integer passengerSpread;

    @Schema(description = "Diferencia máxima en alcance", example = "8500")
    private Integer rangeSpread;

    // Constructors
    public ComparisonSummaryDto() {}

    // Getters and Setters
    public AircraftSummaryDto getLargestCapacity() {
        return largestCapacity;
    }

    public void setLargestCapacity(AircraftSummaryDto largestCapacity) {
        this.largestCapacity = largestCapacity;
    }

    public AircraftSummaryDto getLongestRange() {
        return longestRange;
    }

    public void setLongestRange(AircraftSummaryDto longestRange) {
        this.longestRange = longestRange;
    }

    public AircraftSummaryDto getFastest() {
        return fastest;
    }

    public void setFastest(AircraftSummaryDto fastest) {
        this.fastest = fastest;
    }

    public AircraftSummaryDto getMostFuelEfficient() {
        return mostFuelEfficient;
    }

    public void setMostFuelEfficient(AircraftSummaryDto mostFuelEfficient) {
        this.mostFuelEfficient = mostFuelEfficient;
    }

    public AircraftSummaryDto getNewest() {
        return newest;
    }

    public void setNewest(AircraftSummaryDto newest) {
        this.newest = newest;
    }

    public AircraftSummaryDto getOldest() {
        return oldest;
    }

    public void setOldest(AircraftSummaryDto oldest) {
        this.oldest = oldest;
    }

    public Double getAveragePassengers() {
        return averagePassengers;
    }

    public void setAveragePassengers(Double averagePassengers) {
        this.averagePassengers = averagePassengers;
    }

    public Double getAverageRange() {
        return averageRange;
    }

    public void setAverageRange(Double averageRange) {
        this.averageRange = averageRange;
    }

    public Double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Integer getPassengerSpread() {
        return passengerSpread;
    }

    public void setPassengerSpread(Integer passengerSpread) {
        this.passengerSpread = passengerSpread;
    }

    public Integer getRangeSpread() {
        return rangeSpread;
    }

    public void setRangeSpread(Integer rangeSpread) {
        this.rangeSpread = rangeSpread;
    }
}
