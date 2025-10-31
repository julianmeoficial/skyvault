package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.request.CompareRequestDto;
import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.model.AircraftModel;
import org.mapstruct.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {AircraftMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE // ⭐ IGNORAR UNMAPPED
)
public interface ComparisonMapper {

    @Mapping(target = "aircraft", source = "aircraftList")
    @Mapping(target = "totalAircraft", source = "aircraftList", qualifiedByName = "getListSize")
    @Mapping(target = "comparedAt", expression = "java(getCurrentTimestamp())")
    @Mapping(target = "config", source = "request")
    @Mapping(target = "comparisonTable", ignore = true)
    @Mapping(target = "summary", ignore = true)
    CompareResultDto createCompareResult(List<AircraftModel> aircraftList, CompareRequestDto request);

    /**
     * Mapear configuración de comparación
     */
    ComparisonConfigDto toConfigDto(CompareRequestDto request);

    /**
     * Crear valor de comparación
     */
    @Mapping(target = "isHighest", ignore = true)
    @Mapping(target = "isLowest", ignore = true)
    @Mapping(target = "relativePosition", ignore = true)
    ComparisonValueDto createComparisonValue(Long aircraftId, String originalValue, String displayValue);

    /**
     * Método personalizado para obtener tamaño de lista
     */
    @Named("getListSize")
    default Integer getListSize(List<?> list) {
        return list != null ? list.size() : 0;
    }

    /**
     * Método personalizado para timestamp actual
     */
    default String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Post-procesamiento para configuración
     */
    @AfterMapping
    default void afterMappingConfig(@MappingTarget ComparisonConfigDto dto, CompareRequestDto request) {
        // Asegurar valores por defecto
        if (dto.getIncludeSpecifications() == null) {
            dto.setIncludeSpecifications(true);
        }
        if (dto.getIncludeImages() == null) {
            dto.setIncludeImages(true);
        }
        if (dto.getNormalizeUnits() == null) {
            dto.setNormalizeUnits(true);
        }
        if (dto.getUnitFormat() == null) {
            dto.setUnitFormat("metric");
        }
    }
}
