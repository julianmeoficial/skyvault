package com.skyvault.backend.mapper;

import com.skyvault.backend.dto.response.SearchSuggestionDto;
import com.skyvault.backend.model.AircraftModel;
import com.skyvault.backend.model.Family;
import com.skyvault.backend.model.Manufacturer;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SearchMapper {

    /**
     * Aeronave a sugerencia
     */
    @Mapping(target = "text", source = "displayName")
    @Mapping(target = "type", constant = "aircraft")
    @Mapping(target = "subtitle", source = ".", qualifiedByName = "getAircraftSubtitle")
    @Mapping(target = "imageUrl", source = ".", qualifiedByName = "getAircraftThumbnail")
    @Mapping(target = "matchCount", constant = "1")
    @Mapping(target = "relevance", ignore = true)
    SearchSuggestionDto aircraftToSuggestion(AircraftModel aircraft);

    /**
     * Fabricante a sugerencia
     */
    @Mapping(target = "text", source = "name")
    @Mapping(target = "type", constant = "manufacturer")
    @Mapping(target = "subtitle", source = "country")
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "matchCount", source = ".", qualifiedByName = "getManufacturerAircraftCount")
    @Mapping(target = "relevance", ignore = true)
    SearchSuggestionDto manufacturerToSuggestion(Manufacturer manufacturer);

    /**
     * Familia a sugerencia
     */
    @Mapping(target = "text", source = "name")
    @Mapping(target = "type", constant = "family")
    @Mapping(target = "subtitle", source = ".", qualifiedByName = "getFamilySubtitle")
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "matchCount", source = ".", qualifiedByName = "getFamilyAircraftCount")
    @Mapping(target = "relevance", ignore = true)
    SearchSuggestionDto familyToSuggestion(Family family);

    /**
     * Listas de sugerencias
     */
    List<SearchSuggestionDto> aircraftToSuggestionList(List<AircraftModel> aircraft);
    List<SearchSuggestionDto> manufacturerToSuggestionList(List<Manufacturer> manufacturers);
    List<SearchSuggestionDto> familyToSuggestionList(List<Family> families);

    /**
     * Método personalizado para subtitle de aeronave
     */
    @Named("getAircraftSubtitle")
    default String getAircraftSubtitle(AircraftModel aircraft) {
        return aircraft.getManufacturer().getName() + " - " +
                (aircraft.getType() != null ? aircraft.getType().getName() : "Commercial Aircraft");
    }

    /**
     * Método personalizado para thumbnail de aeronave
     */
    @Named("getAircraftThumbnail")
    default String getAircraftThumbnail(AircraftModel aircraft) {
        return aircraft.getImages() != null ?
                aircraft.getImages().stream()
                        .filter(img -> "thumbnail".equals(img.getImageType()))
                        .map(img -> img.getUrl())
                        .findFirst()
                        .orElse(null) : null;
    }

    /**
     * Método personalizado para contar aeronaves del fabricante
     */
    @Named("getManufacturerAircraftCount")
    default Integer getManufacturerAircraftCount(Manufacturer manufacturer) {
        return manufacturer.getAircraftModels() != null ?
                (int) manufacturer.getAircraftModels().stream()
                        .filter(aircraft -> Boolean.TRUE.equals(aircraft.getIsActive()))
                        .count() : 0;
    }

    /**
     * Método personalizado para subtitle de familia
     */
    @Named("getFamilySubtitle")
    default String getFamilySubtitle(Family family) {
        return family.getManufacturer().getName() + " - " +
                (family.getCategory() != null ? family.getCategory() : "Aircraft Family");
    }

    /**
     * Método personalizado para contar aeronaves de la familia
     */
    @Named("getFamilyAircraftCount")
    default Integer getFamilyAircraftCount(Family family) {
        return family.getAircraftModels() != null ?
                (int) family.getAircraftModels().stream()
                        .filter(aircraft -> Boolean.TRUE.equals(aircraft.getIsActive()))
                        .count() : 0;
    }
}
