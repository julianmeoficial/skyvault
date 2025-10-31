package com.skyvault.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;

@Schema(description = "Estadísticas de búsquedas")
public class SearchStatisticsDto {

    private Long totalSearches;
    private Long uniqueSearchTerms;
    private Long averageSearchesPerDay;
    private List<String> popularSearchTerms;
    private Long noResultSearches;
    private Double noResultRate;
    private Map<String, Long> searchesByType;
    private String mostSearchedTerm;
    private Double averageResultsPerSearch;

    public SearchStatisticsDto() {}

    // Getters y Setters
    public Long getTotalSearches() { return totalSearches; }
    public void setTotalSearches(Long totalSearches) { this.totalSearches = totalSearches; }

    public Long getUniqueSearchTerms() { return uniqueSearchTerms; }
    public void setUniqueSearchTerms(Long uniqueSearchTerms) { this.uniqueSearchTerms = uniqueSearchTerms; }

    public Long getAverageSearchesPerDay() { return averageSearchesPerDay; }
    public void setAverageSearchesPerDay(Long averageSearchesPerDay) { this.averageSearchesPerDay = averageSearchesPerDay; }

    public List<String> getPopularSearchTerms() { return popularSearchTerms; }
    public void setPopularSearchTerms(List<String> popularSearchTerms) { this.popularSearchTerms = popularSearchTerms; }

    public Long getNoResultSearches() { return noResultSearches; }
    public void setNoResultSearches(Long noResultSearches) { this.noResultSearches = noResultSearches; }

    public Double getNoResultRate() { return noResultRate; }
    public void setNoResultRate(Double noResultRate) { this.noResultRate = noResultRate; }

    public Map<String, Long> getSearchesByType() { return searchesByType; }
    public void setSearchesByType(Map<String, Long> searchesByType) { this.searchesByType = searchesByType; }

    public String getMostSearchedTerm() { return mostSearchedTerm; }
    public void setMostSearchedTerm(String mostSearchedTerm) { this.mostSearchedTerm = mostSearchedTerm; }

    public Double getAverageResultsPerSearch() { return averageResultsPerSearch; }
    public void setAverageResultsPerSearch(Double averageResultsPerSearch) { this.averageResultsPerSearch = averageResultsPerSearch; }
}
