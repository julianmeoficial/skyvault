package com.skyvault.backend.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
public class CacheConfig {

    // ========== NOMBRES DE CACHE (SINCRONIZADOS CON @Cacheable) ==========

    // ManufacturersPage
    public static final String MANUFACTURERS_CACHE = "manufacturers";
    public static final String MANUFACTURER_LIST_CACHE = "manufacturerListCache";

    // Families
    public static final String FAMILIES_CACHE = "families";
    public static final String FAMILY_LIST_CACHE = "familyListCache";

    // Aircraft
    public static final String AIRCRAFT_DETAIL_CACHE = "aircraft-detail";
    public static final String AIRCRAFT_LIST_CACHE = "aircraft-list";
    public static final String AIRCRAFT_CACHE = "aircraftCache";

    // Specifications & Images
    public static final String SPECIFICATIONS_CACHE = "specifications";
    public static final String IMAGES_CACHE = "images";

    // Search
    public static final String SEARCH_SUGGESTIONS_CACHE = "search-suggestions";
    public static final String SEARCH_CACHE = "searchCache";

    // Catalogs & Statistics
    public static final String CATALOGS_CACHE = "catalogs";
    public static final String STATISTICS_CACHE = "statisticsCache";

    // Comparison
    public static final String COMPARISON_CACHE = "comparisonCache";

    // Populares & Destacados
    public static final String POPULAR_CACHE = "popularCache";
    public static final String FEATURED_CACHE = "featuredCache";
    public static final String NEWEST_CACHE = "newestCache";

    // ========== CACHE MANAGER PARA DESARROLLO ==========

    @Bean
    @Profile({"dev", "test"})
    public CacheManager simpleCacheManager() {
        return new ConcurrentMapCacheManager(
                // ManufacturersPage
                MANUFACTURERS_CACHE,
                MANUFACTURER_LIST_CACHE,

                // Families
                FAMILIES_CACHE,
                FAMILY_LIST_CACHE,

                // Aircraft
                AIRCRAFT_DETAIL_CACHE,
                AIRCRAFT_LIST_CACHE,
                AIRCRAFT_CACHE,

                // Specifications & Images
                SPECIFICATIONS_CACHE,
                IMAGES_CACHE,

                // Search
                SEARCH_SUGGESTIONS_CACHE,
                SEARCH_CACHE,

                // Catalogs & Statistics
                CATALOGS_CACHE,
                STATISTICS_CACHE,

                // Comparison
                COMPARISON_CACHE,

                // Populares & Destacados
                POPULAR_CACHE,
                FEATURED_CACHE,
                NEWEST_CACHE
        );
    }

    // ========== CACHE MANAGER PARA PRODUCCIÓN ==========

    @Bean
    @Profile("prod")
    public CacheManager productionCacheManager() {
        return new ConcurrentMapCacheManager(
                // ManufacturersPage
                MANUFACTURERS_CACHE,
                MANUFACTURER_LIST_CACHE,

                // Families
                FAMILIES_CACHE,
                FAMILY_LIST_CACHE,

                // Aircraft
                AIRCRAFT_DETAIL_CACHE,
                AIRCRAFT_LIST_CACHE,
                AIRCRAFT_CACHE,

                // Specifications & Images
                SPECIFICATIONS_CACHE,
                IMAGES_CACHE,

                // Search
                SEARCH_SUGGESTIONS_CACHE,
                SEARCH_CACHE,

                // Catalogs & Statistics
                CATALOGS_CACHE,
                STATISTICS_CACHE,

                // Comparison
                COMPARISON_CACHE,

                // Populares & Destacados
                POPULAR_CACHE,
                FEATURED_CACHE,
                NEWEST_CACHE
        );
    }

    // ========== CONSTANTES DE TTL Y TAMAÑO ==========

    public static class CacheConstants {
        // Tiempos de vida del cache (en minutos)
        public static final long MANUFACTURERS_TTL = 60;
        public static final long FAMILIES_TTL = 60;
        public static final long AIRCRAFT_DETAIL_TTL = 30;
        public static final long AIRCRAFT_LIST_TTL = 15;
        public static final long SPECIFICATIONS_TTL = 60;
        public static final long IMAGES_TTL = 120;
        public static final long SEARCH_SUGGESTIONS_TTL = 30;
        public static final long CATALOGS_TTL = 120;
        public static final long COMPARISON_TTL = 30;
        public static final long AIRCRAFT_TTL = 30;
        public static final long SEARCH_TTL = 15;
        public static final long STATISTICS_TTL = 60;

        // Tamaños máximos de cache
        public static final int MANUFACTURERS_MAX_SIZE = 100;
        public static final int FAMILIES_MAX_SIZE = 500;
        public static final int AIRCRAFT_DETAIL_MAX_SIZE = 1000;
        public static final int AIRCRAFT_LIST_MAX_SIZE = 2000;
        public static final int SPECIFICATIONS_MAX_SIZE = 1000;
        public static final int IMAGES_MAX_SIZE = 5000;
        public static final int SEARCH_SUGGESTIONS_MAX_SIZE = 1000;
        public static final int CATALOGS_MAX_SIZE = 200;
        public static final int COMPARISON_MAX_SIZE = 500;
        public static final int AIRCRAFT_MAX_SIZE = 1000;
        public static final int SEARCH_MAX_SIZE = 1000;
        public static final int STATISTICS_MAX_SIZE = 100;

        private CacheConstants() {}
    }

    // ========== GENERADORES DE KEYS ==========

    public static class CacheKeyGenerator {

        public static String aircraftDetailKey(Long aircraftId) {
            return "aircraft:detail:" + aircraftId;
        }

        public static String aircraftListKey(String filters, String sort, int page, int size) {
            return String.format("aircraft:list:%s:%s:%d:%d",
                    hashString(filters), sort, page, size);
        }

        public static String manufacturerListKey(int page, int size, String sort) {
            return String.format("manufacturer:list:%d:%d:%s", page, size, sort);
        }

        public static String familyListKey(Long manufacturerId, String category, int page, int size) {
            return String.format("family:list:%s:%s:%d:%d",
                    manufacturerId, category, page, size);
        }

        public static String suggestionsKey(String query) {
            return "suggestions:" + query.toLowerCase().trim();
        }

        public static String catalogKey(String catalogType) {
            return "catalog:" + catalogType;
        }

        public static String comparisonKey(String aircraftIds, boolean includeSpecs, boolean normalize) {
            return String.format("comparison:%s:%b:%b", aircraftIds, includeSpecs, normalize);
        }

        private static String hashString(String input) {
            return input != null ? String.valueOf(input.hashCode()) : "null";
        }

        private CacheKeyGenerator() {}
    }
}
