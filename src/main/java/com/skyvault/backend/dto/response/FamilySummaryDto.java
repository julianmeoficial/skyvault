package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información resumida de familia")
public class FamilySummaryDto {

    @Schema(description = "ID único de la familia", example = "5")
    private Long id;

    @Schema(description = "Nombre de la familia", example = "737")
    private String name;

    @Schema(description = "Categoría", example = "Narrow-body")
    private String category;

    // Constructors
    public FamilySummaryDto() {}

    public FamilySummaryDto(Long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
