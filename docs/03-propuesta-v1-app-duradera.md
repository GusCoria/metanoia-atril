# Propuesta v1: revaloración de requisitos y plan para una app duradera

Fecha: 2026-11-05 · Complementa `02-informe-revision-especificacion.md`. Donde difieren (sobre todo traducción y actualizaciones), este documento tiene prioridad. **Nombre, applicationId y Fase 0: prevalece `04-estado-fase-0.md`** (el applicationId de abajo, `...lectorepub`, fue sustituido por `io.github.guscoria.metanoiaatril`). Todo lo marcado **Propuesta** requiere aprobación del usuario; lo marcado **Confirmado** ya fue aprobado.

## 1. Dirección indicada por el usuario (registro)
- Prioridad: **TTS**, **display del lector** y **área de notas**.
- Diccionario offline para palabras: hasta **2 o 3 palabras**.
- Traducción de frases: **no se integra WordReference ni modelos locales**; se usa una **app externa ya instalada** (la de WordReference o Google Translate) que hace su propia petición.
- Distribución por **APK** (propio o espejo, lo que sea mejor).
- Preferencia por herramientas **gratuitas**, sin costo para los usuarios.

### 1.1 Decisiones confirmadas por el usuario
- **Copias de seguridad: solo datos** (no incluyen los EPUB) y **diseño simplificado de la sección 5**, que sustituye a las cadenas incrementales (RF-BCK-002, 005 y 010 quedan modificados).
- **Marcas de eliminado en las copias: se conservan 30 días** (igual que la papelera de notas).
- **applicationId:** basado en el usuario de GitHub (github.com/GusCoria; minúsculas, sin guiones). Debe mantenerse sin cambios después del primer lanzamiento. Valor vigente: ver `04-estado-fase-0.md`.
- **WordReference:** la prueba en el teléfono del usuario **funcionó** (aparece en el menú de selección de texto). Se usará `ACTION_PROCESS_TEXT`. Falta anotar modelo y versión de Android donde se probó.
- **Libro terminado (cierra el pendiente 1 de la especificación):**
  - **Automático:** al llegar al final del libro y pasar a la página siguiente se marca como terminado. En desplazamiento vertical continuo cuenta el intento de avanzar estando ya en el final.
  - **Aviso con Deshacer** tras el marcado automático.
  - **Manual:** opción en el menú, con confirmación, para marcarlo como terminado.
  - **TTS:** al llegar al final del libro, la lectura **se detiene y no marca** el libro.
  - **Libro ya terminado:** el menú **no ofrece quitar la marca por sí sola**, sino **"Empezar a leer de nuevo"**: quita la marca de terminado y **reinicia el progreso y la posición guardados**. Se conservan notas, marcadores y resaltados (RF-LECT-003).

## 2. Distribución y actualizaciones

| Opción | Costo | Actualizaciones | Valoración |
|---|---|---|---|
| **A. APK propio en GitHub Releases + Obtainium** | Gratis | Obtainium revisa versiones en segundo plano; con Android 12+ puede actualizar automáticamente | **Recomendada como canal principal** |
| B. APKMirror | Gratis | Manual para el usuario | Comunidad muy curada, sin garantía de publicar tu app. No sirve como canal principal |
| C. F-Droid / IzzyOnDroid | Gratis | Automáticas desde la tienda | Buena opción posterior; exige código abierto y dependencias libres |
| D. Google Play | Cuota de registro (no gratis) | Automáticas | Fuera de lo "gratuito"; obliga a cumplir el target API vigente |

**Consecuencias (Propuesta):**
- RF-UPD-001 (revisión mensual propia, últimas cuatro versiones) **se retira de la app**: la actualización la gestiona Obtainium o la tienda. RF-UPD-002 (respaldo antes de migrar) se mantiene.
- Sin servidor propio y sin comprobar versiones desde la app → la app **no necesita permiso INTERNET**.
- **Clave de firma:** guardar copias de la keystore en lugar seguro; si se pierde, los usuarios no podrán actualizar sobre su instalación.
- Publicar el hash SHA-256 de cada APK en la página de la versión.

## 3. Revaloración de requisitos por módulo

