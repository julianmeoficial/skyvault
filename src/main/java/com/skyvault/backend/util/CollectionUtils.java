package com.skyvault.backend.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Verifica si una colección es null o vacía
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Verifica si una colección no es null ni vacía
     */
    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    /**
     * Obtiene el tamaño seguro de una colección
     */
    public static <T> int safeSize(Collection<T> collection) {
        return collection != null ? collection.size() : 0;
    }

    /**
     * Obtiene el primer elemento de una lista de forma segura
     */
    public static <T> Optional<T> getFirst(List<T> list) {
        if (isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(0));
    }

    /**
     * Obtiene el último elemento de una lista de forma segura
     */
    public static <T> Optional<T> getLast(List<T> list) {
        if (isEmpty(list)) {
            return Optional.empty();
        }

        return Optional.ofNullable(list.get(list.size() - 1));
    }

    /**
     * Crea una lista inmutable de forma segura
     */
    @SafeVarargs
    public static <T> List<T> safeList(T... elements) {
        if (elements == null || elements.length == 0) {
            return List.of();
        }

        return Arrays.stream(elements)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una colección nullable en lista inmutable
     */
    public static <T> List<T> safeList(Collection<T> collection) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Crea un set inmutable de forma segura
     */
    @SafeVarargs
    public static <T> Set<T> safeSet(T... elements) {
        if (elements == null || elements.length == 0) {
            return Set.of();
        }

        return Arrays.stream(elements)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Filtra elementos null de una colección
     */
    public static <T> List<T> filterNull(Collection<T> collection) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Filtra elementos según un predicado
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Mapea elementos de una colección
     */
    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Divide una lista en sublistas de tamaño específico
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list) || size <= 0) {
            return List.of();
        }

        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }

        return partitions;
    }

    /**
     * Encuentra elementos duplicados en una colección
     */
    public static <T> Set<T> findDuplicates(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Set.of();
        }

        Set<T> seen = new HashSet<>();
        Set<T> duplicates = new HashSet<>();

        for (T item : collection) {
            if (item != null && !seen.add(item)) {
                duplicates.add(item);
            }
        }

        return duplicates;
    }

    /**
     * Encuentra elementos únicos (sin duplicados)
     */
    public static <T> List<T> distinct(Collection<T> collection) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Intersección de dos colecciones
     */
    public static <T> Set<T> intersection(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1) || isEmpty(collection2)) {
            return Set.of();
        }

        Set<T> set1 = new HashSet<>(collection1);
        set1.retainAll(collection2);

        return set1;
    }

    /**
     * Unión de dos colecciones
     */
    public static <T> Set<T> union(Collection<T> collection1, Collection<T> collection2) {
        Set<T> result = new HashSet<>();

        if (isNotEmpty(collection1)) {
            result.addAll(collection1);
        }

        if (isNotEmpty(collection2)) {
            result.addAll(collection2);
        }

        return result;
    }

    /**
     * Diferencia entre dos colecciones (elementos en la primera pero no en la segunda)
     */
    public static <T> Set<T> difference(Collection<T> collection1, Collection<T> collection2) {
        if (isEmpty(collection1)) {
            return Set.of();
        }

        Set<T> result = new HashSet<>(collection1);

        if (isNotEmpty(collection2)) {
            result.removeAll(collection2);
        }

        return result;
    }

    /**
     * Convierte lista de IDs en string separado por comas
     */
    public static String joinIds(Collection<Long> ids) {
        if (isEmpty(ids)) {
            return "";
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * Convierte string separado por comas en lista de IDs
     */
    public static List<Long> parseIds(String idsString) {
        if (StringUtils.isEmpty(idsString)) {
            return List.of();
        }

        return Arrays.stream(idsString.split(","))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .map(id -> {
                    try {
                        return Long.parseLong(id);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Ordena una colección de forma segura
     */
    public static <T extends Comparable<T>> List<T> sort(Collection<T> collection) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Ordena una colección con un comparador personalizado
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        if (isEmpty(collection)) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Toma los primeros N elementos de una colección
     */
    public static <T> List<T> take(Collection<T> collection, int count) {
        if (isEmpty(collection) || count <= 0) {
            return List.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Omite los primeros N elementos de una colección
     */
    public static <T> List<T> skip(Collection<T> collection, int count) {
        if (isEmpty(collection) || count <= 0) {
            return safeList(collection);
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .skip(count)
                .collect(Collectors.toList());
    }

    /**
     * Agrupa elementos por una función key
     */
    public static <T, K> Map<K, List<T>> groupBy(Collection<T> collection, Function<T, K> keyExtractor) {
        if (isEmpty(collection)) {
            return Map.of();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(keyExtractor));
    }

    /**
     * Verifica si todos los elementos cumplen una condición
     */
    public static <T> boolean all(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return true;
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .allMatch(predicate);
    }

    /**
     * Verifica si algún elemento cumple una condición
     */
    public static <T> boolean any(Collection<T> collection, Predicate<T> predicate) {
        if (isEmpty(collection)) {
            return false;
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .anyMatch(predicate);
    }
}
