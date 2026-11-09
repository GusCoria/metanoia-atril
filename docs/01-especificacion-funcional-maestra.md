ESPECIFICACIÓN FUNCIONAL MAESTRA

Aplicación Android nativa de lectura y administración de EPUB

Versión: Documento consolidado de requisitos
Estado: Consolidación de acuerdos conversacionales
Alcance: Especificación funcional y registro de decisiones. La arquitectura técnica definitiva permanece sujeta a validación.

---

0. Criterios generales del proyecto

0.1. Objetivo

Desarrollar una aplicación Android nativa para administrar una biblioteca personal y leer libros en formato EPUB, con herramientas de anotación, marcadores, resaltados, traducción, diccionarios offline y lectura mediante síntesis de voz.

La aplicación priorizará:

- Almacenamiento y procesamiento local.
- Privacidad y ausencia de cuentas de usuario.
- Funcionamiento sin conexión para las funciones que lo permitan.
- Persistencia de los datos personales del usuario.
- Interfaz nativa y adaptable a Android.
- Recuperación de información mediante copias de seguridad locales.

0.2. Alcance funcional

- Plataforma: Android.
- Lenguaje de desarrollo previsto: Kotlin.
- Versión mínima considerada: Android 10.
- Formato de libros inicial: EPUB 2 y EPUB 3.
- Los archivos EPUB protegidos mediante DRM o contraseña quedan fuera del alcance.
- PDF y TXT no forman parte de la primera versión.
- No se contempla sincronización en la nube.
- No se requieren cuentas ni autenticación.
- Los servicios externos se utilizarán únicamente en las funciones que expresamente los requieran, como determinadas consultas de WordReference.

0.3. Clasificación de requisitos

Cada requisito debe tener uno de estos estados:

- Confirmado: decisión tomada expresamente.
- Pendiente: comportamiento que todavía requiere definición.
- Propuesta técnica: alternativa de implementación que no representa una decisión funcional.
- Requiere revisión: regla incorporada en documentos posteriores que no coincide con los acuerdos anteriores o cuya confirmación no está disponible.

No se deben convertir propuestas técnicas en decisiones confirmadas.

---

1. Módulo de importación y administración de archivos

1.1. Importación de libros

RF-IMP-001. Importación mediante mecanismos nativos

La aplicación permitirá importar EPUB mediante:

- Selector de archivos nativo de Android.
- Menú Compartir.
- Acción Abrir con.
- Selección de varios archivos en una misma operación.

Reglas:

- La importación múltiple debe procesar cada archivo individualmente.
- Si un archivo falla, los demás deben continuar.
- El usuario debe recibir el resultado de cada importación.
- No se deben solicitar permisos de acceso global al almacenamiento.
- Se utilizará Storage Access Framework (SAF) para los accesos externos.

RF-IMP-002. Ubicación de almacenamiento de los libros

El usuario podrá decidir dónde almacenar los archivos importados:

- Almacenamiento privado de la aplicación.
- Carpeta externa seleccionada mediante el selector nativo.

La aplicación deberá conservar la referencia y los permisos necesarios para acceder a los archivos.

El usuario podrá modificar posteriormente la ubicación del EPUB, conservando su registro y las anotaciones relacionadas.

El cambio de ubicación no deberá interpretarse como la incorporación de un libro nuevo.

Estado: Confirmado.

1.2. Metadatos y portadas

RF-IMP-003. Extracción de metadatos

Se extraerán del EPUB, cuando estén disponibles:

- Título.
- Autor.
- Idioma.
- Editorial.
- Portada.
- Otros metadatos útiles proporcionados por el archivo.

No se descargarán portadas ni metadatos desde servicios externos.

No se generarán portadas artificiales para reemplazar las que falten.

1.3. Validación y reparación

RF-IMP-004. Manejo de EPUB dañados

Al importar un archivo, la aplicación intentará validar su estructura.

Si se detectan errores recuperables:

- Intentará reparar el archivo.
- Si la reparación tiene éxito, utilizará la versión corregida.
- Si falla, conservará intacto el archivo original.
- Informará al usuario del resultado.

La reparación no deberá provocar la pérdida silenciosa del archivo original.

1.4. Duplicados y sustitución

RF-IMP-005. Detección de duplicados

La detección se realizará mediante el contenido del archivo, no exclusivamente por el nombre.

Ante un duplicado, el usuario podrá:

- Conservar el registro existente.
- Sustituir el EPUB existente por el nuevo archivo.

Al sustituir:

- Se comparará la estructura, capítulos y contenido.
- Se intentará recuperar la relación de notas, marcadores y resaltados compatibles.
- Se identificará qué referencias pudieron recuperarse.
- Se informará cuáles no pudieron recuperarse.
- No se deberán asignar referencias a ubicaciones incorrectas cuando exista poca confianza en la correspondencia.

La recuperación de anotaciones deberá respetar las diferencias entre notas persistentes y elementos dependientes del libro.

1.5. Eliminación de libros

RF-IMP-006. Eliminación del libro de la biblioteca

Al eliminar un libro:

- Se retirará de la biblioteca.
- Se eliminará su acceso directo desde las colecciones.
- Se conservará la información interna necesaria para intentar recuperar asociaciones al reimportar el mismo EPUB.
- Las notas permanecerán en Mis notas.
- Los marcadores y resaltados dejarán de mostrarse mientras el libro no esté disponible.
- No se eliminarán automáticamente las notas por eliminar el EPUB.

La conservación interna no significa que los elementos eliminados deban seguir apareciendo como si el libro estuviera disponible.

1.6. Formatos no soportados

RF-IMP-007. Rechazo de formatos incompatibles

La primera versión aceptará EPUB.

Los archivos PDF y TXT no se incorporarán como libros en esta versión.

---

2. Módulo de biblioteca y colecciones

2.1. Vistas

RF-BIB-001. Vista de cuadrícula y lista

La biblioteca dispondrá de:

- Vista de cuadrícula.
- Vista de lista.

La cuadrícula mostrará la portada y la información básica.

La lista mostrará una miniatura, título, autor y progreso de lectura.

La aplicación recordará la última vista seleccionada.

2.2. Información visual de los libros

RF-BIB-002. Estados de lectura

Se contemplan los estados:

- No iniciado.
- En progreso.
- Terminado.

La interfaz deberá distinguir visualmente los estados.

También se contemplan:

- Libros favoritos.
- Porcentaje de lectura.
- Indicador de libro terminado.

En cuadrícula:

- El porcentaje se acompañará de un icono de libro abierto.
- Los libros terminados mostrarán un libro cerrado con una marca de verificación.
- El corazón de favorito aparecerá sobre la portada.

