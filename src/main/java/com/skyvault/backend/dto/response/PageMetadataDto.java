package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metadatos de paginación")
public class PageMetadataDto {

    @Schema(description = "Número de página actual (basado en 0)", example = "0")
    private Integer number;

    @Schema(description = "Tamaño de página", example = "20")
    private Integer size;

    @Schema(description = "Número total de elementos", example = "156")
    private Long totalElements;

    @Schema(description = "Número total de páginas", example = "8")
    private Integer totalPages;

    @Schema(description = "Número de elementos en la página actual", example = "20")
    private Integer numberOfElements;

    @Schema(description = "Es la primera página", example = "true")
    private Boolean first;

    @Schema(description = "Es la última página", example = "false")
    private Boolean last;

    @Schema(description = "Tiene página anterior", example = "false")
    private Boolean hasPrevious;

    @Schema(description = "Tiene página siguiente", example = "true")
    private Boolean hasNext;

    @Schema(description = "Página está vacía", example = "false")
    private Boolean empty;

    // Constructors
    public PageMetadataDto() {}

    // Getters and Setters
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}
