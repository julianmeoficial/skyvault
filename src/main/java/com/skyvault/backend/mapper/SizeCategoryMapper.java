package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SizeCategoryDto;
import com.skyvault.backend.model.SizeCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SizeCategoryMapper {

    @Mapping(target = "aircraftCount", source = ".", qualifiedByName = "getAircraftCount")
    @Mapping(target = "passengerRange", source = ".", qualifiedByName = "formatPassengerRange")
    SizeCategoryDto toDto(SizeCategory sizeCategory);

    /**
     * Lista de categorías
     */
    List<SizeCategoryDto> toDtoList(List<SizeCategory> sizeCategories);

    /**
     * Método personalizado para formatear rango de pasajeros
     */
    @Named("formatPassengerRange")
    default String formatPassengerRange(SizeCategory category) {
        if (category.getMinPassengers() != null && category.getMaxPassengers() != null) {
            return category.getMinPassengers() + "-" + category.getMaxPassengers();
        }
        return null;
    }

    /**
     * Método personalizado para contar aeronaves
     */
    @Named("getAircraftCount")
    default Integer getAircraftCount(SizeCategory category) {
        return category.getAircraftModels() != null ?
                category.getAircraftModels().size() : 0;
    }
}
