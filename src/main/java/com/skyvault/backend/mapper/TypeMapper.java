package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.TypeDto;
import com.skyvault.backend.model.Type;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TypeMapper {

    /**
     * Mapeo a DTO
     */
    @Mapping(target = "aircraftCount", source = ".", qualifiedByName = "getAircraftCount")
    TypeDto toDto(Type type);

    /**
     * Lista de tipos
     */
    List<TypeDto> toDtoList(List<Type> types);

    /**
     * Método personalizado para contar aeronaves
     */
    @Named("getAircraftCount")
    default Integer getAircraftCount(Type type) {
        return type.getAircraftModels() != null ?
                type.getAircraftModels().size() : 0;
    }
}