En lista, los indicadores aparecerán junto a la información del libro.

2.3. Ordenación

RF-BIB-003. Criterios de ordenación

Se podrá ordenar por:

- Fecha de incorporación.
- Título.
- Autor.
- Última lectura.

Cada criterio podrá ordenarse ascendente o descendentemente.

Se conservará la última configuración de ordenación.

2.4. Búsqueda y filtros

RF-BIB-004. Búsqueda de biblioteca

La búsqueda se realizará sobre los metadatos de los libros.

No se indexará el texto completo de los EPUB.

RF-BIB-005. Filtros combinables

Se podrán combinar filtros por:

- Estado de lectura.
- Autor.
- Idioma.
- Editorial.

2.5. Colecciones personalizadas

RF-BIB-006. Administración de colecciones

El usuario podrá:

- Crear colecciones.
- Cambiar sus nombres.
- Eliminar colecciones.
- Asociar un libro a varias colecciones.

Eliminar una colección no eliminará los libros que contiene.

Al eliminar un libro, sus asociaciones se conservarán internamente para permitir una posible recuperación posterior.

La aplicación no deberá crear asociaciones nuevas incorrectas durante una reimportación.

Estado de la recuperación automática de asociaciones: requiere revisión del comportamiento final.

---

3. Módulo de lectura EPUB

3.1. Navegación

RF-LECT-001. Modos de lectura

Se ofrecerán dos modos:

- Desplazamiento vertical continuo.
- Paginación horizontal.

La preferencia será global y se aplicará a los libros.

RF-LECT-002. Tabla de contenidos

El lector permitirá consultar la estructura de navegación del EPUB.

El usuario podrá acceder a capítulos y secciones desde la tabla de contenidos.

3.2. Persistencia de posición

RF-LECT-003. Continuidad de lectura

La aplicación conservará la última ubicación de lectura.

Al reabrir un libro en progreso, se intentará regresar a la ubicación exacta guardada.

La ubicación debe considerar la estructura del EPUB y no depender exclusivamente del número visual de página.

Al volver a leer un libro terminado:

- Se reiniciará la posición.
- Se restablecerá el progreso.
- Se conservarán las notas.
- Se conservarán los marcadores.
- Se conservarán los resaltados.

3.3. Controles del lector

RF-LECT-004. Interfaz de lectura

Los controles podrán ocultarse para ofrecer una experiencia de lectura sin distracciones.

Al tocar la pantalla, los controles podrán volver a mostrarse.

La configuración de brillo utilizará el comportamiento nativo de Android; no se contempla un control independiente de brillo dentro de la aplicación.

3.4. Configuración visual

RF-LECT-005. Temas

Se ofrecerán:

- Claro.
- Oscuro.
- Sepia.
- Negro puro.

No se ofrecerá personalización individual de colores de fondo y texto.

RF-LECT-006. Tamaño y familia tipográfica

Se permitirá:

- Modificar el tamaño del texto mediante un control deslizante.
- Seleccionar familias tipográficas predeterminadas.
- Importar fuentes TTF y OTF.
- Eliminar fuentes importadas.

Si una fuente está en uso, se solicitará seleccionar otra antes de eliminarla.

No se contempla modificar individualmente el peso de la fuente.

RF-LECT-007. Espaciado y márgenes

Se configurarán independientemente:

- Interlineado.
- Espaciado entre párrafos.
- Margen izquierdo.
- Margen derecho.
- Margen superior.
- Margen inferior.

RF-LECT-008. Formato del texto

El texto estará justificado.

No se ofrecerán opciones de alineación izquierda o derecha.

El guionado automático podrá activarse o desactivarse.

Los títulos y encabezados deberán respetar la estructura del EPUB.

3.5. Panel de configuración del lector

RF-LECT-009. Organización de ajustes

El panel tendrá tres categorías:

1. Texto:
   
   - Tamaño.
   - Familia tipográfica.
   - Interlineado.
   - Espaciado entre párrafos.

2. Diseño:
   
   - Márgenes.
   - Justificación.
   - Guionado.

3. Pantalla:
   
   - Tema.
   - Navegación.
   - Mantener pantalla encendida.
   - Modo sin distracciones.

Reglas:

- El panel se superpondrá al lector sin perder la posición.
- Los cambios se aplicarán inmediatamente.
- Los cambios se guardarán.
- Siempre abrirá inicialmente en la categoría Texto.
- No recordará la última categoría utilizada.
- No incluirá controles pequeños para incrementos individuales.
- El restablecimiento general no estará disponible dentro del panel del lector.

RF-LECT-010. Mantener pantalla encendida

La opción será una preferencia global.

Si se modifica dentro de un libro, el cambio afectará a toda la aplicación.

3.6. Finalización de lectura

El comportamiento exacto para marcar automáticamente un libro como terminado y la existencia de una acción manual requieren revisión.

No se debe considerar confirmado el requisito RF-LECT-005 del documento de Gemini sin recuperar la decisión original.

3.7. Búsqueda dentro del libro

No se contempla búsqueda de texto completo dentro del EPUB en la especificación acordada hasta ahora.

Por tanto, el buscador interno descrito en el documento de Gemini, junto con su botón de retorno seguro, no debe incorporarse como requisito confirmado.

---

4. Módulo Mis notas

4.1. Independencia de las notas

RF-NOT-001. Persistencia independiente

Mis notas será una sección independiente de la biblioteca.

Las notas:

- No dependerán de que el EPUB siga disponible.
- Permanecerán después de eliminar un libro.
- Podrán existir sin libro asociado.
- Podrán tener una asociación editable con un libro y capítulo.

Mis notas y Mis marcadores serán módulos diferentes.

4.2. Tipos de notas

RF-NOT-002. Modalidades

Se admitirán tres modalidades:

1. Nota con fragmento:
   
   - Conserva un fragmento original del EPUB.
   - Puede tener libro y capítulo asociados.

2. Nota de capítulo:
   
   - Asociada a un libro y capítulo.
   - No requiere fragmento seleccionado.

3. Nota independiente:
   
   - No depende de un libro ni capítulo.

La interfaz deberá identificar el tipo de nota.

4.3. Creación desde el lector

RF-NOT-003. Creación contextual

Desde el lector se podrá crear una nota:

- Seleccionando texto.
- A partir de un resaltado existente.
- Mediante la acción Nueva nota sin selección.

Si se crea sin selección, se asociará automáticamente al capítulo actual.

Las notas creadas desde el lector comenzarán sin etiquetas.

Las etiquetas podrán agregarse posteriormente desde Mis notas.

