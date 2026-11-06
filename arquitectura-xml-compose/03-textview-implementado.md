# TokenBridgeXML: tema compartido y TextView nativo

La fuente de verdad sigue siendo `theme/LibraryThemes.kt`, con los tokens atómicos y semánticos existentes. `LibraryThemeSet.resolve(...)` selecciona el modo y entrega un `LibraryThemeSnapshot` con `colors`, `typography`, `fontFamily` e `icons`. El snapshot es un contrato de datos: no vuelve a construir un tema ni mantiene un catálogo paralelo.

Compose conserva sus cuatro `CompositionLocal`. La fachada `LibraryTheme.current` los agrupa y respeta también las sustituciones de un local dentro de un scope anidado. XML obtiene el mismo contrato mediante `AndroidThemeResolver`, que consulta el `MyLibraryThemeWrp` más cercano. No se abre una composición para resolver un TextView nativo.

## Una receta para Compose y XML

La tipografía forma parte de `TextViewTokensOverride`. No hay un atributo `mrcyTypographyToken`, una lista de nombres tipográficos propia del componente ni un segundo registro de tipografías.

Definir la receta y registrarla antes de inflar la pantalla, por ejemplo desde `Application.onCreate`:

```kotlin
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.wrappers.components.textviews.tokens.TextViewTokensOverrideRegistry

fun sectionTitle(library: LibraryThemeSnapshot) = TextViewTokensOverride(
    typography = library.typography.headline24,
    textColor = library.colors.brandPrimary,
    fontFamily = library.fontFamily,
    iconEnd = library.icons.alerts.info,
)

// Dentro de Application.onCreate(), antes de inflar layouts:
TextViewTokensOverrideRegistry.register("sectionTitle", ::sectionTitle)
```

La misma función sirve en Compose:

```kotlin
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.textviews.TextView

MyLibraryTheme {
    TextView(
        text = "Información personal",
        tokensOverride = sectionTitle(LibraryTheme.current),
    )
}
```

Y en XML se selecciona la receta usando el registro existente como patrón:

```xml
<cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:themeName="Maracuya"
    app:themeStyle="auto">

    <cruxui.android.lib.wrappers.components.textviews.TextView
        android:id="@+id/title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/section_title"
        app:textViewTokensOverride="sectionTitle"
        app:iconEndContentDescription="@string/more_information" />
</cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp>
```

`section_title` y `more_information` son recursos de la app del ejemplo. El registro almacena funciones puras que reciben la biblioteca activa; no almacena una marca activa global, un `Context` ni una View. Evitar capturar Activities en esas funciones. Cada instancia evalúa su receta contra su scope al crearse, adjuntarse y actualizar su tema. Los hosts anidados conservan su propio alcance.

Los nombres se normalizan sin depender del idioma del dispositivo. Un nombre desconocido falla con un mensaje explícito; el editor de layouts permite previsualizar con defaults cuando el registro de la app no se ha ejecutado. Reemplazar una receta no observa automáticamente las Views: llamar a `refreshTheme()` en las instancias existentes. Se mantiene el overload de registro anterior con cuatro argumentos para los otros wrappers.

## Contrato y organización

```text
core/components/textviews/
  tokens/         TextViewTokens, TextViewTokensOverride, TextViewTokenDefaults, TextViewLayoutTokens
  tokensrender/   política visual compartida: disabled > error > normal
  variant/        DEFAULT, HIPERTEXT
  behavior/       Default, Selectable

theme/
  LibraryThemes.kt          catálogo y resolución del tema
  LibraryThemeSnapshot.kt   contrato completo compartido
  LibraryTheme.kt           acceso agrupado a los locals Compose existentes
  FontAssetCatalog.kt       archivos físicos por familia, peso y cursiva

wrappers/components/textviews/
  TextView.kt       fachada AppCompatTextView, setters y ciclo de vida
  attributes/       XML, styles y textAppearance → entradas nativas
  core/             estado exclusivo de la instancia
  tokens/           aliases compartidos, registro y parches nativos
  tokensrender/     adaptación a Typeface, dimensiones, colores y drawables
  variant/          aplicación nativa del subrayado
  behavior/         capacidades y callbacks de texto e iconos
  render/           aplicación del modelo final a la View

wrappers/core/
  registry/         TokensOverrideRegistry<T>, reutilizable por componente
  theme/            búsqueda del proveedor XML y resolución del tema
  attributes/       lectura común de colores, dimensiones y superficies
  tokens/           fuentes de valores Android, recursos y selectores semánticos
  resources/        adaptación de tokens a recursos Android
  render/           superficies Android
  behavior/         gestos de View y fachada histórica de compatibilidad

wrappers/capabilities/
  text/             lectura, resolución y render de texto
  compounddrawables/ dibujo, celdas, geometría, gestos y accesibilidad de iconos

wrappers/adapters/compose/
                    adaptación de recetas y medidas para wrappers Compose
```

