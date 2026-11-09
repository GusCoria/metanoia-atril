# Informe de revisión de la Especificación Funcional Maestra

Fecha: 2026-11-05 · Estado de todo lo de este documento: **Propuesta / Revisión necesaria** (nada está confirmado hasta que el usuario lo apruebe). Parcialmente superado por `03-propuesta-v1-app-duradera.md` (traducción, actualizaciones, copias de seguridad).

Leyenda: **Pendiente** = requiere definición · **Propuesta** = recomendación no aprobada · **Revisión necesaria** = contradicción, ambigüedad o riesgo técnico.

---

## 1. Hallazgos de la revisión

### 1.1 Riesgos técnicos verificados (Revisión necesaria)
- **WordReference:** no se encontró una API pública vigente; la antigua requería clave. Ver sección 4: se propone retirarlo como proveedor.
- **ML Kit (traducción local):** orientado a traducciones simples, usa inglés como intermedio y gestiona sus propias descargas (choca con RF-TRA-004/005). Ver sección 4: se propone retirar la traducción local.
- **Copias "el día 1":** WorkManager es inexacto (Doze, mínimo 15 min). Propuesta: tarea periódica que ejecute la copia si la última es anterior al mes actual (cubre también "dispositivo apagado").
- **Actualización mensual (RF-UPD-001):** Play prohíbe REQUEST_INSTALL_PACKAGES para autoactualizarse, y comprobar versiones requiere servidor (no contemplado en 0.2). Falta decidir canal de distribución (Play, F-Droid, APK propio).
- **Play:** desde 2026-08-31 las apps nuevas y las actualizaciones deben apuntar a API 36.
- **TTS en segundo plano:** servicio en primer plano tipo `mediaPlayback` con su permiso (Android 14+); con Media3 el tipo mediaPlayback es obligatorio. Se necesitará además el permiso de notificaciones (Android 13+); documentarlo como necesario.
- **DSL:** existe stardict4j (StarDict, estado producción); para DSL no se encontró biblioteca (parser propio).
- **Readium Kotlin 3.1.2:** minSdk 23, compileSdk 36 → compatible con Android 10. Incluye `PublicationSpeechSynthesizer` y resaltados vía Decorator API.

### 1.2 Contradicciones y ambigüedades internas
- RF-LECT-009 lista "Justificación" como ajuste, pero RF-LECT-008 dice que no hay opciones de alineación.
- RF-BCK-003/005: conservar 4 copias automáticas incrementales sin romper cadenas exige consolidar la base; falta definirlo.
- RF-BCK-004 menciona una programación manual "ya definida" que no aparece en el documento.
- Copias automáticas en almacenamiento privado se pierden al desinstalar; considerar carpeta SAF opcional.
- Restauración combinada: falta tratar lo eliminado después de la copia (evitar resurrección de registros).
- ¿El límite de 500 caracteres cuenta texto visible o Markdown?
- Valores sin cifra: "unos segundos" (deshacer), "baja confianza" (reimportación), frecuencia de guardado periódico.
- Catálogos de diccionarios/modelos: no se define origen de descarga ni los diccionarios "incluidos por defecto" (RF-DIC-003/006); implica servicio externo y licencias.
- RF-NOT-009: el Markdown estándar no tiene subrayado; hay que decidir cómo se serializa.
- Mis notas: RF-NOT-021 habla de "resultados de una búsqueda", pero no se define la búsqueda en notas.

### 1.3 Faltantes
Enlaces internos y notas al pie con botón de volver · EPUB de diseño fijo · texto derecha-a-izquierda/vertical · cálculo del porcentaje · controles típicos (toques, teclas de volumen, orientación) · accesibilidad (TalkBack, escala de fuente) · idiomas de la interfaz · tabletas/plegables · requisitos no funcionales (tamaño máximo de EPUB, tiempos, memoria) · comportamiento ante desinstalación (evaluar `hasFragileUserData`, por verificar) · riesgo de copias sin contraseña (documentar).

---

## 2. Decisiones propuestas: lector y TTS (prioridad del proyecto)

### Lector
| ID | Decisión | Opciones | Recomendación |
|---|---|---|---|
| D-LEC-1 | Cómo alojar el lector en Compose | A) Fragment del navigator de Readium dentro de Compose (AndroidView / FragmentContainerView). B) UI completa con Views/Fragments. | **A** |
| D-LEC-2 | Ajustes de texto | `EpubPreferences` de Readium: fontSize, fontFamily, lineHeight, paragraphSpacing, hyphens, theme, scroll, textAlign, columnCount. Persistir con DataStore. | Usar la API de preferencias de Readium |
| D-LEC-3 | Cuatro márgenes independientes (RF-LECT-007) | `pageMargins` de Readium es un factor solo horizontal. A) Padding del contenedor Compose para los 4 lados. B) CSS inyectado. | **A** (Revisión necesaria: confirmar) |
| D-LEC-4 | Temas | Readium trae claro/oscuro/sepia. "Negro puro": `backgroundColor` + `textColor` propios. | Tema Readium + colores fijos para negro puro |
| D-LEC-5 | Justificación | `textAlign = JUSTIFY` fijo; quitar "Justificación" del panel Diseño. | Aplicar (resuelve la contradicción) |
| D-LEC-6 | Fuentes TTF/OTF importadas | Registrar como FontFamily de Readium. | Validar en prototipo |
| D-LEC-7 | Menú de selección | Menú contextual propio con las 6 acciones (RF-SEL-001) sobre la selección del navigator. | Validar en prototipo |
| D-LEC-8 | Resaltados superpuestos (pendiente 5) | A) Permitir superposición. B) Fusionar. C) Impedirlas. | **A**, sin recorte automático |
| D-LEC-9 | Posición | Guardar `Locator` completo (href, progression, totalProgression) + texto antes/después; guardar en onStop y periódicamente. | Aplicar |