4.4. Creación desde Mis notas

RF-NOT-004. Creación independiente o asociada

Desde Mis notas se podrá crear:

- Una nota independiente.
- Una nota asociada a un libro y capítulo.

Durante la creación se podrán seleccionar etiquetas existentes o crear nuevas.

4.5. Conversión de resaltados en notas

RF-NOT-005. Conversión

Convertir un resaltado en nota:

- Creará una nota persistente.
- Vinculará la nota al mismo fragmento.
- Mantendrá el resaltado original.
- Mantendrá el color del resaltado.
- Permitirá editar o eliminar la nota independientemente del resaltado.

4.6. Asociación y desvinculación

RF-NOT-006. Edición de asociaciones

Se permitirá:

- Asociar una nota independiente a un libro.
- Cambiar el libro asociado.
- Cambiar el capítulo asociado.
- Eliminar la asociación con el libro.

La asociación actual y la ubicación histórica original son datos diferentes.

Al desvincular una nota:

- Se conservará el fragmento original como referencia histórica.
- Se eliminará el acceso directo al libro y capítulo.
- No se modificará automáticamente el fragmento original.

Al cambiar una nota del libro A al libro B:

- Se conservará la referencia histórica del libro A.
- Se asociará la nota al libro B.
- No se intentará trasladar automáticamente el fragmento al libro B.

4.7. Título y contenido

RF-NOT-007. Título automático

Al crear una nota, se generará un título usando las primeras ocho palabras del contenido.

Reglas:

- Se ignorarán espacios y saltos de línea iniciales.
- Se conservará la puntuación.
- Si hay menos de ocho palabras, se utilizará el contenido disponible.
- El título será editable.
- Modificar el contenido no actualizará automáticamente el título.
- Se permiten títulos duplicados.
- El título tendrá un máximo de 50 caracteres.
- La generación automática no cortará palabras; respetará el límite aunque deba utilizar menos de ocho palabras.

RF-NOT-008. Límites

- Contenido: máximo 500 caracteres.
- Título: máximo 50 caracteres.
- Fragmento original: máximo 5000 caracteres.

El límite del fragmento original es independiente del límite del contenido.

No se permitirá crear una nota con un fragmento seleccionado que exceda los 5000 caracteres.

Si se pega contenido que excede los 500 caracteres:

- Se insertará únicamente la parte que quepa.
- Se conservará el contenido existente.
- No se permitirá exceder el límite.

El contenido completo podrá corregirse, reemplazarse o eliminarse respetando siempre el límite.

4.8. Editor

RF-NOT-009. Editor WYSIWYG

El editor será visual y almacenará internamente el contenido mediante Markdown.

Formato permitido:

- Negrita.
- Cursiva.
- Subrayado.
- Listas ordenadas.
- Listas desordenadas.
- Citas.

No se permitirá:

- Encabezados H1-H3.
- Imágenes.
- Enlaces web interactivos.
- Contadores.
- Colores de texto.
- Resaltados dentro del editor.
- Listas de verificación.
- Separadores.
- Código o tipografía monoespaciada.
- Tamaños individuales.
- Alineaciones individuales.

El tamaño y la alineación serán uniformes.

RF-NOT-010. Edición y guardado

- Guardado automático.
- Sin botón de guardar.
- La edición no se perderá al salir.
- La fecha de modificación cambiará al editar título o contenido.
- Se permitirá deshacer y rehacer mientras el editor permanezca abierto.
- No se conservará un historial permanente de versiones.
- Al crear una nota, el teclado se abrirá automáticamente.
- El cursor aparecerá al inicio del área de escritura.
- La barra de formato permanecerá accesible con el teclado abierto o cerrado.

RF-NOT-011. Eliminación desde el editor

Eliminar una nota desde el editor la enviará directamente a la papelera.

No se solicitará confirmación adicional.

Al terminar, se regresará a la lista.

4.9. Dictado

RF-NOT-012. Entrada por voz

Se utilizará el reconocimiento de voz nativo de Android o del teclado.

No se desarrollará un sistema de reconocimiento de voz propio.

El dictado respetará el límite de 500 caracteres.

4.10. Etiquetas

RF-NOT-013. Administración de etiquetas

Las etiquetas:

- Tendrán un máximo de 20 caracteres.
- No tendrán colores.
- No tendrán descripciones.
- Se mostrarán alfabéticamente en selectores y filtros.
- Se podrán reutilizar.

Eliminar una etiqueta:

- Eliminará sus asociaciones globales.
- No eliminará las notas.
- No eliminará el contenido.

Las etiquetas sin notas asociadas permanecerán hasta que el usuario las elimine.

La eliminación de una etiqueta podrá deshacerse durante unos segundos, recuperando también sus asociaciones.

RF-NOT-014. Normalización

Para evitar duplicados:

- Se ignorarán diferencias entre mayúsculas y minúsculas.
- Se normalizarán espacios.
- Se eliminarán espacios al inicio y final.
- Se normalizarán secuencias repetidas de espacios.

No se crearán etiquetas duplicadas después de normalizar.

4.11. Vistas y navegación

RF-NOT-015. Organización

Se ofrecerán:

- Vista cronológica, más recientes primero.
- Vista agrupada por libro y capítulo.

Una nota no deberá aparecer duplicada por el hecho de tener varios metadatos asociados.

Las notas sin libro disponible se mostrarán en una categoría específica:

Libros no disponibles.

Se conservarán los metadatos históricos del libro.

RF-NOT-016. Visualización

Al abrir una nota se mostrará:

- Título.
- Contenido.
- Fecha de creación.
- Fecha de modificación.
- Etiquetas.
- Información del libro, autor y capítulo cuando corresponda.
- Fragmento original, cuando exista.

Si el libro está disponible y se puede recuperar la ubicación, se ofrecerá acceso directo.

Si no está disponible, la nota podrá consultarse sin navegación.

Las notas asociadas a un capítulo, sin fragmento, abrirán el inicio del capítulo sin modificar el progreso de lectura.

Las notas independientes no tendrán navegación hacia un libro.

4.12. Eliminación de libros y recuperación

RF-NOT-017. Conservación histórica

Al eliminar un libro:

- Las notas permanecerán.
- Se eliminará su asociación activa.
- Se conservarán título, contenido, autor, capítulo y fragmento original como información histórica.
- Se eliminará el acceso directo al libro.
- La nota se mostrará como libro no disponible.

RF-NOT-018. Reimportación

Al reimportar un EPUB anteriormente eliminado:

- La aplicación detectará notas históricamente relacionadas.
- Ofrecerá recuperar las asociaciones.
- No las asociará automáticamente sin autorización.
- Presentará candidatos según la confianza de correspondencia.
- Las asociaciones de baja confianza permanecerán sin recuperar.