| Módulo | Propuesta | Cambios y razones |
|---|---|---|
| **Importación (RF-IMP)** | Mantener, por etapas | v1: SAF, Compartir, Abrir con, importación múltiple, duplicados por hash del contenido, metadatos y portada. La **reparación automática** (RF-IMP-004) queda pendiente de definir qué errores son "recuperables"; en v1: validar que Readium abre el archivo, avisar y conservar el original. Sustitución con recuperación de anotaciones: etapa posterior |
| **Biblioteca (RF-BIB)** | Mantener | Riesgo bajo. Recuperación de colecciones al reimportar: sigue pendiente |
| **Lector (RF-LECT)** | Mantener, con ajustes | Cuatro márgenes por padding del contenedor Compose. Quitar "Justificación" del panel. Regla de "terminado" **confirmada** (sección 1.1). Añadir: enlaces internos y notas al pie con botón de volver |
| **Selección contextual (RF-SEL)** | Mantener, cambia "Traducir" | "Traducir" pasa a **Consultar en otra app** (sección 4) |
| **TTS (RF-TTS)** | Mantener completo | Núcleo del proyecto. Readium + sesión Media3 (ver informe). Se detiene al final del libro |
| **Mis notas (RF-NOT)** | Mantener | Importación Markdown (RF-NOT-022) en una versión posterior (alta complejidad); la exportación sí en v1 |
| **Marcadores y resaltados (RF-MAR)** | Mantener | El resaltado básico se adelanta porque las notas pueden crearse desde un resaltado (RF-NOT-003/005). Superposición permitida, sin recorte |
| **Traducción (RF-TRA)** | **Reemplazar** | Se retira casi todo RF-TRA-001..009; ver sección 4 |
| **Diccionarios (RF-DIC)** | Mantener, ajustes | StarDict primero; DSL después (parser propio). Instalación por SAF, copia a almacenamiento privado e índice SQLite. Sin diccionarios incluidos por defecto en v1. "Descargar/Actualizar" se reduce a **instalar desde archivo** |
| **Copias de seguridad (RF-BCK)** | **Simplificado (confirmado)** | Datos únicamente; diseño de la sección 5 |
| **Sistema (RF-SYS)** | Reducir | SYS-001/002 solo al copiar diccionarios (espacio y limpieza de parciales). SYS-003 (guardado de progreso) se mantiene |
| **Actualizaciones (RF-UPD)** | Retirar UPD-001 | Delegado a Obtainium o tienda |
| **Técnica (RF-TEC)** | Ajustar | Se retiran Retrofit/OkHttp y cualquier ML de traducción |

## 4. Traducción simplificada
- **Palabra o hasta 3 palabras:** consulta en el diccionario offline instalado. Si no hay entrada exacta: "No encontrado" y la opción de consultar en otra app. *(Propuesta; el límite de 2 o 3 palabras está confirmado.)*
- **Más de 3 palabras:** se ofrece **Consultar en otra app** directamente. *(Propuesta.)*
- **Consultar en otra app (mecanismo confirmado):** la app envía el texto con `ACTION_PROCESS_TEXT`, solo lectura, a través del selector del sistema. WordReference lo acepta en el teléfono del usuario; Google Translate también se registra para esa acción. Requiere declarar `<queries>` para esa acción.
- **Plan B** si en otro teléfono no aparece: abrir la URL de WordReference con `ACTION_VIEW`.
- **Privacidad:** el texto sale únicamente cuando el usuario elige una app en el selector del sistema.
- Efecto: desaparecen modos de traducción, panel propio, historial de proveedores, descargas de modelos y la dependencia de servicios externos.

## 5. Copias de seguridad: diseño (Confirmado)
Con copias de **solo datos**, pesan poco: las cadenas incrementales dejan de justificarse. El diseño aprobado es:
1. **Un archivo por copia**, comprimido, con: instantánea de la base de datos, preferencias en JSON, manifiesto (versión de la app y del esquema, fecha, conteos, suma SHA-256) y una **exportación legible de las notas en Markdown**.
2. **Escritura atómica:** archivo temporal, verificación y solo entonces renombrar. Nunca queda una copia parcial válida; cancelar siempre es seguro.
3. **Identificadores estables** (UUID + fecha de modificación) en notas, marcadores, resaltados, etiquetas y colecciones, para fusionar sin duplicar (RF-BCK-008).
4. **Libros por huella del contenido** más metadatos: al restaurar sin los EPUB, aparecen como "no disponibles" y se reconectan al reimportarlos.
5. **Eliminaciones:** se conserva la marca de eliminado **30 días** para que restaurar no resucite lo borrado.
6. **Restauración:** validar → vista previa con conteos → copia protectora → restaurar en una base temporal, migrarla al esquema actual y fusionar → resolver conflictos individualmente (notas, marcadores, resaltados). Una app antigua avisa si la copia es de un esquema más nuevo.
7. **Automáticas:** tarea periódica que comprueba si la última copia es anterior al mes actual; últimas 4; almacenamiento privado más **copia opcional en carpeta SAF** (lo privado se borra al desinstalar).
8. **Manuales:** sin límite, en la carpeta externa elegida; compartir con el menú Compartir si el usuario lo desea.
9. **Secciones del módulo:** Automáticas, Manuales, Restaurar, Informe de actividad (se elimina "Cadenas importadas"; "importar copia" pasa a ser una acción de Restaurar).
10. Sin contraseña (RF-BCK-001).

