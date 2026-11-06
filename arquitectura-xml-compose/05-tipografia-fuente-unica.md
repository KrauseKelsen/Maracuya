# Una definición tipográfica para Compose, Kotlin y XML

`atoms/LibraryTypographyTokens.kt` define todos los valores base. `LibraryTypography` y `TextTypographyRole`
se generan automáticamente desde sus propiedades públicas `TypographyToken`; ya no se mantiene otra lista
manual de estilos admitidos. Las propiedades de la colección del tema siguen siendo tipadas y admiten `copy`.

La biblioteca contiene 28 estilos al introducir esta generación. Encabezados, subtítulos, cuerpos, cursivas,
legales, botones, label, placeholder y bottomText se exportan por el mismo mecanismo. `Label`, `Caption` y `Body`
eran únicamente tres accesos abreviados del wrapper; los recursos XML ya disponían del catálogo completo.
Ahora los roles también lo incluyen completo. `Body` conserva compatibilidad y equivale a `Body1`.

## Consumo desde Compose

Dentro del tema del DS, la colección efectiva conserva las personalizaciones de marca:

```kotlin
MyLibraryTheme {
    val library = LibraryTheme.current
    TextView(
        text = "Título",
        tokensOverride = TextViewTokensOverride(
            typography = library.typography.headline24Medium,
        ),
    )
}
```

`TextView` y `TextViewTokensOverride` corresponden a `ui.components.textviews` y
`core.components.textviews.tokens`, respectivamente. Un `androidx.compose.material3.Text` recibe el resultado de
`TypographyComposeAdapter.toTextStyle(token, fontFamily)`; `TypographyToken` no es un `TextStyle` de Compose.

## Consumo nativo desde Kotlin

```kotlin
textView.setTypography(TextTypographyRole.Headline24Medium)
textView.setTypography(TextTypographyRole.Body2Italic)
textView.setTypography(TextTypographyRole.LegalBold)
```

El rol almacena un nombre semántico y lo consulta en la biblioteca de esa instancia. No congela el token del
catálogo base. `TextTypography.Semantic { it.headline24Medium }` conserva la alternativa por selector;
`TextTypography.Value(token)` expresa un valor fijo. `LibraryTypographyTokens.headline24Medium` permite
consultar el default directamente, pero omite los cambios del tema activo.

## Consumo directo desde XML

Aplicar un overlay de marca en el contexto Android y seleccionar el atributo del catálogo:

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
        android:text="Título"
        android:textAppearance="?attr/libraryTypographyHeadline24Medium" />
</LinearLayout>
```

El mismo patrón permite `libraryTypographyBody2Italic`, `libraryTypographyLegalBold` y cualquier otro estilo
existente. No necesita `register`, un atributo `mrcyTypographyToken` ni nuevas opciones en cada componente.
Para fijar marca se puede usar `android:textAppearance="@style/TextAppearance.Maracuya.LegalBold"`.
Los textos literales del ejemplo deben convertirse en recursos string en una aplicación localizada.

`res/layout/typography_preview.xml` incluye tres casos verificables sin inicializar ningún registro.
Los estilos y atributos generados también pueden consumirse en widgets Android que acepten TextAppearance.
Si el widget estándar necesita atributos de párrafo directamente sobre el widget, utilizar el estilo generado
`@style/Widget.Maracuya.Typography.Nombre`, con el overlay correspondiente.

Para configurar una combinación de tokens o seguir cambios de tema en ejecución, registrar la receta habitual:

```kotlin
TextViewTokensOverrideRegistry.register("titulo") { library: LibraryThemeSnapshot ->
    TextViewTokensOverride(typography = library.typography.headline24Medium)
}
```

Después seleccionar `app:textViewTokensOverride="titulo"`. Los atributos XML explícitos conservan su prioridad
sobre la receta: no combinar un TextAppearance fijo con una receta esperando que esta sustituya esas métricas.
Los recursos XML se producen al construir el AAR. Cambiar un tema durante la ejecución no crea IDs nuevos ni
reinfla el Context; usar roles o recetas para seguir esas personalizaciones.

## Agregar o modificar un estilo

Modificar exclusivamente `LibraryTypographyTokens`. Ejemplo de una propiedad nueva:

```kotlin
/** Encabezado de campaña compartido por los consumidores del DS. */
val campaignTitle = TypographyToken(
    fontSize = FontSizeTokens.s24,
    lineHeight = LineHeightTokens.lineHeightFrom(FontSizeTokens.s24, LineHeightTokens.ShortRatio),
    weight = FontWeightTokens.Bold,
)
```

Tras compilar aparecen automáticamente:

- `library.typography.campaignTitle` y `library.typography.copy(campaignTitle = ...)`.
- `TextTypographyRole.CampaignTitle`.
- `?attr/libraryTypographyCampaignTitle`.
- `@style/TextAppearance.Maracuya.CampaignTitle` y sus dimensiones.
- `ThemeExports.xmlReferences.typography.campaignTitle`.

El ejemplo no incorpora ese estilo ficticio al catálogo. No editar enums, resolutores, escritores ni la tabla
histórica de compatibilidad. Renombrar o eliminar una propiedad cambia la API pública de esa tipografía.

Para cursivas declarar `italic = true`: ese campo es el contrato que usan ambos adaptadores. `style` es un campo
histórico y no sustituye el peso ni el booleano de cursiva. Los estilos de cursiva existentes ya lo declaran.
La familia tipográfica continúa perteneciendo al tema, separada de las métricas de cada estilo.

## Generación y responsabilidades

1. `catalogSchema` compila los catálogos Library de colores, tipografías e iconos y sus tipos base.
2. `generateCatalogApi` descubre los nombres y genera LibraryTypography y TextTypographyRole.
3. El exportador completo compila esa API, resuelve LibraryThemes y obtiene las métricas efectivas con `asMap`.
4. Los escritores existentes generan atributos, recursos y enlaces; CatalogApiWriter entrega también las
   fuentes semánticas y de roles a Android.
5. Compose y Views consumen el mismo tema; ninguna View usa reflexión para resolver un rol.

Las pruebas recorren todas las propiedades del objeto Library, comprueban los recursos y las conversiones
Compose/nativas, inflan el ejemplo XML y verifican que un nombre nuevo se incorpora sin editar otras tablas.

La generación se comparte ahora con colores e iconos. `TextTypographyRole` es un alias de `LibraryTypographyRole`;
`CorporateTypography` es un alias de compatibilidad para `theme.collections.LibraryTypography`.
Consultar la [estructura común actual](06-colores-iconos-fuente-unica.md).
