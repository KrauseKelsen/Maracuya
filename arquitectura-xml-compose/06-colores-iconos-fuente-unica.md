# Catálogo compartido de colores, tipografías e iconos

Los valores se mantienen en Library. El build genera las proyecciones necesarias para los temas, Kotlin y XML. Un nuevo componente consume estas proyecciones; no necesita añadir sus tokens al exportador ni al core de wrappers.

## Qué responsabilidad conserva Corporate

Ninguna responsabilidad de ejecución. `CorporateIcons` y `CorporateTypography` son aliases generados que permiten recompilar imports Kotlin existentes. El DS utiliza `theme.collections.LibraryIcons` y `LibraryTypography` directamente.

La colección tipográfica permite crear instancias y usar `copy` sobre los defaults del objeto `LibraryTypographyTokens`. La colección de iconos solo agrupa referencias a las categorías originales; ya no copia cientos de propiedades a GeneralIcons, AlertsIcons, etc. Los colores usan directamente `LibraryColorTokens`, que ya es una colección configurable.

`semantics` no contiene clases manuales ni valores. Los aliases podrían retirarse en una migración que actualice los imports de los consumidores. Los antiguos tipos `tokens.icons` también son aliases de sus categorías Library. Esto conserva compatibilidad de fuentes Kotlin habitual, **no compatibilidad binaria/Java** con una versión anterior publicada: hay que recompilar los hosts.

Se conservan los accesos y recursos históricos `people.accesibleMenu` y `extension.lgDavibankHouseEmpty` como propiedades calculadas sobre `accessibleMenu` y `davibankHouseEmpty`. Los argumentos nombrados de constructor/copy usan ahora estos últimos nombres Library; no existe un segundo campo con otro valor.

## Estructura y dirección de dependencias

```text
atoms/LibraryTypographyTokens ──► LibraryTypography generada
atoms/LibraryColorTokens      ──► misma colección + consultas generadas
atoms/LibraryIconTokens       ──► LibraryIcons generada (referencias a los grupos)
               │
               ▼
      LibraryThemeSet / LibraryThemeSnapshot
               ├──► MyLibraryTheme / CompositionLocals / Compose
               ├──► proveedor XML / register / TokensOverride / Views
               └──► token-exporter / recursos XML / LibraryXmlReferences
```

`catalogSchema` descubre propiedades en las clases compiladas. Escritores separados producen tipografía, consultas de color, colecciones de iconos, roles y compatibilidad. `ThemeCatalogReader` lee después los valores efectivos del tema mediante `asMap`. Los escritores Android no conocen componentes ni utilizan reflexión.

Las tareas se encadenan con la compilación normal. Un cambio en los catálogos invalida sus entradas, regenera las proyecciones y compila Android. Si se elimina una propiedad se retiran sus recursos generados; los consumidores de ese nombre deben migrarse. El host del AAR no ejecuta token-exporter ni necesita reflexión en la app.

## Consumo en Compose

```kotlin
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokensOverride
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.textviews.TextView

MyLibraryTheme {
    val library = LibraryTheme.current
    TextView(
        text = "Información",
        tokensOverride = TextViewTokensOverride(
            typography = library.typography.headline24,
            textColor = library.colors.linkDefault,
            iconStart = library.icons.alerts.info,
        ),
    )
}
```

También están disponibles `LibraryColorRole`, `LibraryTypographyRole` y `LibraryIconRole` en `theme.catalog`. Para consultar colores por rol importar `theme.catalog.get`:

```kotlin
val color = library.colors[LibraryColorRole.LinkDefault]
val typography = library.typography[LibraryTypographyRole.Headline24]
val icon = library.icons[LibraryIconRole.AlertsInfo]
```

Los roles contienen nombres; consultan la colección recibida y respetan sus personalizaciones. Los accesos directos continúan siendo la opción más breve. El adaptador de iconos utiliza `iconSize` cuando no se proporciona un tamaño Compose explícito.

## Consumo directo desde XML

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:theme="@style/ThemeOverlay.Maracuya">

    <cruxui.android.lib.wrappers.components.textviews.TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Información"
        android:textAppearance="?attr/libraryTypographyHeadline24"
        android:textColor="?attr/libraryColorLinkDefault"
        android:drawableStart="?attr/libraryIconAlertsInfo" />

    <ImageView
        android:layout_width="?attr/libraryIconAlertsInfoSize"
        android:layout_height="?attr/libraryIconAlertsInfoSize"
        android:src="?attr/libraryIconAlertsInfo"
        android:tint="?attr/libraryIconAlertsInfoTint"
        android:contentDescription="Información" />
