# ButtonNavigationGroup Architecture

Este documento describe la arquitectura de `ButtonNavigationGroupMrcy`.

El grupo no crea un boton nuevo. Solo hace dos cosas:

1. Une una lista personalizada de botones `ButtonNavigationMrcy`.
2. Renderiza una de las cuatro variantes fijas del grupo.

La estructura replica `ButtonNavigationArchitecture.md`: cada capa declarativa tiene
su resolver de logica y el render solo orquesta.

## Mapa De Carpetas

| Carpeta / Archivo | Tipo | Responsabilidad principal |
| --- | --- | --- |
| `ButtonNavigationGroupMrcy.kt` | Render | Orquesta resolvers y renderiza la fila final. |
| `core/ButtonNavigationGroupButton.kt` | Core config | Declara botones personalizados que heredan `ButtonNavigationMrcy`. |
| `core/ButtonNavigationGroupDefaults.kt` | Core defaults | Centraliza medidas por defecto del grupo. |
| `variant/ButtonNavigationGroupVariant.kt` | Variant declarativo | Declara las cuatro variantes fijas publicas. |
| `variant/ButtonNavigationGroupVariantResolver.kt` | Variant resolver | Responde decisiones concretas de variante sin modelos intermedios. |
| `behavior/ButtonNavigationGroupBehavior.kt` | Behavior declarativo | Declara loading del boton trailing. |
| `behavior/ButtonNavigationGroupBehaviorResolver.kt` | Behavior resolver | Traduce behavior publico a `ButtonNavigationBehavior` nativo. |
| `tokens/ButtonNavigationGroupTokens.kt` | Tokens declarativos | Declara spacing, anchos, minimos y radio. |
| `tokens/ButtonNavigationGroupTokensResolver.kt` | Tokens resolver | Normaliza medidas y resuelve el icono submit desde el DS. |
| `tokensrender/ButtonNavigationGroupTokensRender.kt` | Render tokens | Declara botones finales listos para renderizar. |
| `tokensrender/ButtonNavigationGroupTokensRenderResolver.kt` | Render tokens resolver | Combina variant, behavior y tokens en parametros de `ButtonNavigationMrcy`. |

## Flujo De Variante Fija

```kotlin
variant
    -> ButtonNavigationGroupVariantResolver
    -> labels fallback, variantes nativas, visibilidad de icono

buttonNavigationGroupBehavior
    -> ButtonNavigationGroupBehaviorResolver
    -> ButtonNavigationBehavior nativo y fallback loading

core defaults + buttonSpacing + widths
    -> ButtonNavigationGroupTokensResolver
    -> ButtonNavigationGroupResolvedTokens

variant + behavior + ResolvedTokens + labels/clicks
    -> ButtonNavigationGroupTokensRenderResolver
    -> ButtonNavigationGroupTokensRender

ButtonNavigationGroupMrcy
    -> renderiza ButtonNavigationMrcy hijos
```

## Flujo De Lista Personalizada

```kotlin
core/ButtonNavigationGroupButton + buttonSpacing
    -> ButtonNavigationGroupTokensResolver
    -> ButtonNavigationGroupResolvedTokens

core/ButtonNavigationGroupButton + ResolvedTokens
    -> ButtonNavigationGroupTokensRenderResolver
    -> ButtonNavigationGroupTokensRender

ButtonNavigationGroupMrcy
    -> une la lista de ButtonNavigationMrcy
```

Cuando se recibe `buttons`, el developer personaliza cada boton. El grupo no cambia
sus tokens, variant ni behavior: solo ajusta forma, enabled global y separacion.

## Reglas De Capa

