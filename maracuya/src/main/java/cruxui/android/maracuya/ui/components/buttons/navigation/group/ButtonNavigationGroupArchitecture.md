# ButtonNavigationGroup Architecture

Este documento describe la arquitectura de `ButtonNavigationGroupMrcy`.

El grupo no crea un boton visual nuevo. Solo hace dos cosas:

1. Une una lista personalizada de hijos compatibles con `ButtonNavigationMrcy`.
2. Renderiza una de las cuatro variantes fijas del grupo.

La estructura replica `ButtonNavigationArchitecture.md`: cada capa declarativa tiene
su resolver de logica y el render solo orquesta.

## Mapa De Carpetas

| Carpeta / Archivo | Tipo | Responsabilidad principal |
| --- | --- | --- |
| `ButtonNavigationGroupMrcy.kt` | Render | Orquesta resolvers y renderiza la fila final. |
| `child/ButtonNavigationChildMrcy.kt` | Child render | Pinta el child resuelto usando `ButtonNavigationMrcy` como detalle interno. |
| `child/core/ButtonNavigationChild.kt` | Child core config | Declara hijos personalizados compatibles con `ButtonNavigationMrcy`. |
| `child/variant/ButtonNavigationChildVariant.kt` | Child variant declarativo | Declara las variantes permitidas para hijos personalizados. |
| `child/variant/ButtonNavigationChildVariantResolver.kt` | Child variant resolver | Traduce la variante del child a `ButtonNavigationMrcy`. |
| `child/tokens/ButtonNavigationChildTokens.kt` | Child tokens declarativos | Declara overrides visuales permitidos para el child. |
| `child/tokens/ButtonNavigationChildTokensResolver.kt` | Child tokens resolver | Traduce overrides del child a overrides nativos sin exponer loading. |
| `child/tokensrender/ButtonNavigationChildTokensRender.kt` | Child render tokens | Declara el hijo final listo para render nativo. |
| `child/tokensrender/ButtonNavigationChildTokensRenderResolver.kt` | Child render resolver | Convierte el child publico en parametros nativos. |
| `core/ButtonNavigationGroupDefaults.kt` | Core defaults | Centraliza medidas por defecto del grupo. |
| `variant/ButtonNavigationGroupVariant.kt` | Group variant declarativo | Declara las cuatro variantes fijas publicas. |
| `variant/ButtonNavigationGroupVariantResolver.kt` | Group variant resolver | Responde decisiones concretas de variante sin modelos intermedios. |
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
child/core/ButtonNavigationChild + buttonSpacing
    -> ButtonNavigationGroupTokensResolver
    -> ButtonNavigationGroupResolvedTokens

child/core/ButtonNavigationChild + ResolvedTokens
    -> ButtonNavigationGroupTokensRenderResolver
    -> ButtonNavigationChildTokensRenderResolver
    -> ButtonNavigationGroupTokensRender

ButtonNavigationGroupMrcy
    -> delega cada hijo a ButtonNavigationChildMrcy
