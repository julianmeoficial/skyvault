# TimelineSection - Modern Aircraft Gallery

## 📁 Archivos Activos (EN USO)

### ✅ ModernAircraftGallery.tsx
- **Componente principal de la galería**
- Grid responsivo con cards grandes y legibles
- Modal de detalles al hacer click
- Filtros por fabricante (Airbus/Boeing/Todos)
- Diseño Neomórfico + Glassmorphism
- Animaciones sutiles y funcionales

### ✅ ModernAircraftGallery.module.css
- **Estilos del componente**
- Diseño responsive (mobile-first)
- Efectos glassmorphism y neomorphism
- Soporte para tema claro/oscuro
- Animaciones con stagger

### ✅ index.ts
- **Punto de entrada del módulo**
- Exporta ModernAircraftGallery por defecto

---

## 🗑️ Archivos Antiguos (RENOMBRADOS CON _OLD_)

Estos archivos fueron parte del desarrollo anterior pero **YA NO SE USAN**:

### ❌ _OLD_HorizontalGallerySection.tsx
- Versión antigua con scroll horizontal parallax
- **Problema**: UX terrible, imágenes ilegibles, texto pequeño
- Reemplazado por ModernAircraftGallery

### ❌ _OLD_HorizontalGallerySection.module.css
- Estilos de la versión antigua
- Ya no se utiliza

### ❌ _OLD_TimelineSection.tsx
- Componente de timeline vertical con GSAP
- Nunca se llegó a implementar en la página
- Ya no se utiliza

### ❌ _OLD_TimelineSection.module.css
- Estilos del timeline vertical
- Ya no se utiliza

---

## 🔥 Cómo eliminar archivos antiguos

Si estás 100% seguro que no los necesitas, puedes eliminarlos:

```bash
# Desde la carpeta TimelineSection
rm _OLD_*.tsx
rm _OLD_*.css
```

O manualmente desde tu IDE/explorador de archivos.

---

## 📊 Comparación de Componentes

| Feature | OLD Horizontal Gallery | ✅ Modern Gallery |
|---------|----------------------|------------------|
| Layout | Scroll horizontal | Grid responsivo |
| Legibilidad | Baja (blur, scale) | Alta (tamaño completo) |
| Info visible | Solo en hover | Siempre visible |
| Modal | No | Sí |
| UX | Frustrante | Excelente |
| Performance | GSAP pesado | CSS + animaciones ligeras |

---

## 🎯 Uso en el proyecto

El componente se importa en:
- `src/pages/Families/FamiliesPage.tsx`

```tsx
import ModernAircraftGallery from './sections/TimelineSection/ModernAircraftGallery';

// Dentro del componente:
<ModernAircraftGallery />
```

---

**Última actualización**: 2025
**Mantenedor**: Sistema de desarrollo SkyVault