| Capa | Puede hacer | No debe hacer |
| --- | --- | --- |
| **Variant** | Declarar `BACK_CONTINUE`, `BACK_SUBMIT`, `SKIP_CONTINUE`, `SKIP_SUBMIT`. | Declarar loading, leer iconos reales o renderizar UI. |
| **VariantResolver** | Resolver labels fallback, variant nativa, visibilidad de icono y submit icon. | Crear tokens intermedios, leer `LocalLibraryIcons` o manejar loading. |
| **Behavior** | Declarar loading externo o loading iniciado por click. | Cambiar labels, iconos, variantes o spacing. |
| **BehaviorResolver** | Resolver `ButtonNavigationBehavior` por posicion y fallback de loading. | Crear tokens intermedios, leer tokens visuales o crear botones Compose. |
| **Core** | Declarar configuracion compartida, botones personalizados y defaults de layout. | Resolver variantes, behavior, tokens visuales o render final. |
| **Tokens** | Declarar spacing, anchos, minimos y radio. | Resolver shapes por indice o leer estado interactivo. |
| **TokensResolver** | Normalizar medidas y obtener el icono submit del DS. | Crear `ButtonNavigationMrcy` o decidir labels. |
| **TokensRenderResolver** | Crear especificaciones finales de hijos, shapes y overrides puntuales. | Leer `LocalLibraryIcons` o resolver variantes publicas. |
| **Mrcy** | Orquestar resolvers y pintar la fila. | Tener `when` de variantes, resolver iconos o calcular loading. |

## Variantes

Solo existen cuatro variantes publicas:

```kotlin
enum class ButtonNavigationGroupVariant {
    BACK_CONTINUE,
    BACK_SUBMIT,
    SKIP_CONTINUE,
    SKIP_SUBMIT,
}
```

Los estados loading no son variantes. Se manejan con `ButtonNavigationGroupBehavior`.
Asi se obtienen los seis casos de uso:

| Caso | Configuracion |
| --- | --- |
| Back con Continue | `BACK_CONTINUE` + `Default` |
| Back con Submit | `BACK_SUBMIT` + `Default` |
| Back con Loading | `BACK_CONTINUE` o `BACK_SUBMIT` + `Loading` |
| Skip con Continue | `SKIP_CONTINUE` + `Default` |
| Skip con Submit | `SKIP_SUBMIT` + `Default` |
| Skip con Loading | `SKIP_CONTINUE` o `SKIP_SUBMIT` + `Loading` |

Los labels de ambos botones son editables. La variante del boton hijo y la
configuracion fija del grupo no son editables desde la API de variantes.

Los textos `Back`, `Skip`, `Continue`, `Submit` y `Loading` son fallbacks quemados
por los resolvers. No viven en un modelo `VariantTokens` ni forman parte de la
variante declarativa.

## Core

La carpeta `core` contiene configuracion compartida de la API publica del grupo.
No es una capa de logica visual: solo declara modelos y defaults que otras capas
pueden consumir.

### `core/ButtonNavigationGroupButton.kt`

Responsabilidad: **declarar un boton personalizado que el grupo puede unir**.

Este modelo conserva la herencia del boton nativo:

- `variant`
- `buttonNavigationBehavior`
- `buttonNavigationTokens`
- `buttonNavigationTokensOverride`
- `onTrailingClick`
- `onLoadingClick`
- `label`, `enabled`, `shape`, `width` y `minWidth`

No debe resolver shapes por posicion, spacing, labels fallback ni overrides del
submit fijo. Esa traduccion ocurre en `tokensrender`.

### `core/ButtonNavigationGroupDefaults.kt`

Responsabilidad: **centralizar defaults de layout y configuracion del grupo**.

Define `ButtonSpacing`, anchos default, anchos minimos y `CornerRadius`. No debe
leer `LocalComposition`, calcular colores ni decidir variantes.

## Spacing Y Forma

### Lista personalizada

Si `buttonSpacing` no se envia, o se envia como `0.dp`, los botones se unen:

- Primer boton: pared izquierda redonda, pared derecha recta.
- Botones intermedios: ambas paredes rectas.
- Ultimo boton: pared izquierda recta, pared derecha redonda.
- Si solo hay un boton, conserva su forma original.

Si `buttonSpacing` es mayor a `0.dp`, los botones no usan la logica de paredes y
cada uno conserva su shape propio.

### Variante fija

Si `buttonSpacing` no se envia, los dos botones se empujan a las paredes del
contenedor con `Spacer(weight = 1f)`. Sus anchos no son proporcionales al espacio:
usan el ancho enviado o su minimo.

