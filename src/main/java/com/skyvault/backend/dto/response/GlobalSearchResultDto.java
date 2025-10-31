package com.skyvault.backend.dto.response;

import java.util.List;

public class GlobalSearchResultDto {
    private List<SearchSuggestionDto> aircraft;
    private List<SearchSuggestionDto> manufacturers;
    private List<SearchSuggestionDto> families;
    private Integer totalResults;

    public GlobalSearchResultDto() {}

    public List<SearchSuggestionDto> getAircraft() { return aircraft; }
    public void setAircraft(List<SearchSuggestionDto> aircraft) { this.aircraft = aircraft; }

    public List<SearchSuggestionDto> getManufacturers() { return manufacturers; }
    public void setManufacturers(List<SearchSuggestionDto> manufacturers) { this.manufacturers = manufacturers; }

    public List<SearchSuggestionDto> getFamilies() { return families; }
    public void setFamilies(List<SearchSuggestionDto> families) { this.families = families; }

    public Integer getTotalResults() { return totalResults; }
    public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }
}