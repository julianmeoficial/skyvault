package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.*;
import com.skyvault.backend.model.AircraftModel;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ManufacturerMapper.class, FamilyMapper.class, ImageMapper.class, SpecificationsMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AircraftMapper {

    /**
     * Mapeo a DTO de detalle completo
     */
    @Mapping(target = "manufacturer", source = "manufacturer")
    @Mapping(target = "family", source = "family")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "productionState", source = "productionState")
    @Mapping(target = "sizeCategory", source = "sizeCategory")
    @Mapping(target = "specifications", source = "specifications", qualifiedByName = "toSpecificationsDto") // ⭐ ESPECIFICAR MÉTODO
    @Mapping(target = "images", source = "images")
    @Mapping(target = "firstFlightDate", source = "firstFlightDate", dateFormat = "yyyy-MM-dd")
    AircraftDetailDto toDetailDto(AircraftModel aircraft);

    /**
     * Mapeo a DTO resumido
     */
    @Mapping(target = "manufacturerName", source = "manufacturer.name")
    @Mapping(target = "highlightValue", ignore = true)
    AircraftSummaryDto toSummaryDto(AircraftModel aircraft);

    /**
     * Mapeo a DTO de tarjeta (para catálogo)
     */
    @Mapping(target = "manufacturer", source = "manufacturer")
    @Mapping(target = "family", source = "family")
    @Mapping(target = "thumbnailUrl", source = ".", qualifiedByName = "getThumbnailUrl")
    AircraftCardDto toCardDto(AircraftModel aircraft);

    /**
     * Lista de tarjetas
     */
    List<AircraftCardDto> toCardDtoList(List<AircraftModel> aircraft);

    /**
     * Lista de detalles
     */
    List<AircraftDetailDto> toDetailDtoList(List<AircraftModel> aircraft);

    /**
     * Lista de resúmenes
     */
    List<AircraftSummaryDto> toSummaryDtoList(List<AircraftModel> aircraft);

    /**
     * Mapeo de página a respuesta paginada de tarjetas
     */
    @Mapping(target = "content", source = "content")
    @Mapping(target = "page", source = ".", qualifiedByName = "pageToMetadata")
    @Mapping(target = "filters", ignore = true)
    @Mapping(target = "sort", ignore = true)
    PagedResponseDto<AircraftCardDto> toPagedCardResponse(Page<AircraftModel> page);

    /**
     * Método personalizado para extraer URL de thumbnail
     */
    @Named("getThumbnailUrl")
    default String getThumbnailUrl(AircraftModel aircraft) {
        return aircraft.getImages() != null ?
                aircraft.getImages().stream()
                        .filter(img -> "thumbnail".equals(img.getImageType()) || img.getIsPrimary())
                        .map(img -> img.getUrl())
                        .findFirst()
                        .orElse(null) : null;
    }

    /**
     * Método personalizado para crear metadatos de página
     */
    @Named("pageToMetadata")
    default PageMetadataDto pageToMetadata(Page<AircraftModel> page) {
        PageMetadataDto metadata = new PageMetadataDto();
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

    /**
     * Post-procesamiento para DTO de tarjeta
     */
    @AfterMapping
    default void afterMappingCardDto(@MappingTarget AircraftCardDto dto, AircraftModel aircraft) {
        // Asegurar displayName si está vacío
        if (dto.getDisplayName() == null || dto.getDisplayName().isEmpty()) {
            dto.setDisplayName(aircraft.getManufacturer().getName() + " " + aircraft.getModel());
        }
    }

    /**
     * Post-procesamiento para DTO de detalle
     */
    @AfterMapping
    default void afterMappingDetailDto(@MappingTarget AircraftDetailDto dto, AircraftModel aircraft) {
        // Asegurar displayName si está vacío
        if (dto.getDisplayName() == null || dto.getDisplayName().isEmpty()) {
            dto.setDisplayName(aircraft.getManufacturer().getName() + " " + aircraft.getModel());
        }

        // Ordenar imágenes por displayOrder y isPrimary
        if (dto.getImages() != null) {
            dto.getImages().sort((img1, img2) -> {
                if (Boolean.TRUE.equals(img1.getIsPrimary())) return -1;
                if (Boolean.TRUE.equals(img2.getIsPrimary())) return 1;
                return Integer.compare(
                        img1.getDisplayOrder() != null ? img1.getDisplayOrder() : 999,
                        img2.getDisplayOrder() != null ? img2.getDisplayOrder() : 999
                );
            });
        }
    }
}