Si `buttonSpacing` se envia, el grupo pinta la variante fija con esa distancia
entre botones y sin spacer ponderado.

## Herencia Del Boton Nativo

`core/ButtonNavigationGroupButton` expone la configuracion heredable de
`ButtonNavigationMrcy`:

- `variant` (`PRIMARY` o `SECONDARY`)
- `buttonNavigationBehavior`
- `buttonNavigationTokens`
- `buttonNavigationTokensOverride`
- `onTrailingClick`
- `onLoadingClick`
- `label`, `enabled`, `shape`, `width` y `minWidth`

Esto permite que los colores, iconos, loading, labels nulos e icon-only sigan
siendo resueltos por el boton nativo.

## Ejemplos

### Variante fija

```kotlin
ButtonNavigationGroupMrcy(
    variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
    leadingLabel = "Volver",
    trailingLabel = "Continuar",
    onLeadingButtonClick = {},
    onTrailingButtonClick = {},
)
```

### Variante fija con distancia

```kotlin
ButtonNavigationGroupMrcy(
    variant = ButtonNavigationGroupVariant.SKIP_SUBMIT,
    buttonSpacing = 12.dp,
    onLeadingButtonClick = {},
    onTrailingButtonClick = {},
)
```

### Loading del trailing

```kotlin
ButtonNavigationGroupMrcy(
    variant = ButtonNavigationGroupVariant.BACK_CONTINUE,
    buttonNavigationGroupBehavior = ButtonNavigationGroupBehavior.Loading,
    onLeadingButtonClick = {},
    onTrailingButtonClick = {},
)
```

### Lista personalizada unida

```kotlin
ButtonNavigationGroupMrcy(
    buttons = listOf(
        ButtonNavigationGroupButton(
            label = "Volver",
            variant = ButtonNavigationVariant.SECONDARY,
            onClick = {},
        ),
        ButtonNavigationGroupButton(
            label = "Continuar",
            variant = ButtonNavigationVariant.PRIMARY,
            onClick = {},
        ),
    ),
)
```

### Lista personalizada separada

```kotlin
ButtonNavigationGroupMrcy(
    buttons = listOf(
        ButtonNavigationGroupButton(
            label = "Omitir",
            variant = ButtonNavigationVariant.SECONDARY,
            buttonNavigationBehavior = ButtonNavigationBehavior(showIcon = false),
            onClick = {},
        ),
        ButtonNavigationGroupButton(
            label = "Continuar",
            variant = ButtonNavigationVariant.PRIMARY,
            onClick = {},
        ),
    ),
    buttonSpacing = 12.dp,
)
```

## Checklist Para Cambios Futuros

| Pregunta | Si la respuesta es "si", toca |
| --- | --- |
| Necesitas una nueva combinacion fija? | `variant/*` y `tokensrender/*` si requiere traduccion final. |
| Necesitas cambiar loading del grupo? | `behavior/*`. |
| Necesitas cambiar defaults publicos de spacing, radio o anchos? | `core/ButtonNavigationGroupDefaults.kt`. |
| Necesitas cambiar normalizacion de spacing, radio o anchos? | `tokens/*`. |
| Necesitas cambiar shapes por posicion? | `tokensrender/ButtonNavigationGroupTokensRenderResolver.kt`. |
| Necesitas cambiar solo la fila Compose? | `ButtonNavigationGroupMrcy.kt`. |

## Anti-Patrones

Evitar:

- Agregar variantes `LOADING`; loading pertenece a behavior.
- Crear `VariantTokens` o `BehaviorTokens` para el group; son redundantes.
- Resolver iconos reales en `VariantResolver`.
- Calcular shapes por indice en `ButtonNavigationGroupMrcy`.
- Duplicar colores, tipografias o iconos que ya resuelve `ButtonNavigationMrcy`.
- Usar pesos para definir el ancho de los botones del group.
- Poner modelos de configuracion publica fuera de `core` cuando no pertenecen a una capa logica.