Se permitirá:

- Revisar una propuesta general.
- Revisar individualmente.
- Aceptar o rechazar.
- Modificar antes de confirmar.

La recuperación se aplicará después de una confirmación final.

4.13. Papelera de notas

RF-NOT-019. Papelera independiente

Mis notas tendrá una papelera independiente de la papelera de marcadores y resaltados.

Las notas permanecerán en ella durante 30 días.

Al terminar el plazo:

- Se eliminarán permanentemente.
- No se notificará cada eliminación automática.
- No se solicitará confirmación.

Se mostrará la fecha de eliminación definitiva, no una cuenta regresiva de días restantes.

Mientras una nota esté en la papelera:

- No podrá editarse.
- No podrán modificarse sus etiquetas.
- No podrá cambiarse su asociación.

La papelera permitirá:

- Consultar contenido y metadatos.
- Restaurar notas.
- Eliminar individualmente.
- Vaciar la papelera con confirmación.

La lista estará ordenada por fecha de eliminación, más recientes primero.

Al restaurar:

- Si el libro sigue disponible, se recuperará la asociación anterior.
- Si no está disponible, se conservará la información histórica y se mostrará como no disponible.
- No se convertirá automáticamente en nota independiente.

La restauración múltiple recuperará directamente las asociaciones anteriores, sin revisión individual.

4.14. Selección múltiple

RF-NOT-020. Acciones por lote

En Mis notas se permitirá seleccionar varias notas para:

- Enviar a papelera.
- Asignar etiquetas.
- Quitar etiquetas.

No se permitirá cambiar asociaciones en bloque.

Al asignar etiquetas se podrá elegir:

- Agregar: conserva las etiquetas actuales y agrega las seleccionadas.
- Reemplazar: elimina las etiquetas actuales y deja únicamente las seleccionadas.

4.15. Exportación Markdown

RF-NOT-021. Exportación

Se permitirá exportar:

- Una nota.
- Varias notas seleccionadas.
- Todas las notas.
- Los resultados de una búsqueda o filtro.

El formato será Markdown (.md).

Reglas:

- Varias notas se incluirán en un mismo archivo.
- Cada nota tendrá una separación mediante encabezados.
- Si se exporta una sola nota, comenzará directamente con su contenido y no tendrá índice.
- Si se exportan varias, se incluirá índice o tabla de contenido.
- Se respetará el orden y los filtros de la vista.
- Los campos vacíos se omitirán.
- Se incluirán título, contenido, metadatos, etiquetas y fechas de creación y modificación.
- No se agregará el nombre de la aplicación ni la fecha de exportación.
- Se utilizará el mecanismo nativo de compartir de Android.

4.16. Importación Markdown

RF-NOT-022. Importación

La aplicación permitirá importar notas desde Markdown.

Ante duplicados, se ofrecerán opciones por nota:

- Conservar la existente.
- Reemplazarla.
- Importarla como una nota nueva.

Si una estructura no puede reconocerse:

- Se importará como nota independiente.
- Se conservará el texto original.
- Se informará al usuario.

Los campos no soportados se conservarán como texto cuando corresponda.

Para identificar notas:

- Se utilizarán encabezados y estructura.
- No se utilizará el índice como identificador.

Se eliminarán referencias a enlaces, imágenes y archivos adjuntos, conservando el texto.

Se reconocerán los formatos admitidos por el editor.

Las fechas válidas se conservarán.

Las fechas faltantes o inválidas utilizarán la fecha de importación para el campo correspondiente.

Las etiquetas desconocidas se crearán automáticamente, siempre que respeten los límites.

Las etiquetas se normalizarán y reutilizarán cuando ya existan.

Si el contenido supera 500 caracteres:

- Se solicitará reducirlo.
- No se truncará automáticamente.
- No se dividirá en varias notas.
- La nota no se importará hasta que se ajuste.

Si el título supera 50 caracteres, se solicitará reducirlo.

En importaciones múltiples, las notas válidas podrán procesarse mientras las que excedan los límites permanecerán pendientes de corrección.

---

5. Módulo Mis marcadores y resaltados

5.1. Independencia respecto a Mis notas

RF-MAR-001. Elementos vinculados al libro

Los marcadores y resaltados estarán vinculados al EPUB.

No serán elementos persistentes independientes como las notas.

Se administrarán en una sección separada denominada Mis marcadores.

La sección tendrá un selector para alternar entre:

- Marcadores.
- Resaltados.

5.2. Organización

RF-MAR-002. Vistas

Se ofrecerán:

- Orden cronológico, más recientes primero.
- Vista agrupada por libro y capítulo.

La búsqueda permitirá localizar por:

- Libro.
- Autor.
- Capítulo.
- Texto resaltado.

Se podrán combinar filtros independientes por:

- Libro.
- Fecha de creación.
- Color.

5.3. Resaltados

RF-MAR-003. Colores

Se ofrecerá una paleta de colores predefinidos y un selector para crear colores personalizados.

Los colores personalizados podrán reutilizarse.

Un resaltado conservará:

- Fragmento.
- Ubicación.
- Color.

No tendrá título ni descripción.

RF-MAR-004. Edición de color

Se podrá cambiar el color desde:

- El lector.
- Mis marcadores.

El cambio deberá sincronizarse en ambas interfaces.

5.4. Marcadores

RF-MAR-005. Ubicación y metadatos

Un marcador guardará la ubicación del EPUB.

El usuario podrá editar:

- Título.
- Descripción.

La edición no modificará la ubicación original.

5.5. Consulta y navegación

RF-MAR-006. Vista previa

Mis marcadores permitirá:

- Consultar el fragmento asociado.
- Consultar el capítulo.
- Abrir la ubicación dentro del libro cuando esté disponible.

5.6. Libros no disponibles

RF-MAR-007. Dependencia del EPUB

Cuando un libro sea eliminado o no esté disponible:

- Sus marcadores y resaltados dejarán de aparecer como elementos activos.
- No se permitirá navegar a sus ubicaciones.
- Se conservarán internamente los datos necesarios para intentar recuperarlos al reimportar el mismo EPUB.

No se asignarán ubicaciones incorrectas cuando la correspondencia sea poco confiable.

5.7. Eliminación

RF-MAR-008. Eliminación sin papelera

Mis marcadores no tendrá una papelera permanente.

La eliminación será inmediata, con posibilidad de deshacer durante unos segundos.

La eliminación del EPUB no eliminará los datos internos de recuperación.

5.8. Selección múltiple

