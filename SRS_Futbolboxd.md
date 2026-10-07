# Especificación de Requisitos de Software (SRS)
## Futbolboxd: reseñas de partidos de fútbol

**Versión:** 2.0 · **Fecha:** 07/10/2026 · **Entrega:** 08/10/2026 23:59 · **Contexto:** Challenge técnico AranguriApps

> Esta versión actualiza la SRS 1.0 con el alcance realmente implementado: se agregan la búsqueda, el sistema de seguidores, los perfiles públicos y la precarga de datos, y se ajusta el modelo de partido.

---

## 1. Introducción

### 1.1 Propósito
Definir los requisitos de **Futbolboxd**, una app móvil social estilo Letterboxd pero de fútbol. Los usuarios descubren partidos, los puntúan y reseñan, los guardan en favoritos y siguen a otros aficionados.

### 1.2 Alcance
**Incluye:** registro, login, home con filas de partidos, búsqueda, detalle de partido (favoritos y reseñas), perfil propio y público, seguidores/seguidos y edición de perfil.

**No incluye:** resultados en vivo, comentarios sobre reseñas, notificaciones, panel de administración ni inicio de sesión social.

### 1.3 Restricciones del challenge
- Kotlin Multiplatform + Compose Multiplatform (Android e iOS).
- Comunicación de red con Firebase.
- Pantallas conectadas por navegación, con ver/crear/editar datos.
- Arquitectura con separación clara entre lógica y vista.
- Repo con commits frecuentes, README, APK y uso de IA documentado.

### 1.4 Definiciones
| Término | Significado |
|---|---|
| Partido | Documento en Firestore con equipos, escudos, resultado, estadio y etiquetas |
| Reseña | Puntaje (1 a 5) y texto de un usuario sobre un partido |
| Favorito / Guardado | Partido guardado por un usuario |
| Row | Fila horizontal deslizable de partidos en el Home |
| Seguidor / Seguido | Relación entre usuarios, en una sola dirección |

---

## 2. Stack tecnológico y arquitectura

| Aspecto | Detalle |
|---|---|
| UI | Compose Multiplatform `1.12.1` |
| Lenguaje | Kotlin `2.4.20` |
| Inyección de dependencias | Koin (`koin-core`, `koin-compose`, `koin-compose-viewmodel`) |
| Backend | GitLive Firebase: `firebase-auth`, `firebase-firestore` |
| Imágenes | Coil 3 (`coil-compose`, `coil-network-ktor3`) |
| Navegación | JetBrains Navigation Compose |
| Tema | Oscuro, verde césped (*Pitch Green*) y negro profundo; tipografía Nunito |
| Plataformas | Android (minSdk 24) e iOS, con UI compartida |

**Arquitectura:** Clean Architecture con MVVM en la capa de UI, todo en `commonMain`.

- **Domain:** modelos (`Match`, `Review`, `UserProfile`), `AppResult` e interfaces de repositorio. No depende de Firebase ni de Compose.
- **Data:** implementaciones de los repositorios sobre Firebase Auth y Firestore, incluyendo subcolecciones de favoritos, seguidores y seguidos.
- **UI:** pantallas Compose, un ViewModel por pantalla (inyectado con Koin) y navegación.

---

## 3. Requisitos funcionales

### 3.1 Autenticación
| ID | Requisito | Prioridad |
|---|---|---|
| RF-01 | Registro con email, contraseña y nombre de usuario. El nombre se guarda en Firestore y se refleja en el perfil. | Alta |
| RF-02 | Inicio de sesión con email y contraseña. | Alta |
| RF-03 | La contraseña tiene un mínimo de 6 caracteres y un botón para mostrar u ocultar su contenido (en registro y login). | Alta |
| RF-04 | La sesión persiste al cerrar y reabrir la app. | Alta |
| RF-05 | El usuario puede cerrar sesión desde el perfil; se limpia la sesión y se redirige a Login. | Alta |
| RF-06 | Los campos se validan y se muestran errores claros. | Alta |

### 3.2 Home
| ID | Requisito | Prioridad |
|---|---|---|
| RF-07 | Muestra filas con deslizamiento horizontal, organizadas por etiquetas en Firestore (por ejemplo *Partidos de la Semana*, *Premier League*, *Fútbol Argentino*). | Alta |
| RF-08 | Cada `MatchCard` tiene formato póster vertical con los **escudos de ambos equipos** lado a lado sobre fondo oscuro, el resultado, los nombres de los equipos y la competición. | Alta |
| RF-09 | Un partido puede aparecer en más de una fila (asignación por etiquetas). | Media |
| RF-10 | Al tocar una tarjeta se abre el detalle del partido. | Alta |
| RF-11 | Pull-to-refresh para recargar los partidos. | Media |
| RF-12 | Estados de carga, vacío y error con opción de reintentar. | Alta |

