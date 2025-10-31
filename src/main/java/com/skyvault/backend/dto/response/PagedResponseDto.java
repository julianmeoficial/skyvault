package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Respuesta paginada genérica")
public class PagedResponseDto<T> {

    @Schema(description = "Lista de elementos de la página actual")
    private List<T> content;

    @Schema(description = "Información de paginación")
    private PageMetadataDto page;

    @Schema(description = "Información de filtros aplicados")
    private FilterMetadataDto filters;

    @Schema(description = "Información de ordenamiento aplicado")
    private SortMetadataDto sort;

    // Constructors
    public PagedResponseDto() {}

    public PagedResponseDto(List<T> content, PageMetadataDto page) {
        this.content = content;
        this.page = page;
    }

    // Getters and Setters
    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PageMetadataDto getPage() {
        return page;
    }

    public void setPage(PageMetadataDto page) {
        this.page = page;
    }

    public FilterMetadataDto getFilters() {
        return filters;
    }

    public void setFilters(FilterMetadataDto filters) {
        this.filters = filters;
    }

    public SortMetadataDto getSort() {
        return sort;
    }

    public void setSort(SortMetadataDto sort) {
        this.sort = sort;
    }
}

