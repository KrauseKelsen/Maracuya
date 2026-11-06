# Exportación de los catálogos Library

`token-exporter` es una herramienta JVM de construcción. Compila las definiciones puras de Library y genera las APIs y los recursos compartidos por Android. No se incluye como dependencia de ejecución del AAR. El host que consume el AAR recibe clases y recursos ya compilados.

## Una fuente por familia

| Familia | Fuente editable | Proyección generada |
| --- | --- | --- |
| Tipografía | `atoms/LibraryTypographyTokens` | `LibraryTypography`, roles, dimensiones y TextAppearance |
| Colores | `atoms/LibraryColorTokens` y sus paletas | Consultas sobre la misma colección, roles, colores y atributos |
| Iconos | `atoms/LibraryIconTokens` y `atoms/icons/Library*` | `LibraryIcons` con referencias a los grupos originales, roles y atributos de asset/tamaño/tinte |
| Fuentes físicas | `theme/FontAssetCatalog` | Familias XML y enlaces estáticos a recursos |

`theme.collections` contiene las colecciones generadas `LibraryTypography` y `LibraryIcons`. Los colores usan directamente `LibraryColorTokens`, que ya admite personalización con `copy`. `theme.catalog` contiene las consultas y los roles de las tres familias.

`semantics` ya no tiene implementaciones manuales. `CorporateTypography` y `CorporateIcons` se generan como aliases Kotlin de compatibilidad. Los antiguos tipos `tokens.icons.*` también son aliases de las categorías Library; sus funciones `get*Icons` devuelven esas mismas instancias. No hay una segunda lista de iconos ni otra fuente de valores.

## Flujo automático de Gradle

1. `catalogSchema` compila los catálogos Library y sus tipos puros, sin temas ni APIs generadas.
2. `generateCatalogApi` descubre los getters y genera colecciones, consultas, roles y aliases en `token-exporter/build/generated/catalogApi`.
3. Se compila el exportador completo con esas fuentes. `ThemeCatalogReader` consulta los temas efectivos mediante sus `asMap`; no utiliza reflexión.
4. `:maracuya:generateLibraryTokens` produce recursos y código bajo `maracuya/build/generated/libraryTokens`. `CatalogApiWriter` transporta las mismas fuentes a Android mediante un manifiesto generado.
5. Android compila esas fuentes y recursos junto al DS. La app consume el AAR normalmente.

Las dos tareas conocen sus salidas y su classpath. Modificar un valor o una propiedad de Library invalida la generación; no es necesario ejecutar una tarea extra. Ambas limpian exclusivamente sus directorios generados cuando vuelven a ejecutarse para retirar salidas obsoletas. Una compilación limpia también reconstruye todo.

La reflexión se utiliza solo en `catalogSchema`. Los componentes reciben consultas Kotlin directas y bindings estáticos a `R`, sin reflexión, `getIdentifier` ni exportación durante el render.

## Agregar o modificar tokens

- **Tipografía:** declarar un `val TypographyToken` en `LibraryTypographyTokens`.
- **Color:** declarar un `val ColorToken` en `LibraryColorTokens` y definir sus valores en las paletas claras/oscuras construidas explícitamente.
- **Icono:** declarar un `val IconToken` en su categoría Library y asignar su valor en el catálogo base. `resourceName` apunta a un archivo de `res/drawable` sin extensión.
- **Categoría de iconos:** declarar la propiedad en `LibraryIconTokens`, anotar su getter con `@get:LibraryIconGroup("nombrePublico")` y configurarla en `Default`. No agregar una lista al exportador. `legacyType` solo se utiliza para los aliases anteriores.
- **Marca:** declarar un `LibraryThemeSet` y añadirlo a `LibraryThemes.all`. Sus valores efectivos generan sus overlays; cambiar el catálogo base no sobrescribe una personalización explícita de marca.

Después, compilar el módulo o la app. No modificar enums, resolutores, referencias XML ni archivos de `build/generated`. Las listas `legacyOrder` conservan posiciones históricas; **no son listas de tokens admitidos y no deben ampliarse**.

Los nombres exportados son API pública. Renombrar o eliminar una propiedad elimina su recurso en la siguiente generación. Para conservar una lectura anterior puede añadirse una propiedad calculada que devuelva el token actual. Así se conservan `people.accesibleMenu` y `extension.lgDavibankHouseEmpty`, junto a los nombres Library corregidos.

## Recursos y consumo

El build genera `libraryColorNombre`, `libraryTypographyNombre`, `libraryIconGrupoNombre`, `libraryIconGrupoNombreSize` y `libraryIconGrupoNombreTint`. El tinte null se representa como `@null`; no inventa un color. Asset, tamaño y tinte son referencias independientes para que cada componente aplique su política.

`LibraryXmlReferences` agrupa las referencias en `colors`, `typography`, `lineHeight`, `fonts`, `icons`, `iconSizes` e `iconTints`. Cada hoja es un **ID de atributo**, no un color, dimensión o drawable ya resuelto. `ThemeExports.xmlReferences` expone el mismo objeto.

Un `ThemeOverlay` conecta los atributos con los valores de marca y modo. Los snapshots creados mediante `copy` durante la ejecución siguen funcionando con Compose y las recetas/roles de Views, pero no crean IDs ni reescriben los recursos XML compilados. Un icono exclusivo del host con `filledResId` también pertenece a la ejecución; el catálogo exportable necesita un `resourceName` empaquetado en el DS.

## Responsabilidades y extensión

- `src/catalogSchema`: descubrimiento, escritores por familia, compatibilidad y composición de las APIs.
- `catalog`: modelo de exportación y adaptación de los temas efectivos.
- `validation`: compatibilidad de roles, nombres y presencia de assets.
- `android`: escritores independientes de atributos, colores, TextAppearance, fuentes y overlays.
- `kotlin`: transporte de APIs, bindings estáticos y referencias de atributos.
- `components/text`: adaptación opcional para estilos de widgets con texto.
- `output`: escritura y nombres de recursos; permite probar salidas en memoria.

`ExportLibraryTokens.kt` solo compone lectores, validación y escritores. Agregar un Button u otro componente que use estas familias no requiere editar el exportador. Su comportamiento y registro pertenecen al componente. Solo una nueva clase de recursos estáticos derivados necesita un escritor adicional.

## Compatibilidad y validación

Los aliases conservan imports de fuentes Kotlin, no la identidad de clases JVM de una versión publicada. Los hosts deben recompilarse; código Java o binarios que dependan de las clases anteriores necesitan migración. Para argumentos nombrados de constructores/copy de los dos iconos renombrados usar `accessibleMenu` y `davibankHouseEmpty`; los accesos de lectura y recursos anteriores se mantienen.

Las pruebas recorren los catálogos completos, comprueban personalizaciones, identidad de categorías, paridad de recursos y resolutores, exportación sin widgets y ausencia de assets. La [guía de colores e iconos](../arquitectura-xml-compose/06-colores-iconos-fuente-unica.md) contiene ejemplos de Compose, registros y XML.
