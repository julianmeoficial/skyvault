package com.skyvault.backend.specification;

import com.skyvault.backend.model.AircraftModel;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AircraftSpecification extends BaseSpecification<AircraftModel> {

    /**
     * Filtro por fabricante (ID)
     */
    public static Specification<AircraftModel> hasManufacturerId(Long manufacturerId) {
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
    public static Specification<AircraftModel> hasManufacturerIds(List<Long> manufacturerIds) {
        return (root, query, cb) -> {
            if (manufacturerIds == null || manufacturerIds.isEmpty()) {
                return null;
            }

            return root.get("manufacturer").get("id").in(manufacturerIds);
        };
    }

    /**
     * Filtro por nombre de fabricante (texto)
     */
    public static Specification<AircraftModel> hasManufacturerName(String manufacturerName) {
        return (root, query, cb) -> {
            if (manufacturerName == null || manufacturerName.trim().isEmpty()) {
                return null;
            }

            return cb.like(cb.lower(root.get("manufacturer").get("name")),
                    "%" + manufacturerName.toLowerCase().trim() + "%");
        };
    }

    /**
     * Filtro por familia (ID)
     */
    public static Specification<AircraftModel> hasFamilyId(Long familyId) {
        return (root, query, cb) -> {
            if (familyId == null) {
                return null;
            }

            return cb.equal(root.get("family").get("id"), familyId);
        };
    }

    /**
     * Filtro por múltiples familias
     */
    public static Specification<AircraftModel> hasFamilyIds(List<Long> familyIds) {
        return (root, query, cb) -> {
            if (familyIds == null || familyIds.isEmpty()) {
                return null;
            }

            return root.get("family").get("id").in(familyIds);
        };
    }

    /**
     * Filtro por tipo (ID)
     */
    public static Specification<AircraftModel> hasTypeId(Long typeId) {
        return (root, query, cb) -> {
            if (typeId == null) {
                return null;
            }

            return cb.equal(root.get("type").get("id"), typeId);
        };
    }

    /**
     * Filtro por múltiples tipos
     */
    public static Specification<AircraftModel> hasTypeIds(List<Long> typeIds) {
        return (root, query, cb) -> {
            if (typeIds == null || typeIds.isEmpty()) {
                return null;
            }

            return root.get("type").get("id").in(typeIds);
        };
    }

    /**
     * Filtro por estado de producción (ID)
     */
    public static Specification<AircraftModel> hasProductionStateId(Long productionStateId) {
        return (root, query, cb) -> {
            if (productionStateId == null) {
                return null;
            }

            return cb.equal(root.get("productionState").get("id"), productionStateId);
        };
    }

    /**
     * Filtro por múltiples estados de producción
     */
    public static Specification<AircraftModel> hasProductionStateIds(List<Long> productionStateIds) {
        return (root, query, cb) -> {
            if (productionStateIds == null || productionStateIds.isEmpty()) {
                return null;
            }

            return root.get("productionState").get("id").in(productionStateIds);
        };
    }

    /**
     * Filtro por categoría de tamaño (ID)
     */
    public static Specification<AircraftModel> hasSizeCategoryId(Long sizeCategoryId) {
        return (root, query, cb) -> {
            if (sizeCategoryId == null) {
                return null;
            }

            return cb.equal(root.get("sizeCategory").get("id"), sizeCategoryId);
        };
    }

    /**
     * Filtro por múltiples categorías de tamaño
     */
    public static Specification<AircraftModel> hasSizeCategoryIds(List<Long> sizeCategoryIds) {
        return (root, query, cb) -> {
            if (sizeCategoryIds == null || sizeCategoryIds.isEmpty()) {
                return null;
            }

            return root.get("sizeCategory").get("id").in(sizeCategoryIds);
        };
    }

    /**
     * Filtro por rango de pasajeros (mínimo)
     */
    public static Specification<AircraftModel> hasMinPassengers(Integer minPassengers) {
        return (root, query, cb) -> {
            if (minPassengers == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("maxPassengers"), minPassengers);
        };
    }

    /**
     * Filtro por rango de pasajeros (máximo)
     */
    public static Specification<AircraftModel> hasMaxPassengers(Integer maxPassengers) {
        return (root, query, cb) -> {
            if (maxPassengers == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("maxPassengers"), maxPassengers);
        };
    }

    /**
     * Filtro por rango de alcance (mínimo)
     */
    public static Specification<AircraftModel> hasMinRange(Integer minRange) {
        return (root, query, cb) -> {
            if (minRange == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("rangeKm"), minRange);
        };
    }

    /**
     * Filtro por rango de alcance (máximo)
     */
    public static Specification<AircraftModel> hasMaxRange(Integer maxRange) {
        return (root, query, cb) -> {
            if (maxRange == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("rangeKm"), maxRange);
        };
    }

    /**
     * Filtro por año de introducción (mínimo)
     */
    public static Specification<AircraftModel> hasMinIntroductionYear(Integer minYear) {
        return (root, query, cb) -> {
            if (minYear == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("introductionYear"), minYear);
        };
    }

    /**
     * Filtro por año de introducción (máximo)
     */
    public static Specification<AircraftModel> hasMaxIntroductionYear(Integer maxYear) {
        return (root, query, cb) -> {
            if (maxYear == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("introductionYear"), maxYear);
        };
    }

    /**
     * Filtro por velocidad de crucero (mínimo)
     */
    public static Specification<AircraftModel> hasMinCruiseSpeed(Integer minSpeed) {
        return (root, query, cb) -> {
            if (minSpeed == null) {
                return null;
            }

            return cb.greaterThanOrEqualTo(root.get("cruiseSpeedKnots"), minSpeed);
        };
    }

    /**
     * Filtro por velocidad de crucero (máximo)
     */
    public static Specification<AircraftModel> hasMaxCruiseSpeed(Integer maxSpeed) {
        return (root, query, cb) -> {
            if (maxSpeed == null) {
                return null;
            }

            return cb.lessThanOrEqualTo(root.get("cruiseSpeedKnots"), maxSpeed);
        };
    }

    /**
     * Filtro por término de búsqueda (nombre, modelo, displayName)
     */
    public static Specification<AircraftModel> hasSearchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return null;
            }

            String term = "%" + searchTerm.toLowerCase().trim() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), term),
                    cb.like(cb.lower(root.get("model")), term),
                    cb.like(cb.lower(root.get("displayName")), term),
                    cb.like(cb.lower(root.get("manufacturer").get("name")), term),
                    cb.like(cb.lower(root.get("family").get("name")), term)
            );
        };
    }

    /**
     * Filtro por aeronaves activas solamente
     */
    public static Specification<AircraftModel> isActive() {
        return (root, query, cb) -> cb.equal(root.get("isActive"), true);
    }

    /**
     * Filtro por aeronaves que tienen especificaciones
     */
    public static Specification<AircraftModel> hasSpecifications() {
        return (root, query, cb) -> cb.isNotNull(root.get("specifications"));
    }

    /**
     * Filtro por aeronaves que tienen imágenes
     */
    public static Specification<AircraftModel> hasImages() {
        return (root, query, cb) -> {
            Join<Object, Object> imagesJoin = root.join("images", JoinType.LEFT);
            return cb.isNotNull(imagesJoin.get("id"));
        };
    }

    /**
     * Filtro por aeronaves con imagen principal
     */
    public static Specification<AircraftModel> hasPrimaryImage() {
        return (root, query, cb) -> {
            Join<Object, Object> imagesJoin = root.join("images", JoinType.INNER);
            return cb.equal(imagesJoin.get("isPrimary"), true);
        };
    }

    /**
     * Filtro combinado para búsqueda avanzada - CORREGIDO
     */
    public static Specification<AircraftModel> hasAdvancedFilters(
            Long manufacturerId,
            List<Long> manufacturerIds,
            Long familyId,
            List<Long> familyIds,
            Long typeId,
            List<Long> typeIds,
            Long productionStateId,
            List<Long> productionStateIds,
            Long sizeCategoryId,
            List<Long> sizeCategoryIds,
            Integer minPassengers,
            Integer maxPassengers,
            Integer minRange,
            Integer maxRange,
            Integer minIntroductionYear,
            Integer maxIntroductionYear,
            Integer minCruiseSpeed,
            Integer maxCruiseSpeed,
            String searchTerm,
            Boolean onlyActive,
            Boolean onlyWithSpecifications,
            Boolean onlyWithImages) {

        return (root, query, criteriaBuilder) -> {
            // Lista para almacenar todos los predicados
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();

            // Aplicar filtros individualmente
            if (manufacturerId != null) {
                predicates.add(criteriaBuilder.equal(root.get("manufacturer").get("id"), manufacturerId));
            }

            if (manufacturerIds != null && !manufacturerIds.isEmpty()) {
                predicates.add(root.get("manufacturer").get("id").in(manufacturerIds));
            }

            if (familyId != null) {
                predicates.add(criteriaBuilder.equal(root.get("family").get("id"), familyId));
            }

            if (familyIds != null && !familyIds.isEmpty()) {
                predicates.add(root.get("family").get("id").in(familyIds));
            }

            if (typeId != null) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), typeId));
            }

            if (typeIds != null && !typeIds.isEmpty()) {
                predicates.add(root.get("type").get("id").in(typeIds));
            }

            if (productionStateId != null) {
                predicates.add(criteriaBuilder.equal(root.get("productionState").get("id"), productionStateId));
            }

            if (productionStateIds != null && !productionStateIds.isEmpty()) {
                predicates.add(root.get("productionState").get("id").in(productionStateIds));
            }

            if (sizeCategoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("sizeCategory").get("id"), sizeCategoryId));
            }

            if (sizeCategoryIds != null && !sizeCategoryIds.isEmpty()) {
                predicates.add(root.get("sizeCategory").get("id").in(sizeCategoryIds));
            }

            if (minPassengers != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxPassengers"), minPassengers));
            }

            if (maxPassengers != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("maxPassengers"), maxPassengers));
            }

            if (minRange != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rangeKm"), minRange));
            }

            if (maxRange != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rangeKm"), maxRange));
            }

            if (minIntroductionYear != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("introductionYear"), minIntroductionYear));
            }

            if (maxIntroductionYear != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("introductionYear"), maxIntroductionYear));
            }

            if (minCruiseSpeed != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("cruiseSpeedKnots"), minCruiseSpeed));
            }

            if (maxCruiseSpeed != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("cruiseSpeedKnots"), maxCruiseSpeed));
            }

            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String term = "%" + searchTerm.toLowerCase().trim() + "%";

                var searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("displayName")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("manufacturer").get("name")), term),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("family").get("name")), term)
                );

                predicates.add(searchPredicate);
            }

            if (Boolean.TRUE.equals(onlyActive)) {
                predicates.add(criteriaBuilder.equal(root.get("isActive"), true));
            }

            if (Boolean.TRUE.equals(onlyWithSpecifications)) {
                predicates.add(criteriaBuilder.isNotNull(root.get("specifications")));
            }

            if (Boolean.TRUE.equals(onlyWithImages)) {
                var imagesJoin = root.join("images", jakarta.persistence.criteria.JoinType.INNER);
                predicates.add(criteriaBuilder.isNotNull(imagesJoin.get("id")));
            }

            // Combinar todos los predicados con AND
            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }

    /**
     * Filtro para aeronaves similares (por capacidad y rango) - COMPLETO
     */
    public static Specification<AircraftModel> similarTo(Long excludeId, Integer passengers, Integer range,
                                                         Integer passengerTolerance, Integer rangeTolerance) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();

            // Excluir aeronave específica
            if (excludeId != null) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"), excludeId));
            }

            // Filtro por pasajeros similares
            if (passengers != null && passengerTolerance != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("maxPassengers"), passengers - passengerTolerance),
                        criteriaBuilder.lessThanOrEqualTo(root.get("maxPassengers"), passengers + passengerTolerance)
                ));
            }

            // Filtro por rango similar
            if (range != null && rangeTolerance != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("rangeKm"), range - rangeTolerance),
                        criteriaBuilder.lessThanOrEqualTo(root.get("rangeKm"), range + rangeTolerance)
                ));
            }

            // Solo aeronaves activas
            predicates.add(criteriaBuilder.equal(root.get("isActive"), true));

            return criteriaBuilder.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}