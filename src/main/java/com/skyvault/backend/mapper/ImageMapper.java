package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.ImageDto;
import com.skyvault.backend.model.Image;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ImageMapper {

    /**
     * Mapeo completo a DTO
     */
    ImageDto toDto(Image image);

    /**
     * Lista de imágenes
     */
    List<ImageDto> toDtoList(List<Image> images);

    /**
     * Post-procesamiento para asegurar valores por defecto
     */
    @AfterMapping
    default void afterMapping(@MappingTarget ImageDto dto, Image image) {
        // Asegurar altText si está vacío
        if (dto.getAltText() == null || dto.getAltText().isEmpty()) {
            dto.setAltText("Aircraft image");
        }

        // Asegurar displayOrder
        if (dto.getDisplayOrder() == null) {
            dto.setDisplayOrder(Boolean.TRUE.equals(dto.getIsPrimary()) ? 1 : 10);
        }
    }
}