### 3.3 Búsqueda
| ID | Requisito | Prioridad |
|---|---|---|
| RF-13 | Barra de búsqueda que filtra al instante por nombre de equipo, competición o estadio. | Alta |
| RF-14 | Los resultados se muestran en una cuadrícula adaptable de tarjetas de partido. | Alta |
| RF-15 | Pull-to-refresh para recargar los datos. | Media |
| RF-16 | Estado vacío cuando no hay coincidencias. | Media |

### 3.4 Detalle de partido
| ID | Requisito | Prioridad |
|---|---|---|
| RF-17 | **Hero banner** con degradado oscuro: ambos escudos centrados, resultado en verde principal, estadio y fecha (`dd/MM/yy`). | Alta |
| RF-18 | Muestra la calificación promedio y la cantidad total de reseñas. | Media |
| RF-19 | Botón de marcador (*Bookmark*) para guardar o quitar el partido de favoritos. | Alta |
| RF-20 | Formulario de reseña con 1 a 5 estrellas y texto (máx. 500 caracteres). | Alta |
| RF-21 | Un usuario tiene una sola reseña por partido; puede editarla o eliminarla. | Alta |
| RF-22 | Lista de reseñas de la comunidad (autor, puntaje, texto, fecha). | Alta |
| RF-23 | Desde cada reseña se puede **seguir o dejar de seguir** al autor. | Media |
| RF-24 | El nombre del autor es un enlace a su perfil público. | Media |

### 3.5 Perfil propio
| ID | Requisito | Prioridad |
|---|---|---|
| RF-25 | Muestra avatar con iniciales, nombre de usuario y biografía. | Alta |
| RF-26 | Contadores de **Seguidores** y **Seguidos**; al tocarlos se abre un diálogo (`FollowListDialog`) con la lista de usuarios y un mensaje si está vacía. | Media |
| RF-27 | Desde el diálogo se puede navegar al perfil de cada usuario. | Media |
| RF-28 | Dos pestañas: *Mis Reseñas* y *Guardados*. | Alta |
| RF-29 | Desde las reseñas y los guardados se puede ir al detalle del partido. | Alta |
| RF-30 | *Editar perfil*: el usuario puede actualizar nombre y biografía. | Alta |
| RF-31 | Botón *Cerrar sesión*. | Alta |

### 3.6 Perfil público de otro usuario
| ID | Requisito | Prioridad |
|---|---|---|
| RF-32 | Vista del perfil de cualquier usuario, con sus reseñas y partidos guardados. | Media |
| RF-33 | Botón **Seguir / Siguiendo**. | Media |
| RF-34 | No permite editar el perfil, cerrar sesión ni abrir los diálogos de estadísticas. | Media |

### 3.7 Precarga de datos (`DatabaseSeeder`)
| ID | Requisito | Prioridad |
|---|---|---|
| RF-35 | Al iniciar la app se precargan **40 partidos únicos** (20 de la Premier League y 20 de la Liga Profesional Argentina) con escudos en PNG, estadio, fecha y resultado. | Alta |
| RF-36 | La precarga no debe duplicar partidos si ya existen. | Alta |

### 3.8 Navegación
| ID | Requisito |
|---|---|
| RF-37 | Sin sesión: Login ⇄ Registro. Con sesión: barra inferior con **Home**, **Búsqueda** y **Perfil**. |
| RF-38 | Detalle de partido, Editar perfil y Perfil público son pantallas apiladas con botón de volver. |

---

## 4. Requisitos no funcionales

| ID | Categoría | Requisito |
|---|---|---|
| RNF-01 | Arquitectura | Clean Architecture + MVVM; repositorios detrás de interfaces; estado de UI como `StateFlow`. |
| RNF-02 | Robustez | La app no crashea ante fallas de red; los errores de red y de Firebase se capturan (`AppResult`) y se muestran al usuario. |
| RNF-03 | UI | Material 3, tema oscuro propio, transiciones limpias y claves estables en listas para evitar recomposiciones innecesarias. |
| RNF-04 | Rendimiento | Imágenes asíncronas con caché (Coil 3). Listas con `LazyRow` y `LazyColumn`/`LazyVerticalGrid`. |
| RNF-05 | Plataformas | Android 7+ (API 24) e iOS. UI compartida entre ambas. |
| RNF-06 | Seguridad | Reglas de Firestore (ver sección 6). |
| RNF-07 | Calidad | Tests unitarios de ViewModels y casos de uso con repositorios falsos. |
| RNF-08 | Proceso | Commits pequeños con mensajes claros (Conventional Commits) y CI en GitHub Actions. |
| RNF-09 | Documentación | README (proyecto, arquitectura y motivos, herramientas de IA, cómo correrlo) y diagramas. |

---

## 5. Modelo de datos (Firestore)

