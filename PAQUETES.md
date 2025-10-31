# Estructura de Paquetes - SkyVault Backend

## 📦 Organización General

```
src/main/java/com/skyvault/backend/
├── config/           - Configuraciones del sistema
├── controller/       - REST Controllers (endpoints)
├── dto/             - Data Transfer Objects
├── model/           - Entidades JPA (tablas BD)
├── repository/      - Repositorios (acceso a datos)
├── service/         - Lógica de negocio
├── mapper/          - Conversión Entity ↔ DTO
├── specification/   - Queries dinámicos
├── validation/      - Validaciones personalizadas
├── exception/       - Manejo de errores
├── security/        - Seguridad (JWT, auth)
└── util/            - Utilidades
```

---

## 🔧 config/

**Propósito**: Configuraciones centralizadas del sistema

| Archivo | Función |
|---------|---------|
| `SecurityConfig.java` | Spring Security, JWT, autenticación |
| `CorsConfig.java` | CORS para peticiones del frontend |
| `CacheConfig.java` | Configuración de caché |
| `SwaggerConfig.java` | Documentación API OpenAPI |
| `JpaConfig.java` | JPA/Hibernate, dialecto PostgreSQL |
| `AsyncConfig.java` | Procesamiento asíncrono |
| `ActuatorConfig.java` | Monitoreo y health checks |

---

## 🎮 controller/

**Propósito**: Exponer endpoints REST

### api/ - Controllers públicos
- `AircraftController` - CRUD aeronaves, comparación
- `ManufacturerController` - Gestión fabricantes
- `FamilyController` - Gestión familias
- `SearchController` - Búsqueda global, sugerencias
- `StatisticsController` - Estadísticas y análisis
- `CatalogController` - Catálogo completo

### admin/ - Administración
- `AdminController` - Operaciones admin
- `CacheController` - Gestión de caché

### monitoring/ - Monitoreo
- `HealthController` - Estado del sistema
- `MetricsController` - Métricas

---

## 📦 dto/

**Propósito**: Transferencia de datos entre capas

### request/
DTOs de entrada con validaciones
- `AircraftCreateDto` - Crear aeronave
- `AircraftUpdateDto` - Actualizar aeronave
- `AircraftFilterDto` - Filtros de búsqueda
- `CompareRequestDto` - Comparar aeronaves
- `SearchRequestDto` - Búsqueda avanzada

### response/
DTOs de salida optimizados
- `AircraftDetailDto` - Detalles completos
- `AircraftCardDto` - Vista tarjeta (listados)
- `AircraftSummaryDto` - Resumen
- `CompareResultDto` - Resultado comparación
- `PagedResponseDto` - Respuestas paginadas
- `StatisticsDto` - Estadísticas

---

## 🗄️ model/

**Propósito**: Entidades JPA (mapean tablas BD)

| Entidad | Tabla | Descripción |
|---------|-------|-------------|
| `AircraftModel` | `aircraft_models` | Aeronave principal |
| `Manufacturer` | `manufacturers` | Fabricante |
| `Family` | `families` | Familia de aeronaves |
| `Specifications` | `specifications` | Specs técnicas |
| `Image` | `images` | Imágenes |
| `Type` | `types` | Tipo (comercial/carga) |
| `ProductionState` | `production_states` | Estado producción |
| `SizeCategory` | `size_categories` | Categoría tamaño |

### BaseEntity
Clase base con: `id`, `createdAt`, `updatedAt`, `version`

---

## 💾 repository/

**Propósito**: Acceso a datos (Spring Data JPA)

Cada repositorio extiende `JpaRepository` y proporciona:
- Métodos CRUD automáticos
- Queries personalizados
- Soporte para Specifications
- Paginación y ordenamiento

**Repositorios**:
- `AircraftModelRepository`
- `ManufacturerRepository`
- `FamilyRepository`
- `SpecificationsRepository`
- `ImageRepository`
- `TypeRepository`
- `ProductionStateRepository`
- `SizeCategoryRepository`

---

## 💼 service/

**Propósito**: Lógica de negocio

### Interfaces (raíz)
Contratos de servicios

