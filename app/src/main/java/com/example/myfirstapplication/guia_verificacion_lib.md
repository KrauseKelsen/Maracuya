# Verificar el consumo actual de Maracuya

Actualizado: 2 de noviembre de 2026.

La aplicación de este repositorio consume `implementation(project(":maracuya"))`. No necesita descargar ni refrescar una publicación Maven para probar cambios locales. `:token-exporter` genera las colecciones y recursos necesarios antes de compilar la biblioteca.

## Compilación y pruebas

Desde la raíz del repositorio:

```sh
./gradlew :token-exporter:test :maracuya:testDebugUnitTest :maracuya:lintDebug :app:assembleDebug :maracuya:assembleDebugAndroidTest
```

Con un dispositivo o emulador disponible:

```sh
./gradlew :maracuya:connectedDebugAndroidTest
./gradlew :app:installDebug
```

La aplicación y las pruebas instrumentadas son APK distintos. La compilación no demuestra que el runner haya ejecutado las pruebas. La alternativa con `adb` y las capturas comparativas se documenta en [paridad TextView](../../../../../../../arquitectura-xml-compose/07-paridad-textview-compose-xml.md).

## Estructura que debe consumirse

- Compose: `cruxui.android.lib.ui.components.textviews.TextView`, dentro de `MyLibraryTheme`.
- XML: `cruxui.android.lib.wrappers.components.textviews.TextView`, nativo, dentro del ámbito de `MyLibraryThemeWrp` cuando se configure un tema específico.
- Tokens y recetas compartidas: `core.components.textviews.tokens.TextViewTokensOverride`.
- Recursos XML generados: `maracuya/build/generated/libraryTokens/`; no editarlos manualmente.
- Catálogo común: `LibraryThemeSnapshot`, con colores, tipografías, fuentes e iconos derivados de sus Library.
- Galerías equivalentes: `TextViewGalleryPreview` y `res/layout/text_view_preview.xml`.

Para personalizar desde XML utilizar atributos Android, referencias `?attr/library...` bajo el overlay correspondiente o una receta seleccionada con `app:textViewTokensOverride`. Los detalles de precedencia están en [el manual](../../../../../../../arquitectura-xml-compose/03-textview-implementado.md).

## Verificar un AAR publicado desde otra aplicación

En un consumidor externo, consultar la dependencia realmente declarada, sin asumir una versión histórica:

```sh
./gradlew :app:dependencyInsight --dependency maracuya --configuration debugRuntimeClasspath
```

Al inspeccionar el AAR, comprobar `classes.jar`, `res/` y `R.txt`. Las clases wrapper permiten inflación XML; las funciones `@Composable` no son etiquetas XML. Los aliases `CorporateTypography` y `CorporateIcons` ofrecen compatibilidad de fuente Kotlin al recompilar, no clases JVM equivalentes para binarios antiguos.

Si el catálogo se modifica, reconstruir/publicar la biblioteca según el flujo del proyecto y actualizar la versión en el consumidor. No borrar cachés globales como paso rutinario de validación.
