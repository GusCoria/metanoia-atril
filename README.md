# Metanoia Atril

Aplicación Android nativa para leer y administrar libros EPUB. Local, privada y sin cuentas ni nube.
Nombre en el launcher: **Atril**.

> **Estado: Fase 0 (cimientos).** Solo hay una pantalla provisional. La funcionalidad se definirá con la
> Especificación Funcional Maestra. **Este esqueleto aún no ha sido compilado** (ver "Verificación").

## Requisitos
- Android Studio estable reciente (que soporte AGP 9.2) y Git.
- JDK 17 o superior (el JDK integrado de Android Studio sirve).
- Android SDK Platform **37** y Build Tools **36.0.0** (Android Studio los ofrece al sincronizar).
- Dispositivo o emulador con Android 10 (API 29) o superior.

## Abrir y ejecutar
1. Clona el repositorio y abre la carpeta en Android Studio (*Open*, no *Import*).
2. Espera la sincronización de Gradle; acepta instalar los componentes del SDK que pida.
3. Elige un dispositivo o emulador y pulsa *Run*.

Desde terminal: `./gradlew assembleDebug` (en Windows, `gradlew.bat assembleDebug`).

## Estructura
```
app/src/main/java/io/github/guscoria/metanoiaatril/
├── MetanoiaAtrilApp.kt   Application (punto de entrada de Hilt)
├── MainActivity.kt       Solo aloja la UI
└── ui/                   Tema M3 y pantallas
```
Los paquetes `data/`, `domain/`, `service/`, `worker/` y `di/` se crearán cuando tengan código real,
para no dejar paquetes vacíos. Responsabilidades previstas: `data` (persistencia), `domain` (modelos y
casos de uso), `service` (EPUB, TTS, diccionarios), `worker` (segundo plano), `di` (módulos Hilt).
Podrán reorganizarse al recibir la especificación maestra.

## Decisiones técnicas (propuestas, pendientes de verificación en compilación)
| Tema | Decisión | Motivo |
|---|---|---|
| applicationId | `io.github.guscoria.metanoiaatril` (**provisional**) | Basado en el usuario de GitHub. No puede cambiar tras la primera publicación pública. |
| minSdk / targetSdk | 29 / 36 | Android 10 como mínimo. Target 36 es el exigido por Google Play desde 2026-08-31 (por si se usa algún día). |
| compileSdk | 37 | Compose 1.12 (BOM 2026.08.00) lo exige. |
| AGP / Gradle | 9.2.1 / 9.4.1 | Mínimos documentados: Gradle 9.4.1, JDK 17, Build Tools 36.0.0. 9.2.1 corrige un fallo de R8 de la 9.2.0. |
| Kotlin | Integrado en AGP 9 (2.3.10) | No se aplica `kotlin-android`. La versión del plugin Compose en el catálogo debe coincidir. |
| KSP / Hilt | 2.3.9 / 2.59.2 | AGP 9 exige KSP ≥ 2.3.6 y Hilt ≥ 2.59.2; el plugin de Hilt requiere AGP 9. |
| Dependencias | Solo Compose + Material 3, activity-compose, core-ktx y Hilt | Room, DataStore, WorkManager, Readium y Media3 se añaden en su fase, con su versión verificada. |
| Permisos | Ninguno (sin INTERNET) | Privacidad y funcionamiento local. |
| `allowBackup` | `false` | Las copias de seguridad serán propias y controladas por la app. |
| Icono | Provisional | A reemplazar por el definitivo. |

## Verificación
- **No realizado:** compilación, ejecución en emulador/dispositivo y pruebas. El entorno donde se escribió
  no tenía Android SDK ni acceso a los repositorios de Google/Maven.
- Versiones tomadas de documentación oficial: AGP, Gradle, JDK, Build Tools, KSP/Hilt mínimos, BOM de Compose.
- **Sin verificar:** `core-ktx 1.17.0` y `activity-compose 1.12.0` (puestas de memoria), y que AGP 9.2.1
  siga trayendo Kotlin 2.3.10. Si Gradle falla por alguna de ellas, ajustar en `gradle/libs.versions.toml`.

## Pendientes
- Primera compilación y ejecución (a cargo del desarrollador).
- Licencia del repositorio (por decidir).
- Pruebas automatizadas (no hay lógica que probar aún).
- Especificación Funcional Maestra → revisar estructura antes de implementar.

## Documentación
Contexto y requisitos en [`docs/`](docs/README.md); reglas de trabajo para Claude Code en [`CLAUDE.md`](CLAUDE.md).
