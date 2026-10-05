# Análisis de Progreso y SRS - Futbolboxd

Este informe resume el estado actual del desarrollo de la aplicación frente a los requisitos especificados en la **SRS (Especificación de Requisitos de Software)** de Futbolboxd.

---

## 1. Lo que ya tenemos implementado ✅

| Módulo / Característica | Estado | Detalle |
|---|---|---|
| **Configuración KMP & Dependencias** | Completo | Gradle, libs.versions.toml, Koin, GitLive Firebase, Navigation Compose, Coil 3, Ktor, Coroutines, Datetime. |
| **Arquitectura Limpia & Modelos** | Completo | Estructura de paquetes (`domain`, `data`, `ui`, `di`), modelos (`Match`, `Review`, `UserProfile`, `AppResult`). |
| **Tema & UI System** | Completo | `FutbolboxdTheme` con paleta de colores (verdes y negros) y tipografía **Nunito** integrada con Compose Resources. |
| **Autenticación (RF-01 a RF-05, RF-22)** | Completo | `AuthRepositoryImpl` (Firebase Auth), `AuthViewModel`, `RegisterScreen`, `LoginScreen` y navegación inicial. |

---

## 2. Lo que falta por implementar según la SRS 🚀

### A. Capa de Datos Firestore (`data/repository`)
- [ ] **`MatchRepositoryImpl`**: Consultas a Firestore para obtener partidos por etiqueta (`getMatchesByTag`) y detalle de partido (`getMatch`).
- [ ] **`ReviewRepositoryImpl`**: Operaciones CRUD de reseñas en Firestore (`getReviewsForMatch`, `getReviewsByUser`, `saveReview`, `deleteReview`).
- [ ] **`UserRepositoryImpl`**: Gestión de perfil de usuario (`getProfile`, `updateProfile`) y favoritos en subcolecciones (`observeFavorites`, `toggleFavorite`).

### B. Pantallas y Flujos de UI (`ui/`)
- [ ] **Home (`HomeScreen`)**:
  - Carruseles horizontales (`LazyRow`) agrupados por etiquetas (*Partidos de la semana*, *Premier League*, *Fútbol argentino*, etc.).
  - Tarjetas de partido con imágenes cargadas asíncronamente con **Coil 3**.
- [ ] **Detalle de Partido (`DetailScreen`)**:
  - Información completa del partido (imagen, equipos, competición, fecha).
  - Botón de favoritos con estado interactivo.
  - Formulario de reseña (puntaje de 1 a 5 y texto, máx. 500 caracteres).
  - Listado de reseñas de usuarios y cálculo de puntaje promedio.
- [ ] **Perfil y Edición (`ProfileScreen`, `EditProfileScreen`)**:
  - Datos del usuario, contadores y pestañas (*Mis reseñas* y *Guardados*).
  - Pantalla de edición de nombre de usuario y biografía.
  - Botón de cierre de sesión funcional.

### C. Pruebas y Calidad (RNF-07)
- [ ] Tests unitarios para ViewModels y repositorios usando `kotlin-test`, `kotlinx-coroutines-test` y `turbine`.

### D. Documentación y Entrega (RNF-08, RNF-09)
- [ ] README completo con instrucciones, arquitectura y uso de IA.
- [ ] Generación de APK y validación final en Android.

---

## 3. Próximos pasos recomendados

1. **Implementar Repositorios de Firestore** (`MatchRepositoryImpl`, `ReviewRepositoryImpl`, `UserRepositoryImpl`).
2. **Construir la pantalla Home** con los rows de partidos y Coil 3.
3. **Construir Detalle de Partido y Reseñas**.
4. **Construir Perfil y Edición de Perfil**.