RF-MAR-009. Acciones múltiples

Se permitirá seleccionar varios marcadores o resaltados para eliminarlos.

No se contempla exportación múltiple de estos elementos.

5.9. Diferencia entre resaltado y nota

RF-MAR-010. Conversión

Un resaltado podrá convertirse en nota.

La nota será independiente y persistente.

El resaltado continuará existiendo dentro del libro y en Mis marcadores.

Eliminar una nota creada desde un resaltado no eliminará automáticamente el resaltado.

---

6. Módulo de traducción

6.1. Estrategia híbrida

RF-TRA-001. Proveedores

La traducción combinará:

- WordReference para consultas compatibles con sus capacidades de diccionario y equivalencias.
- Modelos de traducción locales descargables para traducción automática de textos.

La aplicación deberá mantener una arquitectura extensible de proveedores.

No se asumirá que WordReference ofrece traducción automática completa de párrafos ni que dispone de una API pública adecuada.

6.2. Modalidades de traducción

RF-TRA-002. Preferencia global

El usuario podrá elegir entre cuatro modos:

1. Automático.
2. Siempre WordReference.
3. Siempre local.
4. Preguntar cada vez.

La opción inicial será Automático.

Automático:

- Con conexión, priorizará WordReference cuando la consulta sea compatible.
- Sin conexión, utilizará un modelo local compatible.
- Si WordReference no puede resolver el tipo de consulta, se podrán considerar alternativas locales compatibles.
- No deberá realizar cambios silenciosos de proveedor durante una consulta que ya está en curso.

Siempre WordReference:

- Utilizará exclusivamente WordReference.
- Si no está disponible, informará al usuario.
- No cambiará automáticamente a un modelo local.

Siempre local:

- Utilizará exclusivamente modelos instalados.
- No recurrirá a servicios externos.
- Si no existe un modelo compatible, informará y ofrecerá alternativas locales descargables.

Preguntar cada vez:

- Permitirá elegir el proveedor para cada consulta.

6.3. Fallos de conexión

RF-TRA-003. Pérdida de conexión

Si WordReference pierde conexión durante una consulta:

- Se informará del problema.
- Se ofrecerá reintentar.
- Se podrá ofrecer una traducción local si existe un modelo compatible.
- No se cambiará automáticamente de proveedor.
- Se conservarán el texto seleccionado, la posición y el contexto de lectura.

Si WordReference no admite el par de idiomas:

- Se informará.
- Se mostrarán los modelos locales compatibles.
- Se mostrarán los diccionarios instalados que puedan complementar la consulta.
- El usuario decidirá cómo continuar.

6.4. Modelos locales

RF-TRA-004. Administración de modelos

Se podrán descargar modelos de traducción.

Cada modelo deberá mostrar:

- Nombre.
- Idiomas soportados.
- Dirección o direcciones de traducción.
- Tamaño individual.
- Espacio total utilizado.
- Espacio disponible.

No se descargarán modelos automáticamente.

Antes de descargar o actualizar:

- Se comprobará el espacio disponible.
- Se informará del tamaño.
- Se solicitará autorización.

No se eliminará un modelo en uso.

Si se requiere liberar espacio, se informará y se solicitará autorización.

RF-TRA-005. Actualizaciones

Las actualizaciones se notificarán, pero no se instalarán automáticamente.

Cuando se actualice un modelo:

- Se intentará conservar el anterior durante la descarga.
- Se verificará la integridad del nuevo.
- No se sustituirá el anterior antes de verificar el nuevo.
- Si no existe espacio suficiente, se ofrecerá una alternativa que requiera autorización.
- Si falla la actualización, se conservará el estado más seguro posible.

Las descargas interrumpidas podrán reanudarse cuando sea posible.

Los archivos parciales dañados o irrecuperables se eliminarán y deberán descargarse nuevamente.

No se utilizarán modelos incompletos.

6.5. Idiomas

RF-TRA-006. Idioma de origen y destino

El idioma de origen se detectará automáticamente.

El usuario podrá corregirlo manualmente.

La detección o corrección no modificará el idioma de la aplicación.

El idioma de destino inicial será el idioma principal de la aplicación.

Se recordará globalmente el último idioma de destino elegido.

La preferencia no será individual por libro.

6.6. Textos extensos

RF-TRA-007. Traducción de textos largos

Se aceptará texto de cualquier longitud dentro de las capacidades de almacenamiento y procesamiento.

Los textos extensos se dividirán automáticamente en segmentos compatibles con el proveedor elegido.

La traducción deberá:

- Mantener el orden original.
- Reconstruir un resultado continuo.
- Evitar pérdidas de fragmentos.
- Mantener la relación entre texto original y traducción.

No está confirmada una estrategia rígida de segmentación por párrafo, oración y palabra ni el procesamiento sin contexto.

Los límites específicos de cada proveedor y las estrategias para mantener coherencia entre segmentos requieren definición.

6.7. Panel de traducción

RF-TRA-008. Presentación

El panel mostrará:

- Texto original.
- Traducción.
- Proveedor utilizado.

Se podrá seleccionar el idioma de origen y destino mediante controles rápidos.

El texto original aparecerá primero y la traducción debajo.

RF-TRA-009. Copiado

El usuario podrá:

- Copiar únicamente la traducción.
- Copiar conjuntamente el texto original y su traducción.

La selección entre ambas modalidades estará disponible en el panel.

---

7. Módulo de diccionarios offline

7.1. Independencia de traducción

RF-DIC-001. Motor independiente

Los diccionarios serán independientes del motor de traducción automática.

Podrán complementar una consulta de traducción, pero no se considerarán el mismo servicio.

7.2. Formatos

RF-DIC-002. Formatos compatibles

Se contemplan:

- StarDict.
- DSL.

Los diccionarios se instalarán mediante selección de archivos y Storage Access Framework.

Una vez instalados, estarán disponibles sin conexión.

7.3. Administración

RF-DIC-003. Gestión de diccionarios

La configuración permitirá:

- Consultar diccionarios instalados.
- Consultar idiomas o pares de idiomas.
- Consultar tamaño.
- Consultar versión.
- Descargar.
- Actualizar.
- Eliminar.

No habrá descargas ni actualizaciones automáticas.

RF-DIC-004. Ubicación

El usuario podrá seleccionar una ubicación mediante SAF.

Una vez descargado un diccionario:

- No se moverá automáticamente.
- Cambiar la ubicación requerirá eliminarlo y descargarlo nuevamente.
- Si se revoca el acceso a la carpeta, se notificará.
- Se solicitará seleccionar una nueva ubicación.
- Se conservará el registro del diccionario.

