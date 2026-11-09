# Descripción del proyecto y alcance de la primera etapa

(Texto original del proyecto "Lector EPUB", hoy **Metanoia Atril**.)

## Tarea de la primera etapa
Crear la carpeta del proyecto, configurar el entorno inicial y preparar una estructura de código limpia y escalable. No adelantarse a implementar funcionalidades ni asumir decisiones no confirmadas. Más adelante habrá una Especificación Funcional Maestra (ver `01-especificacion-funcional-maestra.md`).

## 1. Descripción general
Aplicación Android nativa que permitirá:
- Importar y administrar libros EPUB.
- Organizar una biblioteca personal.
- Leer libros con diferentes opciones de visualización.
- Crear notas independientes.
- Administrar marcadores y resaltados.
- Consultar diccionarios offline.
- Traducir fragmentos de texto (la versión original decía "mediante WordReference y modelos locales"; **cambió**, ver `03-propuesta-v1-app-duradera.md` sección 4).
- Leer libros mediante síntesis de voz (TTS).
- Crear y restaurar copias de seguridad locales.

Uso personal, priorizando privacidad, almacenamiento local y funcionamiento sin conexión.

### 1.1 Alcance inicial
- Android, Kotlin, Android 10 como versión mínima.
- EPUB 2 y EPUB 3. Sin DRM ni libros protegidos con contraseña. Sin PDF ni TXT en la primera versión.
- Sin cuentas de usuario ni sincronización en la nube.

## 2. Tecnologías candidatas (propuesta inicial, no decisión definitiva)
Kotlin, Jetpack Compose, Readium (motor EPUB), Room/SQLite, MVVM, Coroutines y Flow, WorkManager, DataStore, Hilt, Retrofit y OkHttp (esta última **retirada**, ver propuesta).

Antes de incorporar dependencias: verificar compatibilidad de versiones, compatibilidad con la versión mínima de Android, estado de mantenimiento, documentación oficial, requisitos de configuración e incompatibilidades. No agregar bibliotecas innecesarias. Si una tecnología no es adecuada, documentar el motivo y presentar alternativa antes de modificar la propuesta.

## 3. Organización inicial
Estructura por capas y responsabilidades dentro de `app/src/main/java/<paquete>/`: `data/`, `domain/`, `ui/`, `service/`, `worker/`, `di/`, `MainActivity.kt`. Puede ajustarse a las convenciones actuales y a las necesidades reales. No llenar los paquetes con clases vacías; estructura inicial pequeña, funcional y justificada.

- `data/`: persistencia, fuentes de datos, entidades y repositorios.
- `domain/`: modelos de negocio, contratos y casos de uso.
- `ui/`: pantallas, componentes reutilizables y sistema visual.
- `service/`: integración con EPUB, TTS, traducción y diccionarios.
- `worker/`: tareas programadas y procesos en segundo plano.
- `di/`: configuración de dependencias.

## 4. Principios
1. Priorizar el almacenamiento local.
2. Evitar permisos globales de almacenamiento.
3. Usar los mecanismos nativos de Android para seleccionar archivos.
4. Separar la interfaz de la lógica de negocio.
5. Evitar dependencias innecesarias entre módulos.
6. Arquitectura que permita ampliar funcionalidades.
7. No implementar funcionalidades dentro de `MainActivity`.
8. Configuración de compilación reproducible.
9. Git desde el inicio.
10. Documentar decisiones técnicas importantes.

## 5. Interfaz inicial
Solo: app que compile y ejecute, pantalla inicial sencilla, tema básico Material 3, estructura lista para incorporar biblioteca y lector. Sin pantallas definitivas de notas, marcadores, traducción, diccionarios o ajustes.

## 6. Gestión del proyecto
Gradle con Kotlin DSL, Version Catalog, `.gitignore` Android/Kotlin, README inicial, configuración básica de compilación, identificador de aplicación provisional documentado. Sin credenciales, claves API ni configuraciones personales.

## 7. Forma de trabajo
Gradual, explicando decisiones. Orden: (1) inspeccionar entorno, (2) verificar versiones de Android Studio/JDK/Kotlin/Gradle, (3) proponer configuración y dependencias, (4) crear estructura, (5) configurar archivos, (6) compilar, (7) ejecutar en emulador o dispositivo, (8) documentar lo creado y los pendientes. Ante una incompatibilidad importante no continuar en silencio: explicar y presentar alternativas.

## 8. Especificación funcional posterior
No inventar funcionalidades, no implementar requisitos no definidos, no tratar esta descripción como la especificación completa, no descartar reorganizar paquetes o módulos, no tomar las tecnologías candidatas como inamovibles, no desarrollar lógica de negocio definitiva, no generar datos de prueba innecesarios. Al recibir la especificación maestra: analizarla y proponer ajustes a la estructura antes de implementar.

## 9. Resultado esperado de la etapa
Carpeta del proyecto, proyecto Android funcional, Gradle reproducible, estructura organizada, dependencias justificadas, app mínima que compile y se ejecute, README con instrucciones, resumen de decisiones técnicas y pendientes.
