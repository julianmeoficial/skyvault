package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ManufacturerDto;
import com.skyvault.backend.dto.response.ManufacturerSummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.model.Manufacturer;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ManufacturerMapper {

    /**
     * Mapeo completo a DTO
     */
    @Mapping(target = "activeAircraftCount", source = ".", qualifiedByName = "getActiveAircraftCount")
    @Mapping(target = "familyCount", source = ".", qualifiedByName = "getFamilyCount")
    ManufacturerDto toDto(Manufacturer manufacturer);

    /**
     * Mapeo a DTO resumido
     */
    ManufacturerSummaryDto toSummaryDto(Manufacturer manufacturer);

    /**
     * Lista completa
     */
    List<ManufacturerDto> toDtoList(List<Manufacturer> manufacturers);

    /**
     * Lista resumida
     */
    List<ManufacturerSummaryDto> toSummaryDtoList(List<Manufacturer> manufacturers);

    /**
     * Página a respuesta paginada
     */
    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = ".", qualifiedByName = "pageToMetadata")
    @Mapping(target = "filters", ignore = true)
    @Mapping(target = "sort", ignore = true)
    PagedResponseDto<ManufacturerDto> toPagedResponse(Page<Manufacturer> page);

    /**
     * Método personalizado para contar aeronaves activas
     */
    @Named("getActiveAircraftCount")
    default Integer getActiveAircraftCount(Manufacturer manufacturer) {
        return manufacturer.getAircraftModels() != null ?
                (int) manufacturer.getAircraftModels().stream()
                        .filter(aircraft -> Boolean.TRUE.equals(aircraft.getIsActive()))
                        .count() : 0;
    }

    /**
     * Método personalizado para contar familias
     */
    @Named("getFamilyCount")
    default Integer getFamilyCount(Manufacturer manufacturer) {
        return manufacturer.getFamilies() != null ?
                manufacturer.getFamilies().size() : 0;
    }

    /**
     * Método personalizado para crear metadatos de página
     */
    @Named("pageToMetadata")
    default com.skyvault.backend.dto.response.PageMetadataDto pageToMetadata(Page<Manufacturer> page) {
        com.skyvault.backend.dto.response.PageMetadataDto metadata =
                new com.skyvault.backend.dto.response.PageMetadataDto();
        metadata.setNumber(page.getNumber());
        metadata.setSize(page.getSize());
        metadata.setTotalElements(page.getTotalElements());
        metadata.setTotalPages(page.getTotalPages());
        metadata.setNumberOfElements(page.getNumberOfElements());
        metadata.setFirst(page.isFirst());
        metadata.setLast(page.isLast());
        metadata.setHasPrevious(page.hasPrevious());
        metadata.setHasNext(page.hasNext());
        metadata.setEmpty(page.isEmpty());
        return metadata;
    }
}