RF-DIC-005. Integridad

Se verificará la integridad después de descargar y antes de utilizar.

Un diccionario dañado:

- No se utilizará.
- Se notificará.
- Se ofrecerá descargarlo nuevamente.
- Se conservará su registro y configuración.

7.4. Idiomas y selección

RF-DIC-006. Diccionarios por par

Se permitirán varios diccionarios para un mismo par de idiomas.

Solo uno estará activo por par.

No se combinarán resultados de diferentes diccionarios.

Un diccionario podrá permitir ambas direcciones cuando su contenido lo soporte.

Los diccionarios incluidos de forma predeterminada no podrán eliminarse ni actualizarse.

No se contempla búsqueda, filtro u ordenación del catálogo.

7.5. Consulta

RF-DIC-007. Panel contextual

Al seleccionar texto y elegir Diccionario:

- Se abrirá un panel inferior expandible.
- No se perderá la posición del lector.
- Se mostrarán todos los sentidos disponibles.
- Se mostrarán categorías gramaticales.
- Se mostrarán ejemplos.
- Se mostrarán traducciones y equivalencias disponibles.

El diccionario no inventará definiciones ni completará resultados inexistentes.

---

8. Selección contextual y portapapeles

8.1. Acciones disponibles

RF-SEL-001. Menú contextual

Al seleccionar texto dentro del lector, se ofrecerán:

1. Copiar.
2. Copiar como cita.
3. Diccionario.
4. Traducir.
5. Resaltar.
6. Crear nota.

Cada acción deberá conservar la selección y la posición del libro cuando abra un panel.

8.2. Copiar como cita

RF-SEL-002. Formato

La cita incluirá:

Fragmento seleccionado.

— Autor, Título del libro

Si falta algún metadato:

- Se omitirá el dato ausente.
- No se insertarán textos genéricos como «Desconocido».
- No se mostrarán separadores innecesarios.

---

9. Módulo de lectura en voz alta (TTS)

9.1. Motor

RF-TTS-001. Síntesis nativa

Se utilizará el motor Text-to-Speech de Android.

La función está orientada a acompañar la lectura, no a sustituir un reproductor de audiolibros.

9.2. Idioma y voz

RF-TTS-002. Selección de idioma

El idioma principal se obtendrá de los metadatos del EPUB.

Se buscarán voces compatibles con el idioma.

La preferencia global de voz podrá considerar voces femeninas o masculinas cuando el motor lo permita.

Si el motor no ofrece esa distinción, se utilizará su voz predeterminada.

Si no existe una voz compatible:

- Se informará.
- Se indicará cómo instalar o habilitar una voz.
- Se podrá abrir la configuración TTS de Android.

RF-TTS-003. Prueba de voz

La configuración permitirá probar las voces disponibles.

La prueba utilizará la misma velocidad y tono configurados.

No se recordará la última voz probada como selección automática.

9.3. Reproducción

RF-TTS-004. Controles

Se ofrecerá:

- Reproducir.
- Pausar.
- Reanudar.
- Siguiente párrafo.
- Párrafo anterior.

Si se solicita regresar cuando se está al inicio de un párrafo, se retrocederá al párrafo anterior.

Se podrá iniciar la lectura desde un párrafo seleccionado.

RF-TTS-005. Seguimiento visual

Durante la reproducción:

- Se resaltará el párrafo actual.
- Se identificará automáticamente.
- El lector podrá desplazarse para mantenerlo visible.
- Se mostrará el progreso.
- Se conservará un indicador de la última posición TTS.

9.4. Velocidad y tono

RF-TTS-006. Configuración

La velocidad se podrá ajustar entre 0.5x y 2.0x.

El tono dependerá de las capacidades del motor.

Si el motor no admite un control, se omitirá.

Al modificar la velocidad durante la reproducción, se pausará la lectura.

9.5. Temporizador

RF-TTS-007. Temporizador

Se podrá configurar:

- Duración determinada.
- Final del capítulo.

Al llegar al límite, la reproducción se pausará.

Sin temporizador, continuará hasta que el usuario la pause o detenga.

9.6. Segundo plano

RF-TTS-008. Notificación y pantalla bloqueada

El TTS continuará en segundo plano.

Durante la reproducción se mostrará una notificación con:

- Portada.
- Título.
- Autor.
- Retroceder.
- Pausar o reanudar.
- Avanzar.

Los controles de pantalla bloqueada se mostrarán cuando Android lo permita.

La notificación solo estará activa durante una sesión TTS.

9.7. Interrupciones

RF-TTS-009. Audio focus

Se respetará el sistema de gestión de audio de Android.

Cuando otra aplicación inicie audio, se pausará el TTS.

Ante una llamada, se pausará y no se reanudará automáticamente.

RF-TTS-010. Desconexión de auriculares

Si se desconectan auriculares por cable o Bluetooth durante el TTS:

- Se pausará la reproducción.
- Se evitará que continúe accidentalmente por el altavoz del dispositivo.

No se debe confundir esta regla con una desconexión de cualquier dispositivo que no esté participando en la reproducción.

9.8. Fragmentos multilingües

RF-TTS-011. Selección de voz por fragmento

- Se utilizará la voz principal para el idioma principal.
- Los fragmentos explícitamente etiquetados con otro idioma podrán usar una voz compatible.
- Si no existe una voz compatible, se utilizará la voz principal.
- No se analizará automáticamente el idioma de cada fragmento.

---

10. Copias de seguridad y restauración

10.1. Objetivo y contenido

RF-BCK-001. Respaldo general

Las copias generales incluirán:

- Datos de libros y biblioteca.
- Notas.
- Marcadores.
- Resaltados.
- Etiquetas.
- Colecciones.
- Preferencias y configuraciones.

Las preferencias se incluirán en las copias generales.

No se contempla sincronización en la nube.

La aplicación no solicitará contraseña para proteger las copias.

10.2. Organización de la sección

RF-BCK-002. Secciones

El módulo tendrá:

1. Copias automáticas.
2. Copias manuales.
3. Restauración.
4. Cadenas importadas.
5. Informe de actividad.

10.3. Copias automáticas

RF-BCK-003. Programación

- Frecuencia mensual.
- Ejecución el día 1 de cada mes.
- Almacenamiento privado de la aplicación.
- No dependerán de Wi-Fi ni de Internet.
- Se ejecutarán cuando Android permita realizar el trabajo.
- Se conservarán las últimas cuatro copias automáticas.
- Al superar el límite, se eliminará la más antigua sin romper dependencias.

Si el dispositivo está apagado, no tiene espacio suficiente o Android impide ejecutar la tarea:

