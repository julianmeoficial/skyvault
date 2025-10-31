package com.skyvault.backend.specification;

import com.skyvault.backend.dto.request.FamilyFilterDto;
import com.skyvault.backend.model.Family;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FamilySpecification {

    /**
     * Crear especificación desde filtros
     */
    public static Specification<Family> withFilters(FamilyFilterDto filterDto) {
        if (filterDto == null) {
            return null;
        }

        Specification<Family> spec = Specification.where(null);

        if (filterDto.getManufacturerId() != null) {
            spec = spec.and(hasManufacturerId(filterDto.getManufacturerId()));
        }

        if (filterDto.getName() != null && !filterDto.getName().trim().isEmpty()) {
            spec = spec.and(hasName(filterDto.getName()));
        }

        if (filterDto.getCategory() != null && !filterDto.getCategory().trim().isEmpty()) {
            spec = spec.and(hasCategory(filterDto.getCategory()));
        }

        if (filterDto.getSearchTerm() != null && !filterDto.getSearchTerm().trim().isEmpty()) {
            spec = spec.and(hasSearchTerm(filterDto.getSearchTerm()));
        }

        if (Boolean.TRUE.equals(filterDto.getOnlyWithActiveAircraft())) {
            spec = spec.and(hasActiveAircraft());
        }

        return spec;
    }

    /**
     * Filtro por fabricante (ID)
     */
    public static Specification<Family> hasManufacturerId(Long manufacturerId) {
        return (root, query, cb) -> {
            if (manufacturerId == null) {
                return null;
            }
            return cb.equal(root.get("manufacturer").get("id"), manufacturerId);
        };
    }

    /**
     * Filtro por múltiples fabricantes
     */
    public static Specification<Family> hasManufacturerIds(List<Long> manufacturerIds) {
        return (root, query, cb) -> {
            if (manufacturerIds == null || manufacturerIds.isEmpty()) {
                return null;
            }
            return root.get("manufacturer").get("id").in(manufacturerIds);
        };
    }

    /**
     * Filtro por nombre de familia
     */
    public static Specification<Family> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")),
                    "%" + name.toLowerCase().trim() + "%");
        };
    }

    /**
     * Filtro por categoría
     */
    public static Specification<Family> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("category")),
                    "%" + category.toLowerCase().trim() + "%");
        };
    }

    /**
     * Filtro por múltiples categorías
     */
    public static Specification<Family> hasCategories(List<String> categories) {
        return (root, query, cb) -> {
            if (categories == null || categories.isEmpty()) {
                return null;
            }
            return root.get("category").in(categories);
        };
    }

    /**
     * Filtro por término de búsqueda
     */
    public static Specification<Family> hasSearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return null;
            }

            String term = "%" + searchTerm.toLowerCase().trim() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("description")), term),
                    cb.like(cb.lower(root.get("category")), term),
                    cb.like(cb.lower(root.get("manufacturer").get("name")), term)
            );
        };
    }

    /**
     * Filtro por familias que tienen aeronaves activas
     */
    public static Specification<Family> hasActiveAircraft() {
        return (root, query, cb) -> {
            var aircraftJoin = root.join("aircraftModels");
            return cb.equal(aircraftJoin.get("isActive"), true);
        };
    }
}