## 6. Pila tecnológica gratuita (Propuesta, sin confirmar)
| Área | Elección | Nota de durabilidad |
|---|---|---|
| Lenguaje / UI | Kotlin, Jetpack Compose, Material 3 | Respaldo de Google |
| Lector EPUB | Readium Kotlin Toolkit (BSD) | Mantenido; minSdk 23 |
| Datos | Room con `exportSchema` y migraciones probadas | Evita perder datos al actualizar |
| Preferencias | DataStore | |
| Segundo plano | WorkManager | Copias y limpieza de papelera |
| TTS | `TextToSpeech` de Android vía Readium, sesión Media3 | Sin costo |
| Editor de notas | compose-rich-editor, **detrás de una interfaz propia** | Aún en versión rc; sustituible sin tocar el resto |
| Diccionarios | StarDict (biblioteca existente) + parser DSL propio | |
| DI | Hilt | |
| Red | **Ninguna** (sin permiso INTERNET) | Se retiran Retrofit/OkHttp |

Fuente de diccionarios gratuitos sugerida (no incluida): FreeDict (140+ diccionarios, ~45 idiomas, StarDict); la mayoría bajo GPL, por lo que **no conviene empaquetarlos** en la app.

## 7. Prácticas para que la app dure
1. **Datos:** Room con esquemas versionados y pruebas de migración desde la primera versión; copia automática antes de migrar.
2. **Formatos abiertos:** notas en Markdown, copias con manifiesto documentado, EPUB originales sin modificar.
3. **Sin servidores.**
4. **Plataforma:** subir `targetSdk` cada año y probar en cada versión nueva de Android.
5. **Dependencias:** pocas, fijadas en el catálogo, y las inestables (editor, parser DSL) detrás de interfaces.
6. **Pruebas automáticas** para la lógica pura: título automático, normalización de etiquetas, límites, coincidencia de ubicaciones, restauración combinada, migraciones.
7. **Permisos mínimos:** sin INTERNET, sin almacenamiento global. Necesarios: notificaciones (TTS) y servicio de primer plano de reproducción.
8. **Sin telemetría.** Errores en el informe de actividad local.
9. **Documentación viva.**

## 8. Hoja de ruta sugerida
| Fase | Contenido |
|---|---|
| 0 | Cimientos: proyecto, tema M3, catálogo de versiones, Hilt (Room/DataStore llegan en su fase) |
| 1 | Núcleo de lectura: importar (SAF, Compartir), abrir con Readium, ajustes visuales, posición persistente, lista mínima de libros |
| 2 | **TTS completo**: reproducción, resaltado de párrafo, notificación, interrupciones, temporizador |
| 3 | **Notas** + selección contextual + resaltado básico + etiquetas + papelera + exportar Markdown |
| 4 | Biblioteca completa: colecciones, filtros, duplicados, eliminación y reimportación, marca de terminado y "Empezar a leer de nuevo" |
| 5 | Diccionarios StarDict + "Consultar en otra app" |
| 6 | Mis marcadores y resaltados (gestión completa) |
| 7 | Copias de seguridad, restauración y migraciones protegidas |
| 8 | Importación Markdown, DSL, accesibilidad, pulido y publicación (GitHub + Obtainium) |

## 9. Pendientes
1. Anotar modelo de teléfono y versión de Android de la prueba de WordReference.
2. Reglas abiertas de la especificación: recuperación de colecciones al reimportar, qué errores de EPUB son reparables, límites de DSL, accesibilidad y pruebas en distintas versiones de Android.
3. Propuestas de las secciones 2, 4 (umbral de más de 3 palabras) y 6 aún sin aprobar.