- La copia quedará pendiente cuando sea posible.
- Se informará de las restricciones.
- No se deberá presentar una copia fallida como completada.

10.4. Copias manuales

RF-BCK-004. Creación y almacenamiento

Se podrán crear copias manuales desde una sección específica.

El usuario podrá configurar una carpeta externa mediante SAF.

Las copias manuales:

- Permanecerán hasta que el usuario las elimine.
- No estarán sujetas al límite de cuatro copias automáticas.
- Tendrán una cadena independiente de las automáticas.
- No se eliminarán al crear una copia automática.

Se podrá programar una ejecución manual para una fecha u ocasión determinada, según el comportamiento ya definido para esa función.

10.5. Incrementalidad

RF-BCK-005. Cadenas incrementales

Las copias serán incrementales:

- La primera copia de una cadena será completa.
- Las siguientes almacenarán cambios respecto de la cadena anterior.
- Cada incremental dependerá de su base y de los elementos necesarios para reconstruir el estado.

Las cadenas automáticas y manuales serán independientes.

Eliminar una copia base no deberá dejar incrementales inutilizables.

Si una eliminación afecta dependencias, se advertirá y se gestionará la cadena correspondiente.

10.6. Compresión e integridad

RF-BCK-006. Validación

Las copias podrán comprimirse.

Se comprobará la integridad:

- Después de crear una copia.
- Antes de restaurar.
- Al importar una cadena.

Una copia dañada no deberá utilizarse silenciosamente.

10.7. Progreso, interrupciones y espacio

RF-BCK-007. Operaciones seguras

Durante la creación, importación o restauración:

- Se mostrará el progreso.
- Se permitirá cancelar cuando sea seguro.
- Se manejarán interrupciones del sistema.
- Se manejará falta de espacio.
- Se evitará dejar archivos parciales como copias válidas.
- Se mantendrá la consistencia de la cadena.

Las operaciones deberán distinguir entre una cancelación segura y una operación que ya no pueda interrumpirse sin riesgo.

10.8. Restauración

RF-BCK-008. Selección y vista previa

El usuario podrá elegir una copia por fecha.

Antes de restaurar se mostrará una vista previa de su contenido.

Antes de modificar el estado actual será obligatorio crear una copia protectora de la información existente.

La restauración será combinada, no un reemplazo indiscriminado de todos los datos.

Se evitará duplicar registros cuando exista correspondencia.

10.9. Conflictos

RF-BCK-009. Resolución individual

Cuando existan conflictos entre los datos locales y los respaldados, el usuario podrá resolver individualmente los conflictos de:

- Notas.
- Marcadores.
- Resaltados.

La restauración deberá evitar la pérdida silenciosa de información.

No se considera confirmada la regla del documento de Gemini que duplica automáticamente notas recientes y les asigna la etiqueta «Restaurada».

La resolución debe respetar la naturaleza de cada tipo de dato y las decisiones de combinación ya tomadas.

10.10. Importación y exportación de cadenas

RF-BCK-010. Cadenas completas

Se permitirá exportar e importar cadenas completas.

La cadena importada deberá validarse.

Las cadenas importadas:

- Se registrarán como cadenas independientes.
- Podrán copiarse al almacenamiento privado.
- No modificarán los archivos originales externos.
- No se mezclarán silenciosamente con las cadenas existentes.

La reparación de una cadena dañada solo se realizará con autorización del usuario.

10.11. Migraciones

RF-BCK-011. Migración de datos

Las migraciones estructurales de la base de datos deberán proteger los datos actuales.

Se contempla una copia temporal previa a una migración.

Si la migración falla, se deberá recuperar el estado anterior cuando sea posible.

Las cadenas importadas podrán utilizarse para gestionar migraciones y recuperación de estados anteriores sin alterar automáticamente las cadenas existentes.

10.12. Historial

RF-BCK-012. Informe de actividad

Se mantendrá un historial de operaciones.

Deberá permitir consultar las actividades relacionadas con:

- Creación.
- Importación.
- Exportación.
- Restauración.
- Validación.
- Eliminación.
- Errores e interrupciones.

El historial tendrá filtros y mostrará resultados comprensibles para el usuario.

---

11. Modelos, diccionarios y almacenamiento local

11.1. Gestión de descargas

RF-SYS-001. Espacio disponible

Antes de descargar modelos o diccionarios:

- Se comprobará el espacio disponible.
- Se mostrará el tamaño requerido.
- Se advertirá si no hay espacio suficiente.

Si el almacenamiento se agota durante la descarga:

- Se abortará el proceso de forma segura.
- Se eliminará el archivo temporal incompleto cuando corresponda.
- Se conservarán los archivos instalados previamente.
- Se informará del problema.

11.2. Descargas interrumpidas

RF-SYS-002. Reanudación y limpieza

Se intentará reanudar una descarga interrumpida cuando el proveedor y el formato lo permitan.

Si el archivo parcial está dañado o no puede recuperarse:

- Se eliminará el parcial.
- Se solicitará una nueva descarga.
- No se permitirá utilizar el archivo incompleto.

11.3. Guardado del progreso

RF-SYS-003. Persistencia

El progreso y la posición se guardarán ante eventos relevantes del ciclo de vida de Android.

También se contempla guardado periódico durante sesiones de lectura prolongadas.

El sistema deberá minimizar la pérdida de información ante cierres inesperados o gestión de memoria del sistema.

No se debe depender exclusivamente de un único evento como onPause.

---

12. Actualizaciones de la aplicación

12.1. Política de actualización

RF-UPD-001. Actualizaciones periódicas

Se contempla una revisión mensual de actualizaciones, el día 1.

La aplicación:

- Notificará cuando exista una actualización.
- Esperará autorización del usuario.
- No actualizará silenciosamente.
- Mantendrá las últimas cuatro versiones disponibles según la política acordada.

Las actualizaciones no deberán eliminar datos personales ni restablecer las preferencias.

12.2. Seguridad de los datos

RF-UPD-002. Protección ante cambios

Antes de una migración estructural se generará un respaldo temporal.

Si la migración falla, se intentará recuperar el estado previo.

La actualización de la aplicación no debe confundirse con la actualización de modelos de traducción o diccionarios, que tienen sus propias reglas.

---

13. Arquitectura técnica

13.1. Tecnologías y decisiones

RF-TEC-001. Plataforma

Confirmado:

- Android nativo.
- Kotlin.
- Android 10 como versión mínima considerada.
- Almacenamiento local.
- Arquitectura preparada para ampliar formatos en el futuro.

Las tecnologías concretas del documento de Gemini deben considerarse propuestas técnicas, no decisiones funcionales confirmadas.

