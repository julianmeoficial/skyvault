package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Información de imagen de aeronave")
public class ImageDto {

    @Schema(description = "ID único de la imagen", example = "25")
    private Long id;

    @Schema(description = "URL de la imagen", example = "https://images.skyvault.com/aircraft/boeing-737-800-main.jpg")
    private String url;

    @Schema(description = "Texto alternativo", example = "Boeing 737-800 vista lateral")
    private String altText;

    @Schema(description = "Título de la imagen", example = "Boeing 737-800")
    private String title;

    @Schema(description = "Tipo de imagen", example = "main", allowableValues = {"thumbnail", "main", "comparison", "gallery", "detail"})
    private String imageType;

    @Schema(description = "Formato del archivo", example = "jpg")
    private String fileFormat;

    @Schema(description = "Tamaño del archivo en bytes", example = "524288")
    private Long fileSizeBytes;

    @Schema(description = "Ancho en píxeles", example = "1920")
    private Integer width;

    @Schema(description = "Alto en píxeles", example = "1080")
    private Integer height;

    @Schema(description = "Es imagen principal", example = "true")
    private Boolean isPrimary;

    @Schema(description = "Para uso en comparaciones", example = "false")
    private Boolean isComparison;

    @Schema(description = "Orden de visualización", example = "1")
    private Integer displayOrder;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    // Constructors
    public ImageDto() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(Long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean getIsComparison() {
        return isComparison;
    }

    public void setIsComparison(Boolean isComparison) {
        this.isComparison = isComparison;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
