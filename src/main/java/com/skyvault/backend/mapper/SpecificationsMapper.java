package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SpecificationsDto;
import com.skyvault.backend.model.Specifications;
import org.mapstruct.*;
import java.math.BigDecimal;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SpecificationsMapper {

    /**
     * Mapeo principal con nombre específico - COMPATIBLE CON TU DTO
     */
    @Named("toSpecificationsDto")
    @Mapping(target = "lengthM", source = ".", qualifiedByName = "formatLength")
    @Mapping(target = "wingspanM", source = ".", qualifiedByName = "formatWingspan")
    @Mapping(target = "heightM", source = ".", qualifiedByName = "formatHeight")
    @Mapping(target = "maxSpeedKmh", source = ".", qualifiedByName = "formatMaxSpeed")
    @Mapping(target = "emptyWeightKg", source = ".", qualifiedByName = "formatEmptyWeight")
    @Mapping(target = "maxTakeoffWeightKg", source = ".", qualifiedByName = "formatMaxTakeoffWeight")
    @Mapping(target = "engineCount", source = ".", qualifiedByName = "formatEngineCount")
    @Mapping(target = "fuelCapacityLiters", source = ".", qualifiedByName = "formatFuelCapacity")
    @Mapping(target = "engineThrustN", source = ".", qualifiedByName = "formatEngineThrust")
    @Mapping(target = "totalThrustN", source = ".", qualifiedByName = "formatTotalThrust")
    SpecificationsDto toDto(Specifications specifications);

    /**
     * Formateo para longitud
     */
    @Named("formatLength")
    default String formatLength(Specifications specs) {
        return specs.getLengthM() != null ?
                String.format("%.1f", specs.getLengthM().doubleValue()) : null;
    }

    /**
     * Formateo para envergadura
     */
    @Named("formatWingspan")
    default String formatWingspan(Specifications specs) {
        return specs.getWingspanM() != null ?
                String.format("%.1f", specs.getWingspanM().doubleValue()) : null;
    }

    /**
     * Formateo para altura
     */
    @Named("formatHeight")
    default String formatHeight(Specifications specs) {
        return specs.getHeightM() != null ?
                String.format("%.1f", specs.getHeightM().doubleValue()) : null;
    }

    /**
     * Formateo para velocidad máxima
     */
    @Named("formatMaxSpeed")
    default String formatMaxSpeed(Specifications specs) {
        return specs.getMaxSpeedKmh() != null ?
                String.format("%.0f", specs.getMaxSpeedKmh().doubleValue()) : null;
    }

    /**
     * Formateo para peso vacío
     */
    @Named("formatEmptyWeight")
    default String formatEmptyWeight(Specifications specs) {
        return specs.getEmptyWeightKg() != null ?
                String.format("%.0f", specs.getEmptyWeightKg().doubleValue()) : null;
    }

    /**
     * Formateo para peso máximo al despegue
     */
    @Named("formatMaxTakeoffWeight")
    default String formatMaxTakeoffWeight(Specifications specs) {
        return specs.getMaxTakeoffWeightKg() != null ?
                String.format("%.0f", specs.getMaxTakeoffWeightKg().doubleValue()) : null;
    }

    /**
     * Formateo para número de motores
     */
    @Named("formatEngineCount")
    default String formatEngineCount(Specifications specs) {
        return specs.getEngineCount() != null ?
                String.valueOf(specs.getEngineCount()) : "2"; // Default
    }

    /**
     * Formateo para capacidad de combustible
     */
    @Named("formatFuelCapacity")
    default String formatFuelCapacity(Specifications specs) {
        return specs.getFuelCapacityLiters() != null ?
                String.format("%,d", specs.getFuelCapacityLiters()) : null;
    }

    /**
     * Formateo para empuje del motor
     */
    @Named("formatEngineThrust")
    default String formatEngineThrust(Specifications specs) {
        return specs.getEngineThrustN() != null ?
                String.format("%.0f", specs.getEngineThrustN().doubleValue()) : null;
    }

    /**
     * Formateo para empuje total
     */
    @Named("formatTotalThrust")
    default String formatTotalThrust(Specifications specs) {
        return specs.getTotalThrustN() != null ?
                String.format("%.0f", specs.getTotalThrustN().doubleValue()) : null;
    }

    /**
     * Formateo para área alar
     */
    @Named("formatWingArea")
    default String formatWingArea(Specifications specs) {
        return specs.getWingAreaM2() != null ?
                String.format("%.1f", specs.getWingAreaM2().doubleValue()) : null;
    }

    /**
     * Formateo para techo de servicio
     */
    @Named("formatServiceCeiling")
    default String formatServiceCeiling(Specifications specs) {
        return specs.getServiceCeilingM() != null ?
                String.format("%,d", specs.getServiceCeilingM()) : null;
    }

    /**
     * Formateo para distancia de despegue
     */
    @Named("formatTakeoffDistance")
    default String formatTakeoffDistance(Specifications specs) {
        return specs.getTakeoffDistanceM() != null ?
                String.format("%,d", specs.getTakeoffDistanceM()) : null;
    }

    /**
     * Formateo para longitud de cabina
     */
    @Named("formatCabinLength")
    default String formatCabinLength(Specifications specs) {
        return specs.getCabinLengthM() != null ?
                String.format("%.1f", specs.getCabinLengthM().doubleValue()) : null;
    }

    /**
     * Formateo para anchura de cabina
     */
    @Named("formatCabinWidth")
    default String formatCabinWidth(Specifications specs) {
        return specs.getCabinWidthM() != null ?
                String.format("%.2f", specs.getCabinWidthM().doubleValue()) : null;
    }

    /**
     * Formateo para altura de cabina
     */
    @Named("formatCabinHeight")
    default String formatCabinHeight(Specifications specs) {
        return specs.getCabinHeightM() != null ?
                String.format("%.2f", specs.getCabinHeightM().doubleValue()) : null;
    }

    /**
     * Formateo para volumen de carga
     */
    @Named("formatCargoVolume")
    default String formatCargoVolume(Specifications specs) {
        return specs.getCargoVolumeM3() != null ?
                String.format("%.1f", specs.getCargoVolumeM3().doubleValue()) : null;
    }

    /**
     * Post-procesamiento para campos adicionales
     */
    @AfterMapping
    default void afterMapping(@MappingTarget SpecificationsDto dto, Specifications specifications) {
        // Campos directos (String → String)
        if (specifications.getEngineManufacturer() != null) {
            dto.setEngineManufacturer(specifications.getEngineManufacturer());
        }

        if (specifications.getEngineModel() != null) {
            dto.setEngineModel(specifications.getEngineModel());
        }

        if (specifications.getCertificationAuthorities() != null) {
            dto.setCertificationAuthorities(specifications.getCertificationAuthorities());
        }

        // Formateo de campos Integer/BigDecimal adicionales
        if (specifications.getFirstClassSeats() != null) {
            dto.setFirstClassSeats(String.valueOf(specifications.getFirstClassSeats()));
        }

        if (specifications.getBusinessClassSeats() != null) {
            dto.setBusinessClassSeats(String.valueOf(specifications.getBusinessClassSeats()));
        }

        if (specifications.getEconomyClassSeats() != null) {
            dto.setEconomyClassSeats(String.valueOf(specifications.getEconomyClassSeats()));
        }

        if (specifications.getRangeWithMaxPaxKm() != null) {
            dto.setRangeWithMaxPaxKm(String.format("%,d", specifications.getRangeWithMaxPaxKm()));
        }

        if (specifications.getRangeWithMaxPayloadKm() != null) {
            dto.setRangeWithMaxPayloadKm(String.format("%,d", specifications.getRangeWithMaxPayloadKm()));
        }

        if (specifications.getNoiseLevelDb() != null) {
            dto.setNoiseLevelDb(String.format("%.1f", specifications.getNoiseLevelDb().doubleValue()));
        }
    }
}