### TTS
| ID | Decisión | Opciones | Recomendación |
|---|---|---|---|
| D-TTS-1 | Orquestación | A) `PublicationSpeechSynthesizer` de Readium (segmentación con locator, resaltado, avance de página). B) `TextToSpeech` propio. | **A**; B solo si el prototipo falla |
| D-TTS-2 | Sesión y notificación | A) Media3 `MediaSessionService` con un Player que envuelva el TTS. B) MediaSession clásica + notificación manual. | **A** (por verificar: encaje del envoltorio) |
| D-TTS-3 | Unidad de lectura | Párrafo (confirmado en RF-TTS-004/005). | Mantener |
| D-TTS-4 | Última posición TTS | Guardar locator por utterance con limitación de frecuencia; indicador independiente del progreso normal. | Aplicar |
| D-TTS-5 | Velocidad | 0.5x–2.0x acotado al rango que informe el motor. | Aplicar |
| D-TTS-6 | Temporizador | Corrutina en el servicio; "final del capítulo" al cambiar de recurso. | Aplicar |
| D-TTS-7 | Auriculares (RF-TTS-010) | Escuchar el aviso de salida de audio ruidosa y pausar. | Validar con auriculares reales (cable y Bluetooth) |
| D-TTS-8 | Voces multilingües | Etiqueta de idioma del EPUB; si no hay voz, voz principal. | Mantener |

---

## 3. Decisiones propuestas: notas

| ID | Decisión | Opciones | Recomendación |
|---|---|---|---|
| D-NOT-1 | Editor WYSIWYG | A) compose-rich-editor (`toMarkdown`/`setMarkdown`; versión 1.0.0-rc, activo en abril 2026, 74 issues abiertos). B) Editor propio sobre BasicTextField + parser Markdown. C) Views + Markwon. | **A** en prototipo; B como plan de respaldo |
| D-NOT-2 | Restringir formato | La biblioteca admite títulos, enlaces y código: ocultarlos de la barra y sanear lo pegado. | Aplicar |
| D-NOT-3 | Subrayado en Markdown | A) `<u>` embebido. B) Extensión propia. C) Quitar subrayado. | **A** (Revisión necesaria) |
| D-NOT-4 | Límite 500 caracteres | A) Texto visible. B) Markdown completo. | **A** |
| D-NOT-5 | Almacenamiento | Room; fragmento con locator + texto antes/después; etiquetas N:M con nombre normalizado único; papelera con `deletedAt` y limpieza diaria a 30 días. | Aplicar |
| D-NOT-6 | Búsqueda en notas | FTS de Room sobre título, contenido, etiquetas y fragmento. | Definir campos (Pendiente) |
| D-NOT-7 | Autoguardado | Debounce corto + guardado en onStop (valor a definir). | Aplicar |
| D-NOT-8 | Exportar/importar | Exportar con el menú Compartir; importar con SAF. | Mantener spec |

---

## 4. Traducción simplificada (superada por la propuesta v1; ver `03-propuesta-v1-app-duradera.md` sección 4)

Idea original: el diccionario offline atiende **palabras**; para **frases o párrafos**, "Traducir" abre otra app que el usuario ya tenga. Mecanismo: `ACTION_PROCESS_TEXT` con selector del sistema (en Android 11+ requiere `<queries>`).

Efectos sobre la especificación: se retiran casi todos RF-TRA-001 a 009; RF-SYS-001/002 solo aplican a diccionarios; WordReference deja de ser dependencia; la app podría no necesitar permiso INTERNET; Retrofit/OkHttp dejan de ser necesarios; las copias ya no incluyen modelos; la actualización de la app se delega a la tienda o gestor (afecta RF-UPD-001).

---

## 5. Orden sugerido para decidir
1. Traducción simplificada (afecta alcance, permisos y dependencias).
2. Canal de distribución y actualización.
3. Prototipo de lector con Readium: márgenes, fuentes, menú de selección, resaltados.
4. Prototipo de TTS: Readium + Media3, auriculares, notificación.
5. Prototipo del editor de notas (Markdown, subrayado, límite).
6. Reglas de copias: consolidación de cadenas, restauración y desinstalación.

Fuentes: documentación de Readium Kotlin (EpubPreferences, TTS), Android Developers (foreground service types, Media3, WorkManager, requisito de target API), Play Console Help (REQUEST_INSTALL_PACKAGES), ML Kit Translation, repositorios de stardict4j y compose-rich-editor.
