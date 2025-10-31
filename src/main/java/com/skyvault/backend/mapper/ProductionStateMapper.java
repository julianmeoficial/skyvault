package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ProductionStateDto;
import com.skyvault.backend.model.ProductionState;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductionStateMapper {

    /**
     * Mapeo a DTO
     */
    @Mapping(target = "aircraftCount", source = ".", qualifiedByName = "getAircraftCount")
    ProductionStateDto toDto(ProductionState productionState);

    /**
     * Lista de estados
     */
    List<ProductionStateDto> toDtoList(List<ProductionState> productionStates);

    /**
     * Método personalizado para contar aeronaves
     */
    @Named("getAircraftCount")
    default Integer getAircraftCount(ProductionState state) {
        return state.getAircraftModels() != null ?
                state.getAircraftModels().size() : 0;
    }
}