</LinearLayout>
```

En producción, usar strings localizados como muestra `res/layout/catalog_preview.xml`. El overlay sin sufijo sigue los recursos de modo nocturno; `.Light` y `.Dark` fuerzan uno. El asset, tamaño y tinte son atributos distintos: cada widget decide qué propiedades aplica. El tinte null de Library se exporta como `@null`; el puente no inventa un color. Un atributo de drawable por sí solo no aplica automáticamente un tamaño ni la política de color de un componente Compose.

`ThemeExports.xmlReferences` y `LibraryXmlReferences` agrupan los mismos IDs:

| Acceso Kotlin | Referencia XML | Valor al resolver |
| --- | --- | --- |
| `colors.linkDefault` | `?attr/libraryColorLinkDefault` | Color/ColorStateList |
| `typography.headline24` | `?attr/libraryTypographyHeadline24` | TextAppearance |
| `icons.alerts.info` | `?attr/libraryIconAlertsInfo` | Drawable |
| `iconSizes.alerts.info` | `?attr/libraryIconAlertsInfoSize` | Dimensión |
| `iconTints.alerts.info` | `?attr/libraryIconAlertsInfoTint` | Color opcional |

Todas las hojas Kotlin son `@AttrRes`. Resolverlas con el Context temático; no pasarlas como si fueran `@ColorRes` o `@DrawableRes`.

## Recetas y personalización en ejecución

Registrar antes de inflar el layout:

```kotlin
TextViewTokensOverrideRegistry.register("information") { library: LibraryThemeSnapshot ->
    TextViewTokensOverride(
        typography = library.typography.body1,
        textColor = library.colors.linkDefault,
        iconStart = library.icons.alerts.info,
    )
}
```

El XML selecciona `app:textViewTokensOverride="information"`. La función recibe el tema del ámbito de la View; no debe capturar un snapshot global ni leer CompositionLocals. La misma función de receta puede utilizarse en Compose pasándole `LibraryTheme.current`.

Para setters nativos:

```kotlin
view.setColorRole(LibraryColorRole.InfoText)
view.setIconStart(ViewIcon.Role(LibraryIconRole.AlertsInfo))
view.setTypography(LibraryTypographyRole.Headline24)
```

`TextColorRole` y `TextTypographyRole` siguen disponibles como aliases. Las selecciones `Semantic { ... }` también siguen funcionando. Los roles se evalúan al resolver la View y se actualizan al proporcionar otro snapshot. Pasar un `ColorToken` o `IconToken` fijo expresa una sustitución fija.

Una marca se personaliza con las mismas colecciones:

```kotlin
val base = LibraryThemes.Maracuya
val custom = base.copy(
    lightTokens = base.lightTokens.copy(brandPrimary = ColorToken(0xff123456)),
    iconAtoms = base.iconAtoms.copy(
        alertsIconsLibrary = base.iconAtoms.alertsIconsLibrary.copy(
            info = base.iconAtoms.alertsIconsLibrary.warning.copy(iconSize = 24),
        ),
    ),
)
```

`MyLibraryTheme(theme = custom)` aplica el tema en Compose. `view.setThemeTokens(custom.resolve(false))` lo aplica a esa View. Para exportarlo permanentemente como XML, declararlo en `LibraryThemes.all` y recompilar. Los cambios en ejecución no reescriben recursos compilados ni reinflan XML automáticamente.

## Cómo ampliar el catálogo

1. **Color:** agregar una propiedad `ColorToken` en `LibraryColorTokens` y definir sus valores por modo en cada paleta construida explícitamente.
2. **Icono:** agregar la propiedad `IconToken` y su valor base en su clase `atoms/icons/Library*`. `resourceName` debe existir en `res/drawable`; el catálogo exportable no debe usar IDs del host.
3. **Grupo:** agregar su propiedad y default en `LibraryIconTokens`, con `@get:LibraryIconGroup("nombre")`. No se modifica ningún registro del exportador.
4. Compilar. Aparecen roles, consultas, atributos, enlaces de tema y referencias Kotlin.

Modificar un token existente propaga sus valores base al recompilar. Las sustituciones explícitas de una marca siguen prevaleciendo. No modificar clases generadas, resolutores de wrappers ni listas de roles. Una tipografía nueva conserva el mismo procedimiento descrito en la [guía tipográfica](05-tipografia-fuente-unica.md).

## Escalar a otros componentes

Un Button puede recibir el mismo `LibraryThemeSnapshot`, usar un `TokensOverrideRegistry<ButtonTokensOverride>` propio y seleccionar `library.colors`, `library.typography` e `library.icons`. El catálogo no incorpora comportamiento, variantes ni callbacks. `ViewIcon` y `TextColor` describen fuentes; los resolutores seleccionan tokens; los adaptadores cargan recursos; cada componente mantiene sus reglas visuales y de interacción.

La exportación de estos recursos funciona sin `TextWidgetStyleWriter`. Crear más componentes no añade casos a `LibraryIconsSource`, `ColorCatalogSource`, `CatalogApiWriter` ni a los resolutores de roles.
