# ButtonNavigation Architecture

Este documento explica la arquitectura actual de `ButtonNavigationMrcy`.

`ButtonNavigationMrcy` mantiene una sola API publica para Compose. Las variantes `PRIMARY`, `SECONDARY`, `PRIMARYSQUARE` y `SECONDARYSQUARE` se resuelven por `variant`; no existe una sobrecarga separada para pasar `icon` por parametro.

## Mapa De Carpetas

| Carpeta / Archivo | Tipo | Responsabilidad principal |
| --- | --- | --- |
| `ButtonNavigationMrcy.kt` | Mrcy / Render | Orquesta resolvers y dibuja el boton. |
| `ButtonNavigationContent.kt` | Render interno | Pinta icono y label dentro del boton cuando corresponde. |
| `core/ButtonNavigationLoadingScope.kt` | Core | Expone acciones controladas para detener loading interno. |
| `behavior/ButtonNavigationBehavior.kt` | Behavior | Declara flags de comportamiento. |
| `behavior/ButtonNavigationBehaviorResolver.kt` | Behavior resolver | Resuelve estado interactivo, loading, click y rotacion. |
| `variant/ButtonNavigationVariant.kt` | Variant | Declara `PRIMARY`, `SECONDARY`, `PRIMARYSQUARE` y `SECONDARYSQUARE`. |
| `variant/ButtonNavigationVariantResolver.kt` | Variant resolver | Mapea a `ButtonVariant` y resuelve propiedades derivadas de la variante. |
| `tokens/ButtonNavigationTokens.kt` | Tokens | Declara colores, tipografia, familia e iconos base. |
| `tokens/ButtonNavigationTokensResolver.kt` | Tokens resolver | Resuelve tokens base desde el DS o fallback y aplica overrides. |
| `tokensrender/ButtonNavigationTokensRender.kt` | Tokens render | Declara tokens finales listos para renderizar. |
| `tokensrender/ButtonNavigationTokensRenderResolver.kt` | Tokens render resolver | Calcula colores e icono final segun estado visual. |

## Flujo De Resolucion

El componente se resuelve siempre en el mismo orden:

```kotlin
variant
    -> ButtonNavigationVariantResolver
    -> buttonModifier, buttonShape, buttonLabel, bottomLabel, bottomLabelSpacing, iconAtEnd

buttonNavigationTokens + buttonNavigationTokensOverride + variant
    -> ButtonNavigationTokensResolver
    -> ButtonNavigationTokens

buttonNavigationBehavior + enabled + onClick
    -> ButtonNavigationBehaviorResolver
    -> ButtonNavigationBehaviorState

ButtonNavigationTokens + ButtonNavigationBehaviorState + pressed
    -> ButtonNavigationTokensRenderResolver
    -> ButtonNavigationTokensRender

ButtonNavigationMrcy
    -> Button + ButtonNavigationContent
    -> LabelMrcy debajo del boton cuando variant es square y label existe
```

## Regla De Variantes Square

`PRIMARYSQUARE` y `SECONDARYSQUARE` no dependen de una sobrecarga con `icon`.

Cuando se declara:

```kotlin
ButtonNavigationMrcy(
    onClick = {},
    variant = ButtonNavigationVariant.SECONDARYSQUARE,
)
```

el componente se mantiene como `SECONDARYSQUARE`. El icono viene desde `ButtonNavigationTokens.iconToken`; si el caller no envia override, el resolver asigna un icono default del DS.

Si se necesita cambiar el icono, se hace desde tokens:

```kotlin
ButtonNavigationMrcy(
    label = "Contrato",
    onClick = {},
    variant = ButtonNavigationVariant.PRIMARYSQUARE,
    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
        iconToken = LocalLibraryIcons.current.general.contract.copy(
            iconSize = 24,
        ),
    ),
)
```

## Render De Label En Square

Para variantes square, `label` no se pinta dentro del `Button`.

Si `label` no es `null` ni blank:

- Se pinta debajo del boton.
- Usa `LabelMrcy`.
- Se separa del boton con `6.dp`.
- Queda centrado por el contenedor.
- Usa la tipografia resuelta en `ButtonNavigationTokens.typographyToken`; por default las variantes square usan `typography.legal`.
- Usa el color default de `LabelMrcy`, por lo que no hereda `contentColor` ni `iconColor` del boton.

Si `label` es `null` o blank, no se pinta label debajo.

## Reglas De Capa

