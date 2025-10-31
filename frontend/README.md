# ✈️ SkyVault - Aircraft Catalog & Comparison Platform

<div align="center">

![SkyVault](https://img.shields.io/badge/SkyVault-Aircraft%20Catalog-0d4b7a?style=for-the-badge)
![React](https://img.shields.io/badge/React-19.1.1-61DAFB?style=for-the-badge&logo=react)
![TypeScript](https://img.shields.io/badge/TypeScript-5.9.3-3178C6?style=for-the-badge&logo=typescript)
![Vite](https://img.shields.io/badge/Vite-7.1.7-646CFF?style=for-the-badge&logo=vite)

**Una plataforma moderna para explorar, comparar y analizar aeronaves comerciales.**

[Demo](#) • [Documentación](./FRONTEND_DOCUMENTATION.md) • [API Docs](#) • [Reportar Bug](#)

</div>

---

## 🌟 Características Principales

- 🛩️ **Catálogo Completo** - Explora cientos de aeronaves comerciales con filtros avanzados
- 🔄 **Comparador Inteligente** - Compara hasta 3 aeronaves lado a lado
- 📊 **Especificaciones Técnicas** - Información detallada de rendimiento, capacidad y dimensiones
- 🏢 **Fabricantes y Familias** - Navega por fabricantes (Airbus, Boeing) y familias de aeronaves
- 🎨 **Temas Personalizables** - Light, Dark y Minimal con diseño Neomorphic y Liquid Glass
- 📱 **Responsive Design** - Experiencia optimizada en desktop, tablet y móvil
- ⚡ **Performance Optimizado** - Carga rápida con lazy loading y code splitting
- 🎭 **Animaciones Fluidas** - Powered by GSAP para transiciones suaves

---

## 🛠️ Stack Tecnológico

### Core
- **React 19.1.1** - Framework UI principal
- **TypeScript 5.9.3** - Type safety y mejor DX
- **Vite 7.1.7** - Build tool ultra-rápido
- **React Router DOM 7.9.4** - Enrutamiento SPA

### Librerías
- **GSAP 3.13.0** - Animaciones de alta performance
- **Axios 1.13.1** - Cliente HTTP para API REST
- **Heroicons React 2.2.0** - Sistema de iconografía

### Estilos
- **CSS Modules** - Estilos con scope local
- **CSS Variables** - Design tokens y theming
- **Neomorphism & Liquid Glass** - Filosofía de diseño

---

## 🚀 Inicio Rápido

### Prerrequisitos

- Node.js 18+ y npm 9+
- Backend API corriendo en `http://localhost:8080` (o configura `VITE_API_BASE_URL`)

### Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/skyvault.git
cd skyvault/frontend

# 2. Instalar dependencias
npm install

# 3. Configurar variables de entorno
cp .env.example .env
# Edita .env con tus configuraciones

# 4. Iniciar servidor de desarrollo
npm run dev
```

La aplicación estará disponible en `http://localhost:5173`

---

## 📁 Estructura del Proyecto

```
frontend/
├── src/
│   ├── app/              # Configuración de la app (layouts, routes)
│   ├── features/         # Módulos por funcionalidad
│   │   ├── aircraft/    # Catálogo y detalle de aeronaves
│   │   ├── comparison/  # Sistema de comparación
│   │   ├── families/    # Familias de aeronaves
│   │   ├── news/        # Noticias de aviación
│   │   └── auth/        # Autenticación (futuro)
│   ├── pages/            # Componentes de página (routes)
│   ├── shared/           # Código compartido
│   │   ├── components/  # UI components reutilizables
│   │   ├── contexts/    # React Contexts (Theme, etc.)
│   │   ├── hooks/       # Custom hooks
│   │   └── utils/       # Utilidades
│   ├── styles/           # Sistema de diseño y tokens
│   ├── config/           # Configuraciones
│   └── lib/              # Setup de librerías externas
├── public/               # Assets estáticos
└── FRONTEND_DOCUMENTATION.md  # Documentación detallada
```

Ver [documentación completa](./FRONTEND_DOCUMENTATION.md) para más detalles.

---

## 🎨 Sistema de Diseño

### Temas Disponibles

| Tema | Descripción | Preview |
|------|-------------|---------|
| **Light** | Tema claro con paleta azul cielo | ![#D3E9FF](https://via.placeholder.com/15/D3E9FF/000000?text=+) `#D3E9FF` |
| **Dark** | Tema oscuro con acentos azules | ![#0A1929](https://via.placeholder.com/15/0A1929/000000?text=+) `#0A1929` |
| **Minimal** | Minimalista monocromático | ![#F8F9FA](https://via.placeholder.com/15/F8F9FA/000000?text=+) `#F8F9FA` |

### Design Tokens

```css
/* Spacing */
--spacing-xs: 4px → --spacing-3xl: 64px

/* Border Radius */
--radius-sm: 8px → --radius-xl: 20px

/* Typography */
--font-primary: 'Inter', sans-serif
```

---

## 🔌 API Endpoints

El frontend consume los siguientes endpoints del backend:

### Aircraft
- `GET /api/v1/aircraft` - Lista paginada con filtros
- `GET /api/v1/aircraft/:id` - Detalle de aeronave
- `GET /api/v1/aircraft/search` - Búsqueda
- `GET /api/v1/aircraft/popular` - Aeronaves populares
- `GET /api/v1/aircraft/:id/similar` - Similares

### Comparison
- `GET /api/v1/aircraft/compare?ids=1,2,3` - Comparar aeronaves
- `GET /api/v1/search/suggest` - Sugerencias

### Manufacturers & Families
- `GET /api/v1/manufacturers/summary` - Lista de fabricantes
- `GET /api/v1/families` - Familias de aeronaves

Ver [documentación de servicios](./FRONTEND_DOCUMENTATION.md#-consumo-de-api) para más detalles.

---

## 📜 Scripts Disponibles

```bash
npm run dev      # Servidor de desarrollo (puerto 5173)
npm run build    # Build para producción
npm run preview  # Preview del build
npm run lint     # Linting con ESLint
```

---

## 🏗️ Arquitectura

Este proyecto usa **arquitectura feature-based** donde cada feature es autocontenido:

```
feature/aircraft/
├── components/    # Componentes específicos
├── hooks/         # Custom hooks
├── services/      # API calls
├── types/         # TypeScript types
└── utils/         # Utilidades
```

**Beneficios:**
- ✅ Escalabilidad
- ✅ Mantenibilidad
- ✅ Separación de responsabilidades
- ✅ Testing más fácil

---

## 🧪 Testing (Próximamente)

```bash
npm run test        # Tests unitarios
npm run test:e2e    # Tests E2E
npm run test:coverage  # Cobertura de tests
```

---

## 🚀 Deployment

### Build para Producción

```bash
npm run build
```

Los archivos optimizados estarán en `/dist`

### Variables de Entorno de Producción

```env
VITE_API_BASE_URL=https://api.skyvault.com/api/v1
```

### Deploy en Vercel/Netlify

```bash
# Vercel
vercel --prod

# Netlify
netlify deploy --prod --dir=dist
```

---

## 🗺️ Roadmap

### v1.0 (Actual)
- ✅ Catálogo de aeronaves con filtros
- ✅ Comparador de aeronaves
- ✅ Sistema de temas
- ✅ Responsive design

### v1.1 (Próximo)
- [ ] Autenticación de usuarios
- [ ] Favoritos y listas personalizadas
- [ ] Compartir comparaciones
- [ ] PWA support

### v2.0 (Futuro)
- [ ] Sistema de noticias
- [ ] Comentarios y reviews
- [ ] API pública para developers
- [ ] Internacionalización (i18n)

---

## 🤝 Contribución

Las contribuciones son bienvenidas! Por favor:

1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add: amazing feature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Autor

**Estudiante de Ingeniería de Software** (5/10 semestres)

- 💼 Stack: Frontend, Java, Swift, Kotlin, C++, Cangjie
- 🎨 Diseño: Neomorphism, Liquid Glass
- 🛠️ Herramientas: Figma, Notion

---

## 🙏 Agradecimientos

- [React](https://react.dev/) - Framework UI
- [Vite](https://vitejs.dev/) - Build tool
- [GSAP](https://greensock.com/gsap/) - Animaciones
- [Heroicons](https://heroicons.com/) - Iconografía
- Comunidad de desarrolladores ✨

---

<div align="center">

**⭐ Si te gusta este proyecto, dale una estrella!**

[⬆ Volver arriba](#-skyvault---aircraft-catalog--comparison-platform)

</div>