13.2. Tecnologías propuestas por Gemini

Área| Tecnología propuesta| Estado
UI| Jetpack Compose| Requiere validación
Motor EPUB| Readium SDK| Requiere validación
Persistencia| Room / SQLite| Requiere validación
Arquitectura| MVVM| Requiere validación
Flujos reactivos| StateFlow| Requiere validación
Inyección de dependencias| Hilt| Requiere validación
Asincronía| Kotlin Coroutines| Propuesta
Trabajos programados| WorkManager| Propuesta
Preferencias| DataStore| Propuesta
HTTP| Retrofit / OkHttp| Propuesta

La selección definitiva debe considerar la compatibilidad entre Android, Readium, SAF, TTS, almacenamiento y las funciones de EPUB.

13.3. Estructura propuesta

La organización por capas puede considerar:

- "data/": persistencia, entidades, DAO y repositorios.
- "domain/": modelos, interfaces y casos de uso.
- "ui/": pantallas, componentes y temas.
- "service/": EPUB, TTS, traducción y diccionarios.
- "worker/": descargas y copias de seguridad.
- "di/": configuración de dependencias.

La estructura definitiva y los nombres de paquetes deberán validarse durante el diseño técnico.

---

14. Relaciones entre módulos

14.1. Biblioteca y colecciones

Eliminar un libro retira sus asociaciones visibles, pero conserva información interna para una recuperación posterior.

Eliminar una colección no elimina sus libros.

14.2. Lector y notas

La selección de texto puede generar una nota.

Las notas pueden conservar fragmentos históricos incluso cuando el EPUB ya no está disponible.

Las notas pueden desvincularse y cambiar de asociación sin alterar su fragmento original.

14.3. Lector y marcadores

Los marcadores y resaltados dependen de la ubicación dentro del EPUB.

La reimportación puede permitir recuperar las ubicaciones, siempre que exista correspondencia confiable.

14.4. Lector y traducción

La selección de texto inicia una consulta de traducción.

El panel no debe modificar la posición de lectura.

El proveedor se determina según la modalidad seleccionada.

14.5. Lector y diccionarios

La selección de texto puede abrir el diccionario offline.

El panel conserva el contexto de lectura y no altera la posición.

14.6. Lector y TTS

El TTS utiliza el contenido y la estructura del EPUB.

La reproducción mantiene un indicador de posición independiente de los controles visuales normales del lector.

14.7. Notas y respaldos

Las notas se respaldan independientemente de la disponibilidad del EPUB.

La restauración debe conservar sus asociaciones e información histórica.

14.8. Marcadores, resaltados y respaldos

Los marcadores y resaltados deben conservar sus referencias y metadatos en los respaldos.

La recuperación de ubicaciones debe evitar asociaciones incorrectas.

---

15. Registro de decisiones y pendientes

15.1. Decisiones confirmadas relevantes

Área| Decisión
Plataforma| Android nativo con Kotlin
Alcance| EPUB 2/3, sin DRM ni contraseña
Almacenamiento| Local, sin nube
Importación| SAF y compartir, sin permisos globales
Notas| Independientes y persistentes
Notas| Contenido máximo de 500 caracteres
Notas| Título máximo de 50 caracteres
Fragmentos| Máximo de 5000 caracteres
Etiquetas| Máximo de 20 caracteres
Papelera de notas| 30 días
Marcadores| Dependientes del EPUB
Marcadores| Sin papelera, con deshacer temporal
Resaltados| Paleta predefinida y colores personalizados
Traducción| Cuatro modalidades seleccionables
Traducción| WordReference y modelos locales
Modelos| Descarga y actualización con autorización
Diccionarios| StarDict y DSL
Diccionarios| Varios por par, uno activo
TTS| Motor nativo Android
Copias automáticas| Mensuales, día 1
Copias automáticas| Conservar últimas 4
Copias manuales| Ubicación externa configurable
Respaldos| Cadenas incrementales independientes
Restauración| Combinada, con copia protectora
Actualización| Mensual, con autorización del usuario

15.2. Requisitos que deben retirarse o corregirse del documento de Gemini

Requisito del documento recibido| Corrección
Buscador de texto completo dentro del EPUB| No confirmado; contradice el alcance definido
"hasFragileUserData="true""| No confirmado
Reconexión automática de colecciones| No asumir; requiere revisión
Finalización automática al 100 %| Requiere revisión
Botón manual de terminado| Requiere revisión
Recorte automático de resaltados superpuestos| No confirmado
WebView para WordReference| No confirmado
Límite de 1 a 3 palabras para WordReference| No confirmado
Segmentación jerárquica sin contexto| No confirmada
Duplicación automática con etiqueta «Restaurada»| No coincide con la resolución individual acordada
Compose, Readium, Room, Hilt como pila aprobada| Propuesta técnica, no decisión confirmada

15.3. Requisitos pendientes de definición

1. Comportamiento definitivo de finalización de lectura.
2. Alcance del restablecimiento general de ajustes.
3. Comportamiento de datos ante desinstalación y reinstalación.
4. Reglas finales de recuperación de asociaciones de colecciones.
5. Comportamiento de resaltados cuando las selecciones se superponen.
6. Límites técnicos por proveedor para traducciones extensas.
7. Estrategia de coherencia entre segmentos de traducción.
8. Tratamiento de consultas mixtas: palabra, expresión, oración y párrafo.
9. Casos donde falten metadatos de autor o título en las citas.
10. Detalles finales de conflictos entre versiones de datos durante restauración.
11. Validación completa de la política de actualizaciones de la aplicación.
12. Validación de accesibilidad, errores y permisos en diferentes versiones Android.
13. Compatibilidad definitiva de la arquitectura técnica.
14. Revisión de la importación Markdown para casos complejos de formato.
15. Comportamientos restantes de recuperación ante EPUB sustituidos o modificados.

---

16. Criterios de calidad de la especificación

La especificación deberá cumplir las siguientes condiciones:

1. Cada requisito debe describir un comportamiento observable.
2. Los límites numéricos deben conservarse literalmente.
3. Las excepciones deben estar documentadas.
4. Las reglas de persistencia deben ser explícitas.
5. Los módulos deben tener responsabilidades diferenciadas.
6. Las relaciones entre módulos deben estar documentadas.
7. Los estados de confirmación deben ser verificables.
8. Las propuestas técnicas no deben confundirse con requisitos funcionales.
9. Las decisiones nuevas deben registrarse antes de incorporarse como confirmadas.
10. No se debe declarar una cobertura del 100 % mientras existan requisitos pendientes o decisiones sin recuperar.

Fin del documento consolidado.