# Arquitectura SkyVault Backend

## Stack Tecnológico

### Core
- Spring Boot 3.2.0
- Java 17
- Maven

### Base de Datos
- PostgreSQL
- Spring Data JPA
- Flyway (migraciones)
- H2 (testing)

### Seguridad
- Spring Security
- JWT (JJWT 0.12.3)

### API
- SpringDoc OpenAPI 2.5.0
- Swagger UI
- REST

### Mapeo y Validación
- MapStruct 1.5.5
- Jakarta Validation

### Optimización
- Spring Cache
- Spring Actuator
- HikariCP

### Testing
- JUnit 5
- Testcontainers 1.19.0
- REST Assured

---

## Patrón Arquitectónico

**Layered Architecture + MVC**

```
┌─────────────────────┐
│   Frontend/Client   │
└──────────┬──────────┘
           │ HTTP
           ▼
┌─────────────────────┐
│    Controller       │  Endpoints REST
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│     Service         │  Lógica de negocio
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    Repository       │  Acceso a datos
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│    PostgreSQL       │
└─────────────────────┘
```

### Componentes Transversales
- **Mapper**: Entity ↔ DTO (MapStruct)
- **Exception Handler**: Manejo centralizado de errores
- **Security**: Autenticación/Autorización
- **Cache**: Optimización
- **Validation**: Validación multi-nivel

---

## Modelo de Datos

### Entidades Principales

```
Manufacturer (1) ──→ (N) Family (1) ──→ (N) AircraftModel
                                              │
                                              ├─→ (1:1) Specifications
                                              ├─→ (1:N) Image
                                              ├─→ (N:1) Type
                                              ├─→ (N:1) ProductionState
                                              └─→ (N:1) SizeCategory
```

### Tablas
- `aircraft_models` - Modelo de aeronave (principal)
- `manufacturers` - Fabricantes (Boeing, Airbus)
- `families` - Familias (737, A320)
- `specifications` - Especificaciones técnicas detalladas
- `images` - Imágenes de aeronaves
- `types` - Tipos (comercial, carga, privado)
- `production_states` - Estados (producción, descontinuado)
- `size_categories` - Categorías (narrow-body, wide-body)

---

## Flujo de Petición

1. **Request** → Controller recibe HTTP request
2. **Validación** → Valida DTOs de entrada
3. **Service** → Ejecuta lógica de negocio
4. **Repository** → Consulta base de datos
5. **Mapper** → Convierte Entity a DTO
6. **Response** → Retorna JSON al cliente

---

## Endpoints Principales

### Aeronaves
- `GET /api/v1/aircraft` - Listar con filtros
- `GET /api/v1/aircraft/{id}` - Detalle
- `POST /api/v1/aircraft` - Crear
- `PUT /api/v1/aircraft/{id}` - Actualizar
- `DELETE /api/v1/aircraft/{id}` - Eliminar
- `POST /api/v1/aircraft/compare` - Comparar

### Búsqueda
- `GET /api/v1/search` - Búsqueda global
- `GET /api/v1/search/suggestions` - Autocomplete

### Fabricantes/Familias
- `GET /api/v1/manufacturers`
- `GET /api/v1/families`

### Estadísticas
- `GET /api/v1/statistics`

### Admin
- `POST /admin/cache/clear`
- `GET /admin/cache/stats`

---

## Configuración

### Perfiles
- **dev** - Desarrollo local (logs detallados)
- **test** - Testing (H2 memoria)
- **prod** - Producción (PostgreSQL remoto)

### Base de Datos
```
URL: jdbc:postgresql://localhost:5432/skyvault
User: skyvault_user
Pass: skyvault_pass
```

### Comandos Maven
```bash
mvn spring-boot:run                    # Ejecutar
mvn spring-boot:run -Dspring-boot.run.profiles=dev  # Con perfil
mvn clean package                       # Generar JAR
mvn test                               # Tests
mvn flyway:migrate                     # Migraciones
```
