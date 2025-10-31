package com.skyvault.backend.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public abstract class BaseSpecification<T> {

    /**
     * Crea un predicado LIKE case-insensitive
     */
    protected Predicate createLikePredicate(CriteriaBuilder cb, Expression<String> field, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return cb.like(cb.lower(field), "%" + value.toLowerCase().trim() + "%");
    }

    /**
     * Crea un predicado de igualdad null-safe
     */
    protected <V> Predicate createEqualPredicate(CriteriaBuilder cb, Expression<V> field, V value) {
        if (value == null) {
            return null;
        }

        return cb.equal(field, value);
    }

    /**
     * Crea un predicado IN para listas
     */
    protected <V> Predicate createInPredicate(Expression<V> field, List<V> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }

        return field.in(values);
    }

    /**
     * Crea un predicado de rango (mayor o igual que)
     */
    protected <V extends Comparable<V>> Predicate createGreaterThanOrEqualPredicate(
            CriteriaBuilder cb, Expression<V> field, V value) {
        if (value == null) {
            return null;
        }

        return cb.greaterThanOrEqualTo(field, value);
    }

    /**
     * Crea un predicado de rango (menor o igual que)
     */
    protected <V extends Comparable<V>> Predicate createLessThanOrEqualPredicate(
            CriteriaBuilder cb, Expression<V> field, V value) {
        if (value == null) {
            return null;
        }

        return cb.lessThanOrEqualTo(field, value);
    }

    /**
     * Crea un predicado booleano null-safe
     */
    protected Predicate createBooleanPredicate(CriteriaBuilder cb, Expression<Boolean> field, Boolean value) {
        if (value == null) {
            return null;
        }

        return cb.equal(field, value);
    }

    /**
     * Combina predicados con AND, ignorando nulls
     */
    protected Predicate combineWithAnd(CriteriaBuilder cb, Predicate... predicates) {
        Predicate result = null;

        for (Predicate predicate : predicates) {
            if (predicate != null) {
                result = (result == null) ? predicate : cb.and(result, predicate);
            }
        }

        return result;
    }

    /**
     * Combina predicados con OR, ignorando nulls
     */
    protected Predicate combineWithOr(CriteriaBuilder cb, Predicate... predicates) {
        Predicate result = null;

        for (Predicate predicate : predicates) {
            if (predicate != null) {
                result = (result == null) ? predicate : cb.or(result, predicate);
            }
        }

        return result;
    }

    /**
     * Crea un JOIN si no existe
     */
    protected <X, Y> Join<X, Y> getOrCreateJoin(Root<X> root, String attributeName, JoinType joinType) {
        // Verificar si el join ya existe
        for (Join<X, ?> join : root.getJoins()) {
            if (join.getAttribute().getName().equals(attributeName)) {
                @SuppressWarnings("unchecked")
                Join<X, Y> existingJoin = (Join<X, Y>) join;
                return existingJoin;
            }
        }

        // Crear nuevo join
        return root.join(attributeName, joinType);
    }

    /**
     * Crea un fetch si no existe
     */
    protected <X, Y> Fetch<X, Y> getOrCreateFetch(Root<X> root, String attributeName, JoinType joinType) {
        // Verificar si el fetch ya existe
        for (Fetch<X, ?> fetch : root.getFetches()) {
            if (fetch.getAttribute().getName().equals(attributeName)) {
                @SuppressWarnings("unchecked")
                Fetch<X, Y> existingFetch = (Fetch<X, Y>) fetch;
                return existingFetch;
            }
        }

        // Crear nuevo fetch
        return root.fetch(attributeName, joinType);
    }
}
