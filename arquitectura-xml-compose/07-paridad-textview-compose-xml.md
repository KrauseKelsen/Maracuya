# Paridad del TextView Compose y XML

Actualizado: 3 de noviembre de 2026. Compose es la referencia de presentación del DS.

## Contrato compartido

| Aspecto | Default de ambos frontends |
|---|---|
| Tipografía y fuente | `library.typography.label` y familia del tema activo. |
| Color | `fgDefault`; HIPERTEXT utiliza `linkDefault` y subrayado. |
| Estados | Deshabilitado prevalece sobre error, y error sobre normal. Los iconos siguen el color del texto. |
| Superficies | Fondo y foreground transparentes. |
| Iconos | Ausentes hasta configurarlos; error no agrega un icono implícito. |
| Separación | 8dp entre el texto y cada icono presente. |
| Icono decorativo | Ocupa `IconToken.iconSize`. |
| Icono con acciones | Celda mínima de 48 × 48dp, con el asset centrado y sin escalarlo. |
| Disabled | Conserva las celdas y desactiva acciones. |
| Padding exterior | No se añade padding al componente; el host puede configurarlo. |
| Fuente | `includeFontPadding=false`. |
| Líneas | Mínimo 1, máximo sin límite práctico salvo configuración explícita. |

`core/components/textviews/tokens/TextViewLayoutTokens` contiene las medidas comunes. `tokens/spacings/InteractionSpacings` aporta el tamaño interactivo global, reutilizable por otros componentes. No se mantienen archivos `TextViewDefaults` distintos en cada frontend.

Compose conserva su fila, iconos opcionales y texto con `weight(fill=false)` cuando hay iconos. XML conserva `AppCompatTextView`, con sus APIs de texto y selección. Las capacidades nativas adaptan la disposición:

- `AndroidIconLoader` utiliza `FittedIconDrawable` para conservar la proporción del asset, como `ContentScale.Fit` en Compose. El decorador expone el tamaño intrínseco cuadrado del token y recalcula el centrado cuando Android o el editor XML cambian sus límites; no utiliza márgenes fijos que puedan deformar iconos rectangulares.
- `CompoundDrawableRenderer` centra el asset dentro de la celda solicitada y aplica tintes.
- `InlineCompoundDrawableLayout` mide el ancho intrínseco del texto y reserva el sobrante al final del contenido; no cambia `android:padding` del host.
- `OffsetCompoundDrawable` coloca el icono final junto al texto, incluso con `match_parent`.
- `CompoundDrawableGeometry` utiliza ese mismo desplazamiento para las acciones en LTR y RTL.
- `NativeTextTouchTarget` restringe el inicio del gesto al bloque de texto con el mínimo táctil, después de atender las acciones de iconos.

Estas capacidades no conocen el tema, registro, variantes ni clases concretas del DS. Un componente nuevo las incorpora cuando necesita ese tipo de disposición.

## Uso XML

```xml
<cruxui.android.lib.wrappers.components.textviews.TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/information"
    android:theme="@style/ThemeOverlay.Maracuya"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/information"
    app:iconEnd="?attr/libraryIconAlertsInfo"
    app:iconEndContentDescription="@string/show_information" />
```

El host declara y traduce `information` y `show_information`. Sin callback, el icono es decorativo. Para hacerlo interactivo:

```kotlin
val view = findViewById<cruxui.android.lib.wrappers.components.textviews.TextView>(R.id.information)
view.setOnIconEndClickListener { showInformation() }
```

El callback reserva la celda mínima. La descripción localizada debe existir antes de medir un icono interactivo, igual que en Compose. Quitar el último callback restaura el tamaño decorativo; retirar el icono elimina también su separación. Deshabilitar conserva la disposición.

`app:iconPadding`, `android:drawablePadding` y `setCompoundDrawablePadding(px)` permiten una separación explícita. `android:padding`, `includeFontPadding`, `minLines`, `maxLines`, `ellipsize` y los setters Android mantienen sus opciones. Esas personalizaciones pueden producir una apariencia distinta del default y deben aplicarse de manera equivalente al comparar frontends.

