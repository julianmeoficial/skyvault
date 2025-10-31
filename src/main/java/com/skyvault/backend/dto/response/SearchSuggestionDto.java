package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sugerencia de búsqueda")
public class SearchSuggestionDto {

    @Schema(description = "Texto de la sugerencia", example = "Boeing 737-800")
    private String text;

    @Schema(description = "Tipo de sugerencia", example = "aircraft", allowableValues = {"aircraft", "manufacturer", "family"})
    private String type;

    @Schema(description = "ID del elemento sugerido", example = "15")
    private Long id;

    @Schema(description = "Información adicional", example = "Boeing - Commercial Airliner")
    private String subtitle;

    @Schema(description = "URL de imagen pequeña")
    private String imageUrl;

    @Schema(description = "Número de coincidencias encontradas", example = "1")
    private Integer matchCount;

    @Schema(description = "Relevancia de la sugerencia (0-100)", example = "95.5")
    private Double relevance;

    // Constructors
    public SearchSuggestionDto() {}

    public SearchSuggestionDto(String text, String type, Long id) {
        this.text = text;
        this.type = type;
        this.id = id;
    }

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public Double getRelevance() {
        return relevance;
    }

    public void setRelevance(Double relevance) {
        this.relevance = relevance;
    }
}