### impl/ - Implementaciones
- `AircraftServiceImpl` - CRUD, filtros, búsqueda
- `ComparisonServiceImpl` - Comparación de aeronaves
- `SearchServiceImpl` - Búsqueda global, sugerencias
- `StatisticsServiceImpl` - Cálculo de estadísticas
- `CacheServiceImpl` - Gestión de caché
- `ManufacturerServiceImpl` - Gestión fabricantes
- `FamilyServiceImpl` - Gestión familias
- `ValidationServiceImpl` - Validaciones de negocio
- `NotificationServiceImpl` - Notificaciones

**Responsabilidades**:
- Transacciones (`@Transactional`)
- Validaciones de negocio
- Orquestación entre repositorios
- Transformación de datos

---

## 🔄 mapper/

**Propósito**: Conversión Entity ↔ DTO (MapStruct)

Genera código automáticamente en compilación

| Mapper | Función |
|--------|---------|
| `AircraftMapper` | AircraftModel ↔ DTOs |
| `ManufacturerMapper` | Manufacturer ↔ DTOs |
| `FamilyMapper` | Family ↔ DTOs |
| `SpecificationsMapper` | Specifications ↔ DTOs |
| `ComparisonMapper` | Genera DTOs de comparación |
| `SearchMapper` | Genera DTOs de búsqueda |

---

## 🔍 specification/

**Propósito**: Queries dinámicos (JPA Criteria API)

Permite filtros combinables sin SQL

- `AircraftSpecification` - Filtros de aeronaves
- `FamilySpecification` - Filtros de familias
- `ManufacturerSpecification` - Filtros de fabricantes
- `SpecificationUtils` - Utilidades comunes

**Ejemplo**:
```java
Specification.where(hasManufacturer("Boeing"))
    .and(rangeGreaterThan(5000))
    .and(passengersBetween(150, 300))
```

---

## ✅ validation/

**Propósito**: Validaciones personalizadas

### Anotaciones custom
- `@ValidAircraftModel`
- `@ValidIds`
- `@ValidPagination`
- `@ValidRange`
- `@ValidSortField`

### Validadores
- `AircraftModelValidator`
- `PaginationValidator`
- `RangeValidator`
- `IdsValidator`

### Otros
- `ValidationGroups` - Grupos (Create, Update)
- `ValidationMessages` - Mensajes de error
- `ValidationResult` - Resultado de validación

---

## ⚠️ exception/

**Propósito**: Manejo centralizado de errores

### Jerarquía
```
BaseException
├── ResourceNotFoundException (404)
├── BadRequestException (400)
├── ConflictException (409)
├── ValidationException (422)
├── TooManyRequestsException (429)
└── InternalServerErrorException (500)
```

### Handler
- `GlobalExceptionHandler` - Intercepta excepciones
- `ProblemDetail` - Formato RFC 7807

---

## 🔒 security/

**Propósito**: Infraestructura de seguridad

### config/
- `RateLimitingConfig` - Límites de peticiones
- `SecurityPropertiesConfig` - Propiedades de seguridad

### dto/
- `RateLimitDto`
- `SecurityViolationDto`

### En desarrollo
- `jwt/` - Generación y validación JWT
- `filter/` - Filtros de seguridad
- `service/` - Autenticación/Autorización

---

## 🛠️ util/

**Propósito**: Utilidades reutilizables

| Clase | Función |
|-------|---------|
| `StringUtils` | Manipulación de strings |
| `DateUtils` | Operaciones con fechas |
| `NumberUtils` | Operaciones numéricas |
| `CollectionUtils` | Operaciones con listas |
| `ValidationUtils` | Validaciones auxiliares |

---

## 📂 resources/

```
resources/
├── application.properties          - Config base
├── application-dev.properties      - Config desarrollo
├── application-prod.properties     - Config producción
├── db/
│   └── migration/                  - Scripts Flyway
├── data/                           - Datos iniciales
└── static/                         - Recursos estáticos
```

---

## 🧪 test/

```
test/java/com/skyvault/backend/
├── controller/      - Tests de controllers
├── service/         - Tests de servicios
├── repository/      - Tests de repositorios
├── integration/     - Tests de integración
└── util/           - Tests de utilidades
```
