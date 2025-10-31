package com.skyvault.backend.repository;

import com.skyvault.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>,
        JpaSpecificationExecutor<Image> {

    // Imágenes por modelo de aeronave
    List<Image> findByAircraftModelIdOrderByDisplayOrderAsc(Long aircraftModelId);

    // Imagen principal de un modelo
    Optional<Image> findByAircraftModelIdAndIsPrimaryTrue(Long aircraftModelId);

    // Imágenes por tipo
    List<Image> findByAircraftModelIdAndImageTypeOrderByDisplayOrderAsc(Long aircraftModelId, String imageType);

    // Imagen thumbnail principal
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id = :aircraftModelId " +
            "AND i.imageType = 'thumbnail' " +
            "AND i.isPrimary = true " +
            "ORDER BY i.displayOrder ASC")
    Optional<Image> findThumbnailByAircraftModelId(@Param("aircraftModelId") Long aircraftModelId);

    // Primera imagen disponible como thumbnail
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id = :aircraftModelId " +
            "AND i.imageType = 'thumbnail' " +
            "ORDER BY i.displayOrder ASC " +
            "LIMIT 1")
    Optional<Image> findFirstThumbnailByAircraftModelId(@Param("aircraftModelId") Long aircraftModelId);

    // Imágenes para comparación
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id IN :aircraftModelIds " +
            "AND i.isComparison = true " +
            "ORDER BY i.aircraftModel.id, i.displayOrder ASC")
    List<Image> findComparisonImagesByAircraftModelIds(@Param("aircraftModelIds") List<Long> aircraftModelIds);

    // Imágenes principales para múltiples modelos
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id IN :aircraftModelIds " +
            "AND i.isPrimary = true " +
            "ORDER BY i.aircraftModel.id")
    List<Image> findPrimaryImagesByAircraftModelIds(@Param("aircraftModelIds") List<Long> aircraftModelIds);

    // Tipos de imagen disponibles para un modelo
    @Query("SELECT DISTINCT i.imageType FROM Image i " +
            "WHERE i.aircraftModel.id = :aircraftModelId " +
            "ORDER BY i.imageType")
    List<String> findDistinctImageTypesByAircraftModelId(@Param("aircraftModelId") Long aircraftModelId);

    // Contar imágenes por modelo
    long countByAircraftModelId(Long aircraftModelId);

    // Contar imágenes por tipo
    long countByAircraftModelIdAndImageType(Long aircraftModelId, String imageType);

    // Verificar si existe imagen principal
    boolean existsByAircraftModelIdAndIsPrimaryTrue(Long aircraftModelId);

    // Verificar si existe thumbnail
    boolean existsByAircraftModelIdAndImageType(Long aircraftModelId, String imageType);

    // Imágenes más recientes
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id = :aircraftModelId " +
            "ORDER BY i.createdAt DESC")
    List<Image> findRecentImagesByAircraftModelId(@Param("aircraftModelId") Long aircraftModelId);

    // Imágenes optimizadas para web
    @Query("SELECT i FROM Image i " +
            "WHERE i.aircraftModel.id = :aircraftModelId " +
            "AND (i.fileFormat = 'webp' OR i.fileFormat = 'avif') " +
            "ORDER BY i.displayOrder ASC")
    List<Image> findWebOptimizedImagesByAircraftModelId(@Param("aircraftModelId") Long aircraftModelId);

    // Estadísticas de imágenes
    @Query("SELECT i.imageType, COUNT(i) FROM Image i GROUP BY i.imageType ORDER BY COUNT(i) DESC")
    List<Object[]> getImageTypeStatistics();

    // Formatos de archivo disponibles
    @Query("SELECT DISTINCT i.fileFormat FROM Image i WHERE i.fileFormat IS NOT NULL ORDER BY i.fileFormat")
    List<String> findDistinctFileFormats();

    // Imágenes sin modelo de aeronave (para limpieza)
    @Query("SELECT i FROM Image i WHERE i.aircraftModel IS NULL")
    List<Image> findOrphanImages();

    // URLs de imágenes duplicadas
    @Query("SELECT i.url, COUNT(i) FROM Image i GROUP BY i.url HAVING COUNT(i) > 1")
    List<Object[]> findDuplicateImageUrls();
}
