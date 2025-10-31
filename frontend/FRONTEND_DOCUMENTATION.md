# 📚 Documentación Frontend - SkyVault

## 📋 Tabla de Contenidos
- [Información General](#información-general)
- [Stack Tecnológico](#stack-tecnológico)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Estructura de Carpetas](#estructura-de-carpetas)
- [Consumo de API](#consumo-de-api)
- [Sistema de Diseño](#sistema-de-diseño)
- [Consideraciones Técnicas](#consideraciones-técnicas)

---

## 🎯 Información General

**Proyecto:** SkyVault - Catálogo de Aeronaves Comerciales  
**Versión:** 1.5.10.2025  
**Tipo:** Single Page Application (SPA)  
**Puerto de desarrollo:** 5173  
**API Base URL:** `http://localhost:8080/api/v1`

---

## 🛠️ Stack Tecnológico

### Core Technologies
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **React** | 19.1.1 | Framework UI principal |
| **TypeScript** | ~5.9.3 | Tipado estático y type safety |
| **Vite** | ^7.1.7 | Build tool y dev server |
| **React Router DOM** | ^7.9.4 | Enrutamiento y navegación |

### Bibliotecas UI y Animaciones
| Biblioteca | Versión | Uso |
|------------|---------|-----|
| **GSAP** | ^3.13.0 | Animaciones complejas y efectos visuales |
| **Heroicons React** | ^2.2.0 | Sistema de iconografía |

### HTTP y Data Fetching
| Tool | Versión | Propósito |
|------|---------|-----------|
| **Axios** | ^1.13.1 | Cliente HTTP para consumo de API REST |

### Herramientas de Desarrollo
| Herramienta | Versión | Función |
|-------------|---------|---------|
| **ESLint** | ^9.36.0 | Linting y calidad de código |
| **TypeScript ESLint** | ^8.45.0 | Reglas específicas para TypeScript |

### Sistema de Estilos
- **CSS Modules** - Estilos con scope local
- **CSS Variables** - Tokens de diseño y theming
- **Vanilla CSS** - Sin preprocesadores ni CSS-in-JS

---

## 🏗️ Arquitectura del Proyecto

```
frontend/
│
├── 📁 public/                    → Assets estáticos (imágenes, favicons)
│
├── 📁 src/
│   │
│   ├── 📁 app/                   → Configuración principal de la aplicación
│   │   ├── 📁 layouts/          → Layouts reutilizables (MainLayout, etc.)
│   │   └── 📁 routes/           → Configuración de rutas
│   │
│   ├── 📁 features/             → Módulos de funcionalidad (Feature-based)
│   │   │
│   │   ├── 📁 aircraft/         → Funcionalidad de aeronaves
│   │   │   ├── 📁 components/  → Componentes específicos de aeronaves
│   │   │   ├── 📁 hooks/       → Custom hooks (useAircraft, usePagination)
│   │   │   ├── 📁 services/    → aircraftService.ts (Consumo API)
│   │   │   ├── 📁 types/       → Tipos TypeScript (DTOs, interfaces)
│   │   │   └── 📁 utils/       → Utilidades específicas
│   │   │
│   │   ├── 📁 auth/            → Autenticación (futuro)
│   │   │   ├── 📁 components/
│   │   │   ├── 📁 hooks/
│   │   │   ├── 📁 services/
│   │   │   └── 📁 types/
│   │   │
│   │   ├── 📁 comparison/       → Comparación de aeronaves
│   │   │   ├── 📁 services/    → comparisonService.ts
│   │   │   └── 📁 types/
│   │   │
│   │   ├── 📁 families/         → Familias de aeronaves
│   │   │   ├── 📁 services/    → familyService.ts
│   │   │   └── 📁 types/
│   │   │
│   │   └── 📁 news/             → Sistema de noticias
│   │       ├── 📁 components/
│   │       └── 📁 types/
│   │
│   ├── 📁 pages/                → Componentes de página (route components)
│   │   ├── 📁 Home/            → Página principal
│   │   ├── 📁 AircraftCatalog/ → Catálogo de aeronaves
│   │   ├── 📁 AircraftDetail/  → Detalle de aeronave
│   │   ├── 📁 ComparePage/     → Comparador
│   │   ├── 📁 News/            → Listado de noticias
│   │   ├── 📁 Families/        → Familias de aeronaves
│   │   ├── 📁 Manufacturers/   → Fabricantes
│   │   ├── 📁 About/           → Acerca de
│   │   ├── 📁 Contact/         → Contacto
│   │   ├── 📁 Privacy/         → Política de privacidad
│   │   ├── 📁 Terms/           → Términos y condiciones
│   │   └── ... (otras páginas legales)
│   │
│   ├── 📁 shared/               → Código compartido entre features
│   │   ├── 📁 components/      → Componentes reutilizables
│   │   │   ├── 📁 layout/      → Header, Footer, Sidebar
│   │   │   └── 📁 ui/          → Button, Card, Modal, etc.
│   │   ├── 📁 contexts/        → React Contexts (ThemeContext)
│   │   ├── 📁 hooks/           → Custom hooks globales
│   │   ├── 📁 types/           → Tipos compartidos
│   │   └── 📁 utils/           → Funciones de utilidad
│   │
│   ├── 📁 config/               → Configuraciones
│   │   ├── aircraftImageMapping.ts  → Mapeo de imágenes
│   │   └── aircraftImagesData.ts    → Data de imágenes
│   │
│   ├── 📁 lib/                  → Configuración de librerías externas
│   │   └── gsap.config.ts      → Setup de GSAP
│   │
│   ├── 📁 styles/               → Estilos globales y tokens
│   │   ├── variables.css       → CSS Variables (Design Tokens)
│   │   ├── global.css          → Estilos globales
│   │   └── 📁 tokens/          → Tokens de diseño adicionales
│   │
│   ├── 📁 assets/               → Imágenes, SVGs, fonts
│   ├── 📁 constants/            → Constantes de la aplicación
│   ├── 📁 data/                 → Data estática o mock data
│   │
│   ├── App.tsx                  → Componente raíz (Router setup)
│   ├── main.tsx                → Entry point (ReactDOM.render)
│   └── index.css               → Estilos base
│
├── vite.config.ts              → Configuración de Vite
├── tsconfig.json               → Configuración TypeScript
├── package.json                → Dependencias y scripts
└── README.md                   → Documentación inicial

```

---

## 📂 Estructura de Carpetas Detallada

### 1. `/src/features/` - Arquitectura por Features

**Propósito:** Organización modular donde cada feature es autocontenido y escalable.

#### 🛩️ **feature/aircraft/**
**Maneja:** Catálogo, detalle, búsqueda y gestión de aeronaves

```
aircraft/
├── components/          → AircraftCard, AircraftFilters, AircraftGrid
├── hooks/              → useAircraft, useAircraftFilters, usePagination
├── services/           → aircraftService.ts (API calls)
├── types/              → aircraft.types.ts (DTOs e interfaces)
└── utils/              → Funciones auxiliares de aeronaves
```

**Consume API:**
- `GET /api/v1/aircraft` - Lista paginada de aeronaves
- `GET /api/v1/aircraft/:id` - Detalle de aeronave por ID
- `GET /api/v1/aircraft/:slug` - Detalle por slug
- `GET /api/v1/aircraft/search` - Búsqueda de aeronaves
- `GET /api/v1/aircraft/popular` - Aeronaves populares
- `GET /api/v1/aircraft/featured` - Aeronaves destacadas
- `GET /api/v1/aircraft/:id/similar` - Aeronaves similares
- `GET /api/v1/manufacturers/summary` - Lista de fabricantes
- `GET /api/v1/families` - Lista de familias

**Archivo principal:** `src/features/aircraft/services/aircraftService.ts`

---

#### 🔄 **feature/comparison/**
**Maneja:** Comparación lado a lado de aeronaves

```
comparison/
├── services/           → comparisonService.ts
└── types/              → comparison.types.ts
```

**Consume API:**
- `GET /api/v1/aircraft/compare?ids=1,2,3` - Comparación de aeronaves
- `GET /api/v1/search/suggest` - Sugerencias de búsqueda

**Archivo principal:** `src/features/comparison/services/comparisonService.ts`

---

#### 👨‍👩‍👧‍👦 **feature/families/**
**Maneja:** Familias de aeronaves (A320, 737, etc.)

```
families/
├── services/           → familyService.ts
└── types/              → family.types.ts
```

**Consume API:**
- `GET /api/v1/families`
- `GET /api/v1/families/:id`

**Archivo principal:** `src/features/families/services/familyService.ts`

---

#### 📰 **feature/news/**
**Maneja:** Sistema de noticias de aviación

```
news/
├── components/         → NewsCard, NewsList
└── types/              → news.types.ts
```

**Consume API:** (Futuro endpoint de noticias)

---

### 2. `/src/pages/` - Componentes de Ruta

**Propósito:** Componentes que representan páginas completas

| Página | Ruta | Descripción |
|--------|------|-------------|
| **Home** | `/` | Landing page principal |
| **AircraftCatalog** | `/aircraft`, `/catalog` | Catálogo con filtros y paginación |
| **AircraftDetail** | `/aircraft/:identifier` | Detalle completo de una aeronave |
| **ComparisonSelector** | `/compare-select` | Selector de aeronaves a comparar |
| **ComparePage** | `/compare` | Página de comparación |
| **News** | `/news` | Lista de noticias |
| **NewsDetail** | `/news/:id` | Detalle de noticia |
| **Families** | `/families` | Explorar familias de aeronaves |
| **Manufacturers** | `/manufacturers` | Explorar fabricantes |
| **About** | `/about` | Acerca de SkyVault |
| **Contact** | `/contact` | Formulario de contacto |
| **Privacy** | `/privacy` | Política de privacidad |
| **Terms** | `/terms` | Términos y condiciones |
| **Cookies** | `/cookies` | Política de cookies |
| **FAQ** | `/faq` | Preguntas frecuentes |
| **APIPage** | `/api` | Documentación de API |
| **DataSources** | `/sources` | Fuentes de datos |

---

### 3. `/src/shared/` - Código Compartido

**Propósito:** Componentes, hooks y utilidades reutilizables

```
shared/
├── components/
│   ├── layout/         → Header, Footer, Navigation, Sidebar
│   └── ui/             → Button, Card, Modal, Input, Badge
├── contexts/
│   └── ThemeContext    → Gestión de tema (light/dark/minimal)
├── hooks/              → useTheme, useDebounce, useIntersection
├── types/              → Tipos globales compartidos
└── utils/              → Helpers, formatters, validators
```

---

### 4. `/src/styles/` - Sistema de Diseño

**Propósito:** Tokens de diseño y estilos globales

```
styles/
├── variables.css       → CSS Variables (design tokens)
├── global.css          → Reset y estilos globales
└── tokens/             → Tokens específicos (colores, spacing)
```

**Design Tokens Implementados:**
- **Spacing:** `--spacing-xs` a `--spacing-3xl`
- **Border Radius:** `--radius-sm` a `--radius-xl`
- **Typography:** `--font-primary`, font-weights
- **Transitions:** `--transition-fast/normal/slow`
- **Color Palette:** Variables de color temáticas
- **Shadows:** `--shadow-sm/md/lg/card`

**Temas Disponibles:**
1. **Light** (Default) - Tema claro con paleta azul
2. **Dark** - Tema oscuro adaptado
3. **Minimal** - Tema minimalista monocromático

---

### 5. `/src/config/` - Configuraciones

**Propósito:** Archivos de configuración estática

```
config/
├── aircraftImageMapping.ts    → Mapeo de imágenes por aeronave
└── aircraftImagesData.ts      → URLs de imágenes
```

---

### 6. `/src/lib/` - Configuración de Librerías

**Propósito:** Setup de librerías de terceros

```
lib/
└── gsap.config.ts    → Configuración y setup de GSAP
```

---

## 🔌 Consumo de API

### Base URL
```typescript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';
```

### Servicios Principales

#### 1. **Aircraft Service** (`src/features/aircraft/services/aircraftService.ts`)

**Responsabilidades:**
- Obtener aeronaves con filtros y paginación
- Búsqueda de aeronaves
- Detalles de aeronave específica
- Aeronaves populares y destacadas
- Aeronaves similares

**Endpoints consumidos:**

```typescript
// Catálogo paginado con filtros
GET /api/v1/aircraft
  ?page=0
  &size=20
  &manufacturerId=1
  &familyId=2
  &searchTerm=A350
  &onlyActive=true
  &sortField=name
  &sortDirection=ASC

// Detalle de aeronave
GET /api/v1/aircraft/:identifier  // ID numérico o slug

// Búsqueda
GET /api/v1/aircraft/search?q=airbus&limit=20

// Aeronaves populares
GET /api/v1/aircraft/popular?limit=10

// Aeronaves destacadas
GET /api/v1/aircraft/featured?limit=8

// Aeronaves similares
GET /api/v1/aircraft/:id/similar
  ?passengerTolerance=50
  &rangeTolerance=1000
  &limit=6
  &onlyActive=true

// Fabricantes
GET /api/v1/manufacturers/summary?onlyActive=true

// Familias
GET /api/v1/families?manufacturerId=1
```

**Funciones exportadas:**
- `getAircraft()` - Obtiene aeronaves con filtros
- `getAircraftById()` - Detalle por ID
- `getAircraftDetail()` - Detalle por ID o slug
- `searchAircraft()` - Búsqueda por término
- `getPopularAircraft()` - Top aeronaves
- `getFeaturedAircraft()` - Aeronaves destacadas
- `getSimilarAircraft()` - Aeronaves similares
- `getManufacturers()` - Lista de fabricantes
- `getFamilies()` - Lista de familias

---

#### 2. **Comparison Service** (`src/features/comparison/services/comparisonService.ts`)

**Responsabilidades:**
- Comparar 2-3 aeronaves lado a lado
- Buscar aeronaves para comparación
- Agrupar aeronaves por fabricante

**Endpoints consumidos:**

```typescript
// Comparación de aeronaves
GET /api/v1/aircraft/compare
  ?ids=1,2,3
  &includeSpecifications=true
  &includeImages=true
  &normalizeUnits=true
  &unitFormat=metric

// Sugerencias de búsqueda
GET /api/v1/search/suggest
  ?q=boeing
  &limit=10
  &type=aircraft
  &onlyActive=true

// Obtener todas las aeronaves para selector
GET /api/v1/aircraft?size=100&page=0&sort=name,asc
```

**Funciones exportadas:**
- `fetchComparisonData()` - Compara aeronaves
- `searchAircraft()` - Busca aeronaves para comparar
- `getGroupedAircraft()` - Agrupa por fabricante

---

#### 3. **Family Service** (`src/features/families/services/familyService.ts`)

**Responsabilidades:**
- Gestión de familias de aeronaves (A320, 737, etc.)

**Endpoints consumidos:**

```typescript
// Lista de familias
GET /api/v1/families

// Familias por fabricante
GET /api/v1/families?manufacturerId=1

// Detalle de familia
GET /api/v1/families/:id
```

---

### Manejo de Errores en Servicios

Todos los servicios implementan manejo de errores consistente:

```typescript
try {
    const response = await fetch(url, options);
    
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}: ${response.statusText}`);
    }
    
    return await response.json();
} catch (error) {
    console.error('❌ Error in service:', error);
    throw error;
}
```

---

### Variables de Entorno

**Archivo:** `.env` (no incluido en git)

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

**Uso en código:**
```typescript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
```

---

## 🎨 Sistema de Diseño

### Design Philosophy
- **Neomorphism** - Interfaces con profundidad y sombras suaves
- **Liquid Glass** - Efectos de vidrio y transparencias
- **Minimalismo Elegante** - Diseño limpio y funcional

### Tokens de Diseño (CSS Variables)

#### Spacing Scale
```css
--spacing-xs: 4px
--spacing-sm: 8px
--spacing-md: 16px
--spacing-lg: 24px
--spacing-xl: 32px
--spacing-2xl: 48px
--spacing-3xl: 64px
```

#### Border Radius
```css
--radius-sm: 8px
--radius-md: 12px
--radius-lg: 16px
--radius-xl: 20px
```

#### Typography
```css
--font-primary: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif
--font-weight-normal: 400
--font-weight-medium: 500
--font-weight-semibold: 600
--font-weight-bold: 700
```

#### Transitions
```css
--transition-fast: 150ms cubic-bezier(0.4, 0, 0.2, 1)
--transition-normal: 300ms cubic-bezier(0.4, 0, 0.2, 1)
--transition-slow: 500ms cubic-bezier(0.4, 0, 0.2, 1)
```

### Sistema de Temas

#### Light Theme (Default)
- Background principal: `#D3E9FF`
- Color primario: `#0d4b7a`
- Textos: `#1d1d1f`
- Bordes: `rgba(13, 75, 122, 0.15)`

#### Dark Theme
- Background principal: `#0A1929`
- Color primario: `#4A9FD8`
- Textos: `#E3E8EF`
- Bordes: `rgba(178, 229, 255, 0.15)`

#### Minimal Theme
- Background principal: `#F8F9FA`
- Color primario: `#5A6C7D`
- Textos: `#2C3E50`
- Bordes: `rgba(90, 108, 125, 0.18)`

### ThemeContext Implementation

**Ubicación:** `src/shared/contexts/ThemeContext`

**Uso:**
```tsx
import { useTheme } from '@/shared/contexts/ThemeContext';

function MyComponent() {
    const { theme, setTheme } = useTheme();
    
    return (
        <button onClick={() => setTheme('dark')}>
            Switch to Dark
        </button>
    );
}
```

---

## ⚙️ Consideraciones Técnicas

### 1. **Arquitectura Feature-Based**
- Cada feature es autocontenido
- Facilita escalabilidad y mantenimiento
- Código organizado por dominio de negocio

### 2. **Type Safety con TypeScript**
- Interfaces para todos los DTOs de API
- Tipos estrictos en servicios y componentes
- IntelliSense mejorado en desarrollo

### 3. **Separación de Responsabilidades**
- **Services:** Lógica de API y data fetching
- **Hooks:** Lógica de estado y side effects
- **Components:** Solo presentación y UI
- **Utils:** Funciones puras reutilizables

### 4. **Paginación y Filtros**
- Implementado en `aircraftService.ts`
- Soporte para filtros múltiples
- Ordenamiento configurable
- Respuesta paginada del backend

### 5. **Optimización de Performance**
- Code splitting por rutas
- Lazy loading de componentes
- Imágenes optimizadas
- GSAP para animaciones fluidas

### 6. **Accesibilidad**
- Soporte para `prefers-reduced-motion`
- Contraste de colores WCAG AA
- Navegación por teclado
- ARIA labels donde sea necesario

### 7. **SEO y Meta Tags**
- React Helmet para meta tags dinámicos
- URLs amigables con slugs
- Open Graph tags configurados

### 8. **Environment Configuration**
- Variables de entorno con Vite
- Configuración por ambiente (dev/prod)
- API base URL configurable

### 9. **Error Handling**
- Try-catch en todos los servicios
- Logging consistente
- Mensajes de error user-friendly

### 10. **Testing Strategy** (Futuro)
- Carpeta `_tests_` preparada
- Jest + React Testing Library
- Unit tests para servicios
- Integration tests para features

---

## 📦 Scripts Disponibles

```bash
# Desarrollo
npm run dev          # Inicia servidor en puerto 5173

# Build
npm run build        # Compila para producción

# Linting
npm run lint         # Verifica código con ESLint

# Preview
npm run preview      # Preview del build de producción
```

---

## 🔐 Seguridad

### CORS
- Configurado en Vite para desarrollo
- Backend debe habilitar CORS para producción

### Variables de Entorno
- No commitear `.env` en git
- Usar `.env.example` como template

### Sanitización de Inputs
- Validación en cliente y servidor
- Protección contra XSS
- Escape de HTML en contenido dinámico

---

## 🚀 Roadmap Técnico

### Corto Plazo
- [ ] Implementar autenticación (feature/auth)
- [ ] Tests unitarios para servicios
- [ ] Optimización de imágenes con CDN
- [ ] Error boundary components

### Mediano Plazo
- [ ] Progressive Web App (PWA)
- [ ] Server-Side Rendering (SSR) con Vite SSR
- [ ] Internacionalización (i18n)
- [ ] Analytics integration

### Largo Plazo
- [ ] Migración a React Server Components
- [ ] GraphQL para data fetching
- [ ] Offline support
- [ ] Real-time updates con WebSockets

---

## 📞 Contacto y Soporte

**Desarrollador:** OUTTARY (Julián Espitia y Mónica Vellojin)
**Tecnologías Preferidas:** Frontend, Java, JavaScript, TypeScript, Swift, Kotlin, C++, Cangjie
**Diseño:** Neomorphism, Liquid Glass, Aqua Design, Minimalismo, Flat Design  
**Idiomas:** Español, Inglés
**Herramientas:** Figma, Notion, Linear, Rave, Spline, Blender, Framer, After Effects

---

## 📝 Notas Finales

Este frontend está construido con las mejores prácticas de desarrollo moderno:
- ✅ Arquitectura escalable y mantenible
- ✅ Type safety con TypeScript
- ✅ Diseño responsive y accesible
- ✅ Performance optimizado
- ✅ Código limpio y documentado

**Última actualización:** Octubre 2025
