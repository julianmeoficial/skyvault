package com.skyvault.backend.specification;

import com.skyvault.backend.dto.request.ManufacturerFilterDto;
import com.skyvault.backend.model.Manufacturer;
import org.springframework.data.jpa.domain.Specification;

public class ManufacturerSpecification {

    /**
     * Crear especificación desde filtros
     */
    public static Specification<Manufacturer> withFilters(ManufacturerFilterDto filterDto) {
        if (filterDto == null) {
            return null;
        }

        Specification<Manufacturer> spec = Specification.where(null);

        if (filterDto.getName() != null && !filterDto.getName().trim().isEmpty()) {
            spec = spec.and(hasName(filterDto.getName()));
        }

        if (filterDto.getCountry() != null && !filterDto.getCountry().trim().isEmpty()) {
            spec = spec.and(hasCountry(filterDto.getCountry()));
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
     * Filtro por nombre de fabricante
     */
    public static Specification<Manufacturer> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")),
                    "%" + name.toLowerCase().trim() + "%");
        };
    }

    /**
     * Filtro por país
     */
    public static Specification<Manufacturer> hasCountry(String country) {
        return (root, query, cb) -> {
            if (country == null || country.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("country")),
                    "%" + country.toLowerCase().trim() + "%");
        };
    }

    /**
     * Filtro por término de búsqueda (nombre o país)
     */
    public static Specification<Manufacturer> hasSearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return null;
            }

            String term = "%" + searchTerm.toLowerCase().trim() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("country")), term),
                    cb.like(cb.lower(root.get("description")), term)
            );
        };
    }

    /**
     * Filtro por fabricantes que tienen aeronaves activas
     */
    public static Specification<Manufacturer> hasActiveAircraft() {
        return (root, query, cb) -> {
            var aircraftJoin = root.join("aircraftModels");
            return cb.equal(aircraftJoin.get("isActive"), true);
        };
    }

    /**
     * Filtro por fabricantes que tienen familias
     */
    public static Specification<Manufacturer> hasFamilies() {
        return (root, query, cb) -> {
            var familiesJoin = root.join("families");
            return cb.isNotNull(familiesJoin.get("id"));
        };
    }
}