Las rutas públicas del nuevo TextView Compose siguen disponibles mediante aliases. Ambos frontends usan las mismas clases de tokens, variantes y capacidades de comportamiento. XML no importa `LabelTokens` ni deriva su contenido de `LabelVariant.Optional`.

El contrato visual conserva `textColor`, `backgroundColor`, `errorColor`, `foregroundColor`, `disabledColor`, `iconStart`, `iconEnd`, `typography` y `fontFamily`. La tipografía se convierte a tamaño, altura de línea, tracking, peso, cursiva y alineación. `HIPERTEXT` usa el color de enlace y subrayado; no cambia el texto ni abre enlaces automáticamente. Los iconos son opcionales y siguen el color efectivo del texto, igual que el componente Compose.

Para incorporar otro componente: declarar sus modelos puros en `core/components`, derivar sus defaults desde `LibraryThemeSnapshot`, extender `TokensOverrideRegistry<SuTokensOverride>` y crear su lector, adaptación y renderer nativos. Reutilizar `wrappers/core`; no copiar el catálogo del tema ni el registro.

## Recursos XML derivados del catálogo

El módulo JVM `:token-exporter` compila las mismas fuentes puras del catálogo. La tarea `:maracuya:generateLibraryTokens` ejecuta ese catálogo antes de compilar recursos y genera:

| Resultado | Ejemplo |
|---|---|
| Colores claros y oscuros | `@color/library_maracuya_light_brand_primary` |
| Referencias semánticas de color | `?attr/libraryColorBrandPrimary` |
| TextAppearances completos | `@style/TextAppearance.Maracuya.Headline24` |
| Referencias tipográficas | `?attr/libraryTypographyHeadline24` |
| Dimensiones | `@dimen/library_maracuya_headline24_size` |
| Alturas de línea semánticas | `?attr/libraryLineHeightHeadline24` |
| Estilos para TextView estándar | `@style/Widget.Maracuya.Typography.Headline24` |
| Familias con pesos y cursivas | `@font/library_mulish`, `?attr/libraryFontPrimary` |
| Iconos y tamaños | `?attr/libraryIconAlertsInfo`, `?attr/libraryIconAlertsInfoSize` |
| Overlays de marca/modo | `ThemeOverlay.Maracuya`, `.Light`, `.Dark` |

`ThemeOverlay.Maracuya` utiliza `values-night` para AUTO. Las referencias Kotlin se agrupan en `ThemeExports.xmlReferences` / `LibraryXmlReferences`; por ejemplo `LibraryXmlReferences.typography.headline24` es un **ID de atributo**, no el valor resuelto ni un ID de estilo. Se resuelve contra el tema del `Context` con `obtainStyledAttributes`.

Un TextView estándar también puede usar los recursos exportados:

```xml
<androidx.appcompat.widget.AppCompatTextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    style="@style/Widget.Maracuya.Typography.Headline24"
    android:theme="@style/ThemeOverlay.Maracuya"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/section_title"
    android:textColor="?attr/libraryColorBrandPrimary" />
```

En el TextView del DS se puede usar `android:textAppearance="?attr/libraryTypographyHeadline24"` con el mismo overlay. La receta registrada es la opción para seguir cambios de tema dinámicos del proveedor. Los recursos `?attr` pertenecen al tema Android del `Context`: cambiar `MyLibraryThemeWrp.themeStyle` actualiza recetas y tokens semánticos, pero no reinfla estilos Android ni cambia el `Context` de Views existentes. Para cambiar referencias XML explícitas de marca/modo, aplicar el overlay correspondiente y recrear o reinflar la pantalla. Mantener `themeName`/`themeStyle` y el overlay coherentes cuando se combinan ambos mecanismos.

La salida vive en `maracuya/build/generated/libraryTokens/` y no se edita ni versiona. No hay reflexión ni búsqueda de recursos por nombre durante el render; el generador produce enlaces estáticos a `R`. La reflexión se limita al proceso de construcción y a pruebas de paridad del catálogo. Ver [el módulo exportador](../token-exporter/README.md) para agregar temas o assets.

## Setters, precedencia y comportamiento

