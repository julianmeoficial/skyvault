package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metadatos de ordenamiento aplicado")
public class SortMetadataDto {

    @Schema(description = "Campo de ordenamiento", example = "name")
    private String field;

    @Schema(description = "Dirección de ordenamiento", example = "ASC")
    private String direction;

    @Schema(description = "Se aplicó ordenamiento", example = "true")
    private Boolean sorted;

    @Schema(description = "Descripción legible del ordenamiento", example = "Sorted by name (A-Z)")
    private String description;

    // Constructors
    public SortMetadataDto() {}

    public SortMetadataDto(String field, String direction) {
        this.field = field;
        this.direction = direction;
        this.sorted = true;
    }

    // Getters and Setters
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Boolean getSorted() {
        return sorted;
    }

    public void setSorted(Boolean sorted) {
        this.sorted = sorted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
