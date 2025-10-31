package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.FamilyDto;
import com.skyvault.backend.dto.response.FamilySummaryDto;
import com.skyvault.backend.dto.response.PagedResponseDto;
import com.skyvault.backend.model.Family;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ManufacturerMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FamilyMapper {

    /**
     * Mapeo completo a DTO
     */
    @Mapping(target = "manufacturer", source = "manufacturer")
    @Mapping(target = "activeModelCount", source = ".", qualifiedByName = "getActiveModelCount")
    FamilyDto toDto(Family family);

    /**
     * Mapeo a DTO resumido
     */
    FamilySummaryDto toSummaryDto(Family family);

    /**
     * Lista completa
     */
    List<FamilyDto> toDtoList(List<Family> families);

    /**
     * Lista resumida
     */
    List<FamilySummaryDto> toSummaryDtoList(List<Family> families);

    /**
     * Página a respuesta paginada
     */
    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = ".", qualifiedByName = "pageToMetadata")
    @Mapping(target = "filters", ignore = true)
    @Mapping(target = "sort", ignore = true)
    PagedResponseDto<FamilyDto> toPagedResponse(Page<Family> page);

    /**
     * Método personalizado para contar modelos activos
     */
    @Named("getActiveModelCount")
    default Integer getActiveModelCount(Family family) {
        return family.getAircraftModels() != null ?
                (int) family.getAircraftModels().stream()
                        .filter(aircraft -> Boolean.TRUE.equals(aircraft.getIsActive()))
                        .count() : 0;
    }

    /**
     * Método personalizado para crear metadatos de página
     */
    @Named("pageToMetadata")
    default com.skyvault.backend.dto.response.PageMetadataDto pageToMetadata(Page<Family> page) {
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