```kotlin
import cruxui.android.lib.wrappers.components.textviews.TextView as DesignTextView
import cruxui.android.lib.wrappers.components.textviews.behavior.TextViewBehavior
import cruxui.android.lib.wrappers.components.textviews.variant.TextViewVariant

val title = findViewById<DesignTextView>(R.id.title)
title.text = "Datos de contacto"       // setText nativo: conserva spans
title.setVariant(TextViewVariant.HIPERTEXT)
title.setColor(title.getThemeTokens().colors.brandPrimary)
title.setTypography(title.getThemeTokens().typography.headline24)
title.setTextSizeSp(26f)
title.setOnClickListener { /* acción del texto */ }
title.setOnDoubleClickListener { /* doble pulsación del texto */ }
title.setOnLongClickListener { /* pulsación larga */ true }
title.setOnIconEndClickListener { /* acción independiente del icono */ }
title.setOnIconEndLongClickListener { /* ayuda */ true }
title.setBehavior(TextViewBehavior.Selectable)
```

Se mantienen las APIs nativas de texto, spans, `maxLines`, `ellipsize`, tamaño, fuente, `ColorStateList`, espaciado, fondos, alineación y selección. Los setters de estilo gestionados se conservan al refrescar el tema o cambiar de variante. `setError` y `setErrorState` comparten estado; deshabilitar bloquea acciones del texto e iconos. La selección nativa reserva long press y doble pulsación del texto; al desactivarla se recuperan los callbacks configurados. Las acciones de iconos se exponen también a accesibilidad. Cada icono interactivo reserva una celda mínima de 48dp y conserva el asset centrado; cada icono presente se separa 8dp del texto. Ambos valores proceden de `TextViewLayoutTokens`, compartido con Compose. Un icono decorativo ocupa solamente su tamaño.

Prioridad: **parche de ejecución > XML resuelto por Android > receta registrada > defaults de la variante en el tema**. Dentro de una capa, valores nativos específicos como tamaño pueden ajustar una tipografía completa. Aplicar después una tipografía sustituye sus métricas anteriores; aplicar después `setTextSize` ajusta solo el tamaño. La familia es un campo independiente. `lineHeight` y `lineSpacing` son alternativas; el último setter elige una.

`setTokensOverride` usa el mismo contrato semántico que Compose. Su `textColor` cambia el estado normal; `errorColor` y `disabledColor` mantienen la política compartida. `setTextColor` nativo aplica su color o selector a todos los estados. `setNativeOverrides(TextViewNativeOverrides(...))` acepta representaciones Android como recursos, dimensiones, selectores, drawables o `Typeface` cuando sean necesarias. `setColor(Int)` espera `@ColorRes`; para un ARGB usar `setTextColor(Int)`.

Los parches combinan campos presentes; `null` significa ausencia. Para retirar un icono usar `setIconStart(null as IconToken?)`, `setIconEnd(null as IconToken?)` o `ViewIcon.None` en el parche nativo. `clearOverrides`, `clearColorOverride` y `clearTypographyOverrides` restauran los valores inferiores de XML/receta. `setTokensOverrideName(null)` retira la receta. `setThemeTokens(snapshot)` permite inyección explícita por instancia; `null` restaura la herencia del proveedor.

## Migración y validación

- El wrapper anterior basado en Label se sustituye por el nuevo contrato TextView. `optional`, `setTokens`, `setComponentTokensOverride`, `clearTextOverride` y los atributos particulares `typography`/`textColorRole` de aquella propuesta se retiran. El componente Label conserva su implementación independiente.
- La configuración semántica va a `TextViewTokensOverride`; recursos/valores exclusivamente Android van a `TextViewNativeOverrides`. Las variantes Kotlin son `DEFAULT` y `HIPERTEXT`; en XML, `standard` y `hipertext`.
- Los iconos incluidos usan `resourceName` para mantener puro el catálogo. Para obtener un drawable ID en código Android usar `LibraryAssetResources.icon(token)`; no leer `filledResId` de un icono incluido, pues ahora es cero. Se conserva `IconToken(name, filledResId = R.drawable.mi_icono)` para recursos de la app.
- Los adaptadores Compose y Views consumen el mismo catálogo físico completo de fuentes, incluidos peso 900 y cursivas.

Las pruebas cubren catálogo exportado claro/oscuro, 28 tipografías, enlaces de iconos y fuentes, recetas, scopes, inflación, precedencia, fuentes, alineación con/sin RTL, estados, selección, gestos y accesibilidad. Ejecutar:

```sh
./gradlew :maracuya:testDebugUnitTest :maracuya:lintDebug :app:assembleDebug :maracuya:assembleDebugAndroidTest
```

Las pruebas instrumentadas de interacción Compose requieren un dispositivo/emulador con `:maracuya:connectedDebugAndroidTest`. Compilar su APK no equivale a ejecutarlas. La revisión visual en dispositivo es adicional a las verificaciones automatizadas. Los callbacks y parches de ejecución se reaplican desde el propietario de la pantalla al recrearla; no se serializan snapshots ni listeners.


La galería y el contrato de medidas se detallan en [paridad Compose–XML](07-paridad-textview-compose-xml.md).