```

Cuando se recibe `children`, el developer personaliza cada hijo. El grupo no cambia
su behavior interno: solo traduce su variante propia, aplica overrides visuales,
ajusta forma, enabled global y separacion.

## Reglas De Capa

| Capa | Puede hacer | No debe hacer |
| --- | --- | --- |
| **Child Variant** | Declarar `PRIMARY`/`SECONDARY` del child. | Exponer square, loading, leer iconos reales o renderizar UI. |
| **Child VariantResolver** | Traducir la variante del child a la variante nativa. | Resolver labels del group, loading o tokens visuales. |
| **Child Tokens** | Declarar overrides visuales permitidos por el child. | Exponer `loadingIconToken` u otros tokens de responsabilidades externas. |
| **Child TokensResolver** | Traducir overrides del child a overrides nativos. | Leer `LocalLibraryIcons` o decidir variantes. |
| **Child TokensRenderResolver** | Crear el render nativo del child personalizado. | Resolver spacing, shapes por indice o loading del grupo. |
| **Child Mrcy** | Pintar un child ya resuelto con `ButtonNavigationMrcy`. | Resolver variante publica, spacing o tokens del grupo. |
| **Variant** | Declarar `BACK_CONTINUE`, `BACK_SUBMIT`, `SKIP_CONTINUE`, `SKIP_SUBMIT`. | Declarar loading, leer iconos reales o renderizar UI. |
| **VariantResolver** | Resolver labels fallback, variant nativa, visibilidad de icono y submit icon. | Crear tokens intermedios, leer `LocalLibraryIcons` o manejar loading. |
| **Behavior** | Declarar loading externo o loading iniciado por click. | Cambiar labels, iconos, variantes o spacing. |
| **BehaviorResolver** | Resolver `ButtonNavigationBehavior` por posicion y fallback de loading. | Crear tokens intermedios, leer tokens visuales o crear botones Compose. |
| **Core** | Declarar configuracion compartida, botones personalizados y defaults de layout. | Resolver variantes, behavior, tokens visuales o render final. |
| **Tokens** | Declarar spacing, anchos, minimos, radio y solape de union. | Resolver shapes por indice o leer estado interactivo. |
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

### `child/core/ButtonNavigationChild.kt`

Responsabilidad: **declarar un hijo personalizado que el grupo puede unir**.

Este modelo conserva solo la configuracion compatible con el layout del grupo:

- `variant`
- `buttonNavigationChildTokensOverride`
- `showIcon`
- `label`, `enabled`, `shape`, `width` y `minWidth`

No debe exponer loading, callbacks de loading, variantes square, shapes por
posicion, spacing, labels fallback ni overrides del submit fijo. Esa traduccion
ocurre en `tokensrender`.

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
- Los bordes internos se colapsan con `JoinedChildBorderOverlap` para evitar
  paredes verticales duplicadas entre hijos unidos.

Si `buttonSpacing` es mayor a `0.dp`, los botones no usan la logica de paredes y
cada uno conserva su shape propio.

### Variante fija

Si `buttonSpacing` no se envia, los dos botones se empujan a las paredes del
contenedor con `Spacer(weight = 1f)`. Sus anchos no son proporcionales al espacio:
usan el ancho enviado o su minimo.

Si `buttonSpacing` se envia, el grupo pinta la variante fija con esa distancia
entre botones y sin spacer ponderado.

## Child Personalizado

`child/core/ButtonNavigationChild` expone una configuracion restringida y compatible
con el grupo:

- `variant` (`ButtonNavigationChildVariant.PRIMARY` o `SECONDARY`)
- `buttonNavigationChildTokensOverride` para personalizaciones visuales puntuales
- `showIcon`
- `label`, `enabled`, `shape`, `width` y `minWidth`

Esto permite personalizar colores e iconos con overrides sin permitir square,
loading o behaviors que descuadran el layout unido.

`child/tokens/ButtonNavigationChildTokensOverride` no contiene `loadingIconToken`.
El adapter hacia `ButtonNavigationMrcy` vive dentro de `child/*`, como detalle
interno, para no filtrar responsabilidades nativas en la API publica del child.

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
    children = listOf(
        ButtonNavigationChild(
            label = "Volver",
            variant = ButtonNavigationChildVariant.SECONDARY,
            onClick = {},
        ),
        ButtonNavigationChild(
            label = "Continuar",
            variant = ButtonNavigationChildVariant.PRIMARY,
            onClick = {},
        ),
    ),
)
```

### Lista personalizada separada

```kotlin
ButtonNavigationGroupMrcy(
    children = listOf(
        ButtonNavigationChild(
            label = "Omitir",
            variant = ButtonNavigationChildVariant.SECONDARY,
            showIcon = false,
            onClick = {},
        ),
        ButtonNavigationChild(
            label = "Continuar",
            variant = ButtonNavigationChildVariant.PRIMARY,
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
