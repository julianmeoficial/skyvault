# 🛩️ SkyVault - Aircraft Catalog Frontend

[![React](https://img.shields.io/badge/React-19.1.1-61DAFB?logo=react)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.9.3-3178C6?logo=typescript)](https://www.typescriptlang.org/)
[![Vite](https://img.shields.io/badge/Vite-7.1.7-646CFF?logo=vite)](https://vitejs.dev/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

Modern and elegant web application for exploring commercial aircraft specifications, comparing models, and discovering aviation data.

---

## ✨ Features

- ✈️ **Aircraft Catalog** - Browse through a comprehensive database of commercial aircraft
- 🔍 **Advanced Filters** - Filter by manufacturer, family, passengers, range, and more
- 🆚 **Comparison Tool** - Compare 2-3 aircraft side-by-side with detailed specifications
- 🌓 **Multi-Theme Support** - Light, Dark, and Minimal themes
- 📱 **Responsive Design** - Optimized for desktop, tablet, and mobile
- ⚡ **Fast & Optimized** - Built with Vite for blazing-fast performance
- 🎨 **Modern UI** - Neomorphism and liquid glass design aesthetics
- 🔄 **Real-time Search** - Instant aircraft search with autocomplete
- 📊 **Pagination** - Efficient data loading with server-side pagination
- 🎯 **Type-Safe** - Fully typed with TypeScript

---

## 🚀 Tech Stack

### Core
- **React** 19.1.1 - UI Framework
- **TypeScript** ~5.9.3 - Type Safety
- **Vite** ^7.1.7 - Build Tool & Dev Server
- **React Router DOM** ^7.9.4 - Routing

### UI & Animations
- **GSAP** ^3.13.0 - Advanced Animations
- **Heroicons React** ^2.2.0 - Icon System
- **CSS Modules** - Scoped Styling

### Data Fetching
- **Axios** ^1.13.1 - HTTP Client

### Development Tools
- **ESLint** ^9.36.0 - Code Linting
- **TypeScript ESLint** ^8.45.0 - TS-specific Rules

---

## 📦 Installation

### Prerequisites
- **Node.js** 18+ 
- **npm** 9+ or **yarn** 1.22+

### Install Dependencies
```bash
npm install
```

### Environment Variables
Create a `.env` file in the root directory:

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

---

## 🔧 Development

Start the development server:

```bash
npm run dev
```

The app will be available at `http://localhost:5173`

---

## 🏗️ Build

Build for production:

```bash
npm run build
```

Preview production build:

```bash
npm run preview
```

---

## 📂 Project Structure

```
frontend/
├── src/
│   ├── app/                    # App configuration (layouts, routes)
│   ├── features/              # Feature modules (aircraft, comparison, etc.)
│   │   ├── aircraft/          # Aircraft catalog feature
│   │   ├── comparison/        # Comparison feature
│   │   ├── families/          # Aircraft families
│   │   ├── news/              # News system
│   │   └── auth/              # Authentication (future)
│   ├── pages/                 # Route components
│   ├── shared/                # Shared components, hooks, utils
│   │   ├── components/        # Reusable components
│   │   ├── contexts/          # React contexts
│   │   ├── hooks/             # Custom hooks
│   │   ├── types/             # Shared TypeScript types
│   │   └── utils/             # Utility functions
│   ├── styles/                # Global styles & design tokens
│   │   ├── variables.css      # CSS Variables (themes)
│   │   └── global.css         # Global styles
│   ├── config/                # Configuration files
│   ├── lib/                   # Third-party library configs
│   └── assets/                # Static assets
├── public/                    # Public static files
└── FRONTEND_DOCUMENTATION.md  # Complete technical documentation
```

> **⚠️ Note:** Please ignore the `frontend_backup/` folder if present. It contains an old architecture that is no longer used and was kept only for reference to track changes. It's irrelevant for the current implementation.

---

## 📖 Documentation

For complete technical documentation, architecture details, and API consumption guide, see:

**[📚 FRONTEND_DOCUMENTATION.md](./FRONTEND_DOCUMENTATION.md)**

Includes:
- Complete architecture breakdown
- Feature-based structure explanation
- API endpoints and consumption
- Design system and theming
- Type definitions
- Best practices

---

## 🎨 Design System

### Themes
- **Light Theme** - Default bright theme with blue palette
- **Dark Theme** - Dark mode with adapted colors
- **Minimal Theme** - Monochromatic elegant design

### Design Tokens
```css
/* Spacing */
--spacing-xs: 4px
--spacing-sm: 8px
--spacing-md: 16px
--spacing-lg: 24px
--spacing-xl: 32px

/* Colors (Light Theme) */
--color-primary: #0d4b7a
--color-bg-main: #D3E9FF
--color-text-primary: #1d1d1f

/* Shadows */
--shadow-sm: 0 2px 8px rgba(13, 75, 122, 0.06)
--shadow-card: 0 4px 24px rgba(13, 75, 122, 0.12)
```

---

## 🛣️ Routes

| Route | Description |
|-------|-------------|
| `/` | Home page |
| `/aircraft` | Aircraft catalog |
| `/aircraft/:identifier` | Aircraft detail page |
| `/compare-select` | Comparison selector |
| `/compare` | Comparison view |
| `/families` | Aircraft families |
| `/manufacturers` | Manufacturers list |
| `/news` | Aviation news |
| `/about` | About SkyVault |
| `/api` | API documentation |

---

## 🔌 API Integration

The frontend consumes a REST API with the following base URL:

```
http://localhost:8080/api/v1
```

### Main Endpoints Used

```typescript
// Aircraft
GET /aircraft                    # Paginated catalog
GET /aircraft/:id                # Aircraft detail
GET /aircraft/search            # Search aircraft
GET /aircraft/popular           # Popular aircraft
GET /aircraft/featured          # Featured aircraft
GET /aircraft/:id/similar       # Similar aircraft
GET /aircraft/compare           # Compare aircraft

// Manufacturers & Families
GET /manufacturers/summary      # Manufacturers list
GET /families                   # Families list

// Search
GET /search/suggest             # Search suggestions
```

For complete API documentation, see [FRONTEND_DOCUMENTATION.md](./FRONTEND_DOCUMENTATION.md)

---

## 🧪 Scripts

```bash
# Development
npm run dev          # Start dev server (port 5173)

# Build
npm run build        # Build for production

# Linting
npm run lint         # Run ESLint

# Preview
npm run preview      # Preview production build
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'feat: add some amazing feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Convention

Follow [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, etc.)
- `refactor:` - Code refactoring
- `perf:` - Performance improvements
- `test:` - Adding tests
- `chore:` - Maintenance tasks

---

## 🐛 Known Issues

- [ ] None at the moment

Report issues at: [GitHub Issues](https://github.com/YOUR_USERNAME/skyvault-frontend/issues)

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**SkyVault Development Team**
- OUTTARY (Julián Espitia y Mónica Vellojin) - Software Engineering Students (5/10 semesters)
- Specialization: Frontend, Java, JavaScript, TypeScript, Swift, Kotlin, C++, Cangjie
- Design: Neomorphism, Liquid Glass aesthetics
- Tools: Figma, Notion, Linear, Rave, Spline, Blender, Framer, After Effects

---

## 🌟 Acknowledgments

- React team for the amazing framework
- Vite team for the incredible build tool
- GSAP for powerful animations
- All contributors and supporters

---

## 📞 Support

For questions, issues, or suggestions:

- 📧 Email: julianmeoficial@outlook.com

---

<div align="center">

**⭐ If you like this project, please give it a star! ⭐**

Made with ❤️ and ✈️

</div>