| Capa | Puede hacer | No debe hacer |
| --- | --- | --- |
| **Behavior** | Declarar flags como `isLoading`, `startsLoadingOnClick`, `showIcon`. | Resolver clicks, animaciones, colores, iconos o variantes. |
| **BehaviorResolver** | Manejar estado Compose, loading interno, disabled y callback final. | Leer tokens visuales o decidir estilos de variante. |
| **Variant** | Declarar variantes publicas. | Resolver iconos, colores o tokens. |
| **VariantResolver** | Mapear a `ButtonVariant`, detectar square y resolver modifier, shape, posicion de icono, placement y spacing de label. | Crear tokens visuales o renderizar UI. |
| **Tokens** | Declarar datos visuales base y `merge`. | Leer estado de interaccion, pressed, loading o disabled. |
| **TokensResolver** | Resolver tokens base desde DS y aplicar overrides. | Resolver colores finales de render o click behavior. |
| **TokensRenderResolver** | Resolver colores e icono final para el render actual. | Crear tokens base, leer `LocalLibraryIcons` o resolver variantes. |
| **Core** | Declarar soporte compartido que no pertenece a variant, behavior, tokens ni render. | Resolver estado visual, leer tokens o crear UI. |
| **Mrcy** | Orquestar resolvers y renderizar UI con propiedades ya resueltas. | Decidir defaults por variante, resolver tokens base o duplicar modelos square. |

## API Compose

```kotlin
ButtonNavigationMrcy(
    label = "Continuar",
    onClick = {},
    variant = ButtonNavigationVariant.PRIMARY,
)
```

```kotlin
ButtonNavigationMrcy(
    label = "Contrato",
    onClick = {},
    variant = ButtonNavigationVariant.SECONDARYSQUARE,
    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
        iconToken = LocalLibraryIcons.current.general.contract.copy(
            iconSize = 24,
        ),
    ),
)
```

```kotlin
ButtonNavigationMrcy(
    onClick = {},
    variant = ButtonNavigationVariant.SECONDARYSQUARE,
)
```

## Tokens

`ButtonNavigationTokens` es el modelo unico para todas las variantes.

| Token | Uso |
| --- | --- |
| `contentColor` | Color del texto dentro del boton en variantes no square. |
| `contentPressColor` | Color de contenido cuando el boton esta pressed. |
| `containerColor` | Color base del contenedor. |
| `disabledContentColor` | Color de contenido disabled. |
| `disabledContainerColor` | Color de contenedor disabled. |
| `hoverContainerColor` | Color de contenedor en pressed/hover. |
| `borderContainerColor` | Color base de borde. |
| `borderDisabledColor` | Color de borde disabled. |
| `iconToken` | Icono renderizado dentro del boton. En square tambien define el icono visible. |
| `loadingIconToken` | Icono usado en loading. |
| `iconColor` | Color base del icono. |
| `typographyToken` | Tipografia del texto; en square alimenta el `LabelMrcy` inferior. |
| `fontFamilyToken` | Familia tipografica del texto; en square alimenta el `LabelMrcy` inferior. |

`ButtonNavigationTokensOverride` permite cambiar cualquiera de esos valores, incluido `iconToken`. Como `iconToken` esta tipado como `IconToken`, solo acepta iconos del Design System o instancias tipadas compatibles.

## Defaults Square

`ButtonNavigationTokensResolver` asigna defaults especiales para square:

| Variante | Contenedor | Borde | Icono default | Color icono | Tipografia label |
| --- | --- | --- | --- | --- | --- |
| `PRIMARYSQUARE` | `brandPrimary` | `brandPrimary` | `keyboardBackspaceRight` | `fgOnInverse` | `legal` |
| `SECONDARYSQUARE` | `errorFillSoft` | `brandPrimary` | `keyboardBackspaceLeft` | `brandPrimary` | `legal` |

Estos defaults evitan que `PRIMARYSQUARE` o `SECONDARYSQUARE` caigan accidentalmente en `PRIMARY` o `SECONDARY` cuando no se envia icono por parametro.

## Responsabilidades Principales

| Archivo | Responsabilidad |
| --- | --- |
| `ButtonNavigationMrcy.kt` | Mantener una sola API publica y renderizar usando propiedades ya resueltas. |
| `ButtonNavigationContent.kt` | Pintar contenido interno del `Button` usando tokens de render ya resueltos. |
| `ButtonNavigationTokensResolver.kt` | Resolver defaults para las cuatro variantes, incluidos iconos square. |
| `ButtonNavigationTokensRenderResolver.kt` | Resolver estado visual final sin conocer variantes ni DS. |
| `ButtonNavigationVariantResolver.kt` | Resolver `isSquare`, `ButtonVariant` base, modifier, shape, labels, spacing y posicion de icono. |

## Anti Patrones

- Crear otra sobrecarga de `ButtonNavigationMrcy` para square.
- Pasar `icon` como parametro publico del componente.
- Reintroducir tokens o render tokens exclusivos para square.
- Resolver `height`, `shape`, label interno, label inferior o spacing de label directamente en `ButtonNavigationMrcy`.
- Usar `contentColor` o `iconColor` del boton para el label inferior.
- Hacer que `PRIMARYSQUARE` o `SECONDARYSQUARE` caigan en `PRIMARY` o `SECONDARY` por ausencia de icono.