```
matches/{matchId}
  homeTeam: string
  awayTeam: string
  homeTeamUrl: string        // escudo local (PNG)
  awayTeamUrl: string        // escudo visitante (PNG)
  homeScore: number
  awayScore: number
  stadium: string
  competition: string        // "Premier League", "Liga Profesional"...
  date: timestamp
  tags: string[]             // ["week", "premier", "argentina"]

reviews/{userId_matchId}     // una reseña por usuario y partido
  matchId, userId: string
  username: string           // desnormalizado
  rating: number (1-5)
  text: string
  createdAt, updatedAt: timestamp

users/{userId}
  username: string
  bio: string
  createdAt: timestamp
  favorites/{matchId}        // matchId, savedAt
  followers/{followerId}     // followerId, createdAt
  following/{followedId}     // followedId, createdAt
```

**Rows del Home:** cada fila es una consulta a `matches` con `tags array-contains <tag>`. La lista de filas (título y etiqueta) se define en el cliente.

**Búsqueda:** el filtro por equipo, competición o estadio se aplica en el cliente sobre los partidos ya cargados (40 documentos).

**Seguir a un usuario:** se escribe en dos lugares, `following/{B}` en el perfil de A y `followers/{A}` en el perfil de B.

**Índices:** `reviews` por `matchId` ordenado por `createdAt`, y por `userId` ordenado por `createdAt`.

---

## 6. Reglas de seguridad de Firestore (a definir)

| Colección | Lectura | Escritura |
|---|---|---|
| `matches` | Usuarios autenticados | Ver nota sobre el seeder |
| `reviews` | Usuarios autenticados | Solo el autor (`userId == auth.uid`) |
| `users/{id}` | Usuarios autenticados | Solo el dueño |
| `users/{id}/favorites` | Dueño | Dueño |
| `users/{id}/following` | Usuarios autenticados | Dueño |
| `users/{id}/followers` | Usuarios autenticados | Solo el documento propio (`followerId == auth.uid`) |

> **Nota sobre el seeder:** como la precarga escribe en `matches` desde el cliente, las reglas deben permitir escritura en esa colección, aunque sea de forma temporal. Esto contradice el principio de "partidos de solo lectura". Alternativas: dejar la escritura abierta solo durante el desarrollo y cerrarla antes de la entrega, o cargar los partidos una sola vez y quitar el seeder del código final. La decisión debe documentarse en el README.

---

## 7. Pantallas

| # | Pantalla | Contenido clave |
|---|---|---|
| 1 | Login | Email, contraseña (mostrar/ocultar), enlace a registro |
| 2 | Registro | Usuario, email, contraseña |
| 3 | **Home** | Filas horizontales de `MatchCard`, pull-to-refresh |
| 4 | **Búsqueda** | Barra de filtro y cuadrícula de partidos |
| 5 | Detalle de partido | Hero banner, favorito, promedio, formulario de reseña, reseñas de la comunidad |
| 6 | **Perfil** | Datos, seguidores/seguidos, pestañas Reseñas y Guardados, Editar y Cerrar sesión |
| 7 | Editar perfil | Usuario y biografía |
| 8 | Perfil público | Datos, botón Seguir, reseñas y guardados |

---

## 8. Estado y entregables pendientes

**Implementado según el avance actual:** autenticación, home, búsqueda, detalle con reseñas y favoritos, perfiles propio y público, seguidores y seguidos, y seeder.

**Pendiente de confirmar o completar antes del cierre (08/10, 23:59):**
- Tests unitarios de ViewModels y casos de uso.
- CI en GitHub Actions (build + tests).
- Reglas de Firestore definitivas (sección 6).
- README con arquitectura, motivos, herramientas de IA y guía para correr el proyecto.
- Diagramas de arquitectura y de modelo de datos.
- Prueba en iOS y APK instalable de Android.
- Mail de entrega a `info@aranguriapps.com`, con asunto `[NOMBRE APELLIDO - Challenge tecnico AranguriApps]`, los links al repo y el APK.

---

## 9. Riesgos

| Riesgo | Mitigación |
|---|---|
| Tiempo restante muy corto | Congelar funcionalidades nuevas y priorizar README, tests, CI y APK |
| Firebase en iOS (necesita Mac) | Documentar el estado de iOS en el README si no se llega a probar |
| El seeder obliga a dejar `matches` escribible | Cerrar la escritura antes de entregar o quitar el seeder |
| Reglas de Firestore mal definidas (seguidores, reseñas) | Probarlas con dos usuarios distintos antes de la entrega |
| Código generado por IA que rompe la app | Revisar cada diff, compilar y probar en cada commit |
| Derechos de autor de los escudos | Indicar la fuente de las imágenes en el README |

---

## 10. Criterios de aceptación

- Un usuario nuevo puede registrarse, ver el Home, buscar un partido, abrirlo, guardarlo y reseñarlo, y luego verlo en su Perfil.
- Un usuario puede seguir a otro desde una reseña y ver los contadores y listas actualizados.
- Editar el perfil y las reseñas funciona sin cerrar la app.
- Ningún flujo principal crashea, incluso sin conexión.
- El repo incluye README, historial de commits coherente, CI en verde y APK instalable.
