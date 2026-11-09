# Estado de la Fase 0 (2026-11-09)

Complementa `03-propuesta-v1-app-duradera.md`. **Donde difiere (nombre, applicationId, Fase 0), este documento tiene prioridad.**

## Confirmado
- Nombre: **Metanoia Atril**; etiqueta del launcher: **Atril**.
- Repositorio: https://github.com/GusCoria/metanoia-atril
- El usuario tiene Android Studio y Git y aprobó la propuesta de configuración de Fase 0.

## Propuesta (aplicada en el esqueleto, sin compilar)
- applicationId `io.github.guscoria.metanoiaatril` (sustituye a `...lectorepub`). Provisional hasta la primera publicación.
- minSdk 29, targetSdk 36, compileSdk 37 (Compose 1.12 / BOM 2026.08.00 lo exige).
- AGP 9.2.1, Gradle 9.4.1, JDK 17+, Kotlin integrado (2.3.10), KSP 2.3.9, Hilt 2.59.2.
- Dependencias solo: Compose + Material 3, activity-compose, core-ktx, Hilt. Room, DataStore, WorkManager, Readium y Media3 llegan en su fase.
- Sin permisos (sin INTERNET); `allowBackup=false`.

## Verificación
- No compilado ni ejecutado en el entorno donde se escribió (sin Android SDK ni acceso a Google/Maven). La primera compilación la hace el usuario.
- Versiones **sin verificar**: core-ktx 1.17.0, activity-compose 1.12.0, y que AGP 9.2.1 traiga Kotlin 2.3.10.

## Pendientes
- Primera compilación y ejecución; licencia del repositorio; modelo/Android de la prueba de WordReference; verificar Readium y Media3 antes de la Fase 1.