Los gestos de texto e iconos son independientes. Selección reserva pulsación larga y doble toque del texto; sus callbacks se conservan al desactivar la selección. Los iconos siguen disponibles. El espacio libre fuera de las zonas táctiles no activa el texto. Los menús de selección y la representación de acciones accesibles pertenecen a cada plataforma y no se fuerzan a ser visualmente idénticos.

Para RTL, el host habilita `android:supportsRtl="true"` en su `<application>`. Android necesita esa declaración para aplicar `layoutDirection` a las Views. El manifiesto del APK de pruebas también la declara; `LocalLayoutDirection` de Compose por sí solo no habilita RTL en una aplicación Android.

## Galerías equivalentes

`ui/previews/TextViewPreview.kt` y `res/layout/text_view_preview.xml` muestran estos nueve casos, en el mismo orden:

1. Texto básico.
2. Caption con `fgMuted` y `bgSubtle`.
3. Estado de error sin icono.
4. Estado deshabilitado.
5. Iconos inicial y final con click y pulsación larga independientes.
6. Label con icono final interactivo.
7. Click, doble click y pulsación larga del texto.
8. Texto seleccionable y copiable.
9. Hipervínculo.

Ambas galerías comparten los recursos `text_view_preview_*`, padding exterior de 16dp y separación vertical de 12dp. Estos márgenes son de la galería, no tokens del componente. `wrappers/previews/TextViewGalleryPreview` conecta callbacks vacíos para reproducir las celdas interactivas; no interviene en pantallas del host.

Los antiguos colores y selectores demo se retiraron de producción. Las pruebas de precedencia con estilos artificiales usan fixtures exclusivos de `src/debug/res`.

## Verificación reproducible

```sh
./gradlew :token-exporter:test :maracuya:testDebugUnitTest :maracuya:lintDebug :app:assembleDebug :maracuya:assembleDebugAndroidTest
./gradlew :maracuya:connectedDebugAndroidTest
```

`TextViewParityTest` compara ancho, alto, línea base y posición del icono final entre renderizadores reales, incluyendo estados, iconos decorativos/interactivos, multilínea, caption, ancho fijo y RTL. La tolerancia es de dos píxeles por redondeos de plataforma.

`TextViewGalleryParityTest` infla el layout real, compara las posiciones de los nueve textos, las dimensiones de la galería y los contornos pintados de los iconos, y guarda `textview-compose.png` / `textview-xml.png` en el directorio privado del APK de pruebas. `TextViewInteractionTest` comprueba las acciones Compose; las pruebas nativas cubren listeners, selección, estados, accesibilidad y conservación del padding.

Cuando el plugin UTP de Gradle no está disponible en la caché offline, el mismo APK puede ejecutarse directamente:

```sh
adb install -r maracuya/build/outputs/apk/androidTest/debug/maracuya-debug-androidTest.apk
adb shell am instrument -w -r cruxui.android.lib.test/androidx.test.runner.AndroidJUnitRunner
```

Compilar el APK no demuestra que las pruebas instrumentadas hayan pasado: comprobar el resultado de AndroidJUnitRunner.

## Resultado de la verificación

Validado el 3 de noviembre de 2026 en la rama `TokenBridgeXML`:

- Compilación correcta de la app debug, el AAR release y el APK de pruebas.
- 75 pruebas unitarias aprobadas: 61 del DS y 14 del exportador. Las pruebas nativas con Robolectric cubren API 28.
- 8 pruebas instrumentadas aprobadas mediante AndroidJUnitRunner en un emulador API 36. La matriz de paridad recorre 13 configuraciones; la galería compara los nueve ejemplos.
- Lint sin errores. Permanecen 22 advertencias y una sugerencia sobre recursos, otros previews y el fondo intencional de la galería.
- App instalada y actividad principal iniciada correctamente; ambas capturas revisadas visualmente.
- Generación de 51 colores, 28 tipografías y 375 assets de iconos desde Library.

Los resultados locales están en `maracuya/build/reports/textview-parity`: `build.log`, `instrumentation.log`, `app-launch.log`, `compose.png` y `xml.png`. Son artefactos de verificación regenerables, excluidos de Git. El emulador temporal se elimina al finalizar; no forma parte del proyecto.

La cobertura confirma estas configuraciones y esta versión de Android; no representa una validación visual de todas las densidades, idiomas o tamaños de fuente posibles. Las opciones nativas explícitas del host siguen pudiendo modificar la presentación.
