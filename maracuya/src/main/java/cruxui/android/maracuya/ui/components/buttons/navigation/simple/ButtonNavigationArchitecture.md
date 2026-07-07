# ButtonNavigation Architecture

Este documento explica la arquitectura de `ButtonNavigationMrcy` y la responsabilidad de cada capa dentro del componente.

`ButtonNavigationMrcy` es un componente nativo del Design System. A diferencia de `ButtonNavigationGroupMrcy`, este componente si resuelve **tokens visuales**, **variant**, **behavior**, **tokens de render** y finalmente compone la UI.

> **Regla principal:** cada archivo debe tener una responsabilidad cerrada. Si una clase empieza a decidir cosas de otra capa, el componente se vuelve dificil de replicar.

## Mapa De Carpetas

| Carpeta / Archivo | Tipo | Responsabilidad principal |
| --- | --- | --- |
| `ButtonNavigationMrcy.kt` | Render | Orquesta resolvers y dibuja el boton. |
| `ButtonNavigationLoadingScope.kt` | Behavior support | Expone una accion controlada para detener loading interno. |
| `behavior/ButtonNavigationBehavior.kt` | Behavior declarativo | Declara flags de comportamiento. |
| `behavior/ButtonNavigationBehaviorResolver.kt` | Behavior resolver | Resuelve estado interactivo, loading, click y rotacion. |
| `variant/ButtonNavigationVariant.kt` | Variant declarativo | Declara variantes publicas. |
| `variant/ButtonNavigationVariantResolver.kt` | Variant resolver | Mapea `ButtonNavigationVariant` al `ButtonVariant` base que hereda. |
| `tokens/ButtonNavigationTokens.kt` | Tokens declarativos | Declara colores, tipografia, familia e iconos base. |
| `tokens/ButtonNavigationTokensResolver.kt` | Tokens resolver | Resuelve tokens base desde el DS o fallback y aplica overrides. |
| `tokensrender/ButtonNavigationTokensRender.kt` | Render tokens | Declara tokens finales listos para renderizar. |
| `tokensrender/ButtonNavigationTokensRenderResolver.kt` | Render tokens resolver | Calcula colores e iconos finales segun estado visual. |

## Flujo De Resolucion

El componente se resuelve siempre en este orden:

```kotlin
variant
    -> ButtonNavigationVariantResolver
    -> ButtonVariant base heredado

buttonNavigationTokens + override + variant
    -> ButtonNavigationTokensResolver
    -> ButtonNavigationTokens

buttonNavigationBehavior + enabled + onClick
    -> ButtonNavigationBehaviorResolver
    -> ButtonNavigationBehaviorState

ButtonNavigationTokens + ButtonNavigationBehaviorState + pressed
    -> ButtonNavigationTokensRenderResolver
    -> ButtonNavigationTokensRender

ButtonNavigationMrcy
    -> renderiza Button + ButtonNavigationContent
```

## Reglas De Capa

| Capa | Puede hacer | No debe hacer |
| --- | --- | --- |
| **Behavior** | Declarar flags como `isLoading`, `startsLoadingOnClick`, `showIcon`. | Resolver clicks, animaciones, colores, iconos o variantes. |
| **BehaviorResolver** | Manejar estado Compose, loading interno, disabled y callback final. | Leer tokens visuales o decidir estilos de variante. |
| **Variant** | Declarar `PRIMARY` y `SECONDARY`. | Mapear a `ButtonVariant`, iconos, posiciones o tokens. |
| **VariantResolver** | Resolver que `ButtonVariant` base se hereda. | Crear clases intermedias redundantes como roles o posiciones obvias. |
| **Tokens** | Declarar datos visuales base y `merge`. | Leer estado de interaccion, pressed, loading o disabled. |
| **TokensResolver** | Resolver tokens base desde DS y aplicar overrides. | Resolver colores finales de render o click behavior. |
| **TokensRenderResolver** | Resolver colores e icono final para el render actual. | Crear tokens base, leer `LocalLibraryIcons` o resolver variantes. |
| **Mrcy** | Orquestar resolvers y renderizar UI. | Concentrar logica de negocio, resolver tokens base o manipular loading directamente. |

## ButtonNavigationMrcy.kt

### `ButtonNavigationMrcy`

Responsabilidad: **componer la UI publica del boton de navegacion**.

| Parametro | Responsabilidad |
| --- | --- |
| `label` | Texto visible del boton. Puede ser `null`. |
| `onClick` | Accion principal del boton. |
| `enabled` | Habilita o bloquea interaccion. |
| `variant` | Define si se hereda estilo `PRIMARY` o `SECONDARY`. |
| `buttonNavigationBehavior` | Configuracion declarativa de comportamiento. |
| `buttonNavigationTokens` | Tokens base opcionales enviados manualmente. |
| `buttonNavigationTokensOverride` | Overrides puntuales sobre tokens resueltos. |
| `onLoadingClick` | Callback opcional asociado al ciclo de loading. |
| `onTrailingClick` | Alias legado para compatibilidad con integraciones previas. |

Esta funcion:

- Pide tokens base a `ButtonNavigationTokensResolver`.
- Pide comportamiento resuelto a `ButtonNavigationBehaviorResolver`.
- Lee `pressed` desde `interactionSource`.
- Pide tokens finales de render a `ButtonNavigationTokensRenderResolver`.
- Convierte tokens del DS a tipos Compose.
- Renderiza el `Button` de Material.

No debe:

- Resolver iconos desde `LocalLibraryIcons`.
- Decidir colores finales de disabled, pressed o loading.
- Manejar estado interno de loading.
- Crear modelos extra de variante.

### `ButtonNavigationContent`

Responsabilidad: **renderizar texto e icono en el orden correcto**.

| Entrada | Uso |
| --- | --- |
| `tokens` | Contiene icono final, colores y tipografia ya resueltos. |
| `iconAtEnd` | Indica si el icono se pinta despues del texto. |
| `behaviorState` | Entrega la rotacion del icono cuando hay loading. |
| `contentColor` | Color final del texto. |
| `iconColor` | Color final del icono. |
| `textStyle` | Estilo tipografico Compose final. |

> `ButtonNavigationContent` es privado porque no representa una API del DS. Solo existe para separar el render interno y mantener `ButtonNavigationMrcy` legible.

## ButtonNavigationLoadingScope.kt

### `ButtonNavigationLoadingScope`

Responsabilidad: **exponer acciones seguras para controlar el loading local**.

| Funcion | Responsabilidad |
| --- | --- |
| `stopLoading()` | Detiene el loading interno iniciado por `startsLoadingOnClick`. |

Este scope evita exponer directamente el estado mutable interno del resolver.

Ejemplo conceptual:

```kotlin
ButtonNavigationMrcy(
    buttonNavigationBehavior = ButtonNavigationBehavior.Loading,
    onLoadingClick = {
        // cuando termina la carga:
        stopLoading()
    },
    onClick = { /* iniciar accion */ },
)
```

## behavior/ButtonNavigationBehavior.kt

### `ButtonNavigationBehavior`

Responsabilidad: **declarar comportamiento, no resolverlo**.

| Propiedad | Descripcion |
| --- | --- |
| `isLoading` | Fuerza estado loading desde fuera del componente. |
| `startsLoadingOnClick` | Indica que el loading interno inicia al presionar el boton. |
| `showIcon` | Controla si el icono se debe mostrar. |

| Companion | Uso |
| --- | --- |
| `Default` | Sin loading inicial, sin inicio automatico de loading y con icono visible. |
| `Loading` | Configuracion lista para iniciar loading al hacer click. |

No debe contener:

- `MutableState`.
- Animaciones.
- Callbacks.
- Tokens.
- Decisiones de `PRIMARY` o `SECONDARY`.

## behavior/ButtonNavigationBehaviorResolver.kt

### `ButtonNavigationBehaviorResolver`

Responsabilidad: **resolver el comportamiento interactivo real**.

| Funcion | Responsabilidad |
| --- | --- |
| `resolve(...)` | Resuelve loading activo, click final, bloqueo de click, visibilidad de icono y rotacion del loading. |
| `resolveLoadingRotationDegrees(...)` | Calcula la rotacion infinita del icono cuando `isLoading` esta activo. |

### `resolve(...)`

| Entrada | Uso |
| --- | --- |
| `behavior` | Declaracion publica del comportamiento. |
| `enabled` | Bloquea el click si el boton esta deshabilitado. |
| `onClick` | Accion principal que debe ejecutarse si el boton puede hacer click. |
| `onLoadingClick` | Callback opcional que recibe `ButtonNavigationLoadingScope`. |

| Salida | Significado |
| --- | --- |
| `isLoading` | Loading externo o interno activo. |
| `loadingIconRotationDegrees` | Grados de rotacion para el icono de loading. |
| `canClick` | `true` solo si el boton esta enabled y no esta cargando. |
| `showIcon` | Propaga si el icono debe mostrarse. |
| `onClick` | Callback final protegido contra clicks invalidos. |

### `ButtonNavigationBehaviorState`

Responsabilidad: **transportar estado de behavior ya resuelto hacia el render**.

Este modelo es interno porque solo debe ser consumido por `ButtonNavigationMrcy` y resolvers internos.

## variant/ButtonNavigationVariant.kt

### `ButtonNavigationVariant`

Responsabilidad: **declarar las variantes publicas disponibles**.

| Variante | Intencion visual |
| --- | --- |
| `PRIMARY` | Hereda `ButtonVariant.PRIMARY`; icono se pinta al final del contenido. |
| `SECONDARY` | Hereda `ButtonVariant.SECONDARY`; icono se pinta al inicio del contenido. |

No debe:

- Resolver `ButtonVariant`.
- Declarar iconos.
- Declarar posiciones con modelos extra.
- Crear roles redundantes.

## variant/ButtonNavigationVariantResolver.kt

### `ButtonNavigationVariantResolver`

Responsabilidad: **mapear la variante publica al `ButtonVariant` que hereda**.

| Funcion | Entrada | Salida |
| --- | --- | --- |
| `resolve(...)` | `ButtonNavigationVariant` | `ButtonVariant` |

Regla:

```kotlin
PRIMARY -> ButtonVariant.PRIMARY
SECONDARY -> ButtonVariant.SECONDARY
```

> Esta clase no debe resolver posicion de icono ni roles. Si la variante ya es `PRIMARY`, la posicion y el sentido del icono son obvios para el render y los tokens.

## tokens/ButtonNavigationTokens.kt

### `ButtonNavigationTokens`

Responsabilidad: **declarar tokens visuales base**.

| Grupo | Tokens |
| --- | --- |
| Contenido | `contentColor`, `contentPressColor`, `disabledContentColor` |
| Contenedor | `containerColor`, `disabledContainerColor`, `hoverContainerColor` |
| Borde | `borderContainerColor`, `borderDisabledColor` |
| Iconos | `iconToken`, `loadingIconToken`, `iconColor` |
| Tipografia | `typographyToken`, `fontFamilyToken` |

### `ButtonNavigationTokensOverride`

Responsabilidad: **permitir overrides puntuales sin reemplazar todo el token set**.

Cada propiedad es nullable para que el `merge` conserve el valor base cuando no se envia override.

### `ButtonNavigationTokens.merge(...)`

Responsabilidad: **fusionar tokens base con overrides**.

| Caso | Resultado |
| --- | --- |
| `override == null` | Retorna los tokens base sin cambios. |
| Campo override no nulo | Usa el valor enviado por override. |
| Campo override nulo | Conserva el valor base. |

> Aunque `merge` contiene logica simple, vive en `ButtonNavigationTokens.kt` para mantener la estructura fija de las clases `Tokens` del DS.

## tokens/ButtonNavigationTokensResolver.kt

### `ButtonNavigationTokensResolver`

Responsabilidad: **resolver tokens base del componente**.

| Funcion / Propiedad | Responsabilidad |
| --- | --- |
| `primary` | Atajo composable para tokens `PRIMARY`. |
| `secondary` | Atajo composable para tokens `SECONDARY`. |
| `resolve(...)` | Resuelve tokens base y aplica overrides. |
| `hasLibraryTokens()` | Verifica si las composiciones del DS estan disponibles. |
| `fromLibrary(...)` | Construye tokens desde tokens corporativos y `ButtonTokensResolver`. |
| `fromMaterial(...)` | Fallback visual. Hoy delega en `fromLibrary`. |

### `resolve(...)`

Flujo interno:

```kotlin
val base = tokens ?: when {
    hasLibraryTokens() -> fromLibrary(variant)
    else -> fromMaterial(variant)
}

return base.merge(override)
```

### `fromLibrary(...)`

Responsabilidad: **adaptar tokens de `ButtonMrcy` al boton de navegacion**.

| Dato | Fuente |
| --- | --- |
| Colores y tipografia | `ButtonTokensResolver.resolve(...)` |
| `ButtonVariant` heredado | `ButtonNavigationVariantResolver.resolve(...)` |
| Icono primary | `icons.arrows.keyboardBackspaceRight` |
| Icono secondary | `icons.arrows.keyboardBackspaceLeft` |
| Icono loading | `icons.arrows.sync` |

No debe calcular:

- `pressed`.
- `disabled` visual final.
- `loading` visual final.
- Rotacion del icono.

## tokensrender/ButtonNavigationTokensRender.kt

### `ButtonNavigationTokensRender`

Responsabilidad: **declarar tokens finales listos para renderizar**.

| Token | Uso en render |
| --- | --- |
| `containerColor` | Color del contenedor activo. |
| `contentColor` | Color del texto activo. |
| `disabledContainerColor` | Color del contenedor cuando Material recibe `enabled = false`. |
| `disabledContentColor` | Color del contenido cuando Material recibe `enabled = false`. |
| `borderColor` | Color final del borde. |
| `iconColor` | Color final del icono. |
| `iconToken` | Icono final: normal, loading o `null`. |
| `typographyToken` | Tipografia final. |
| `fontFamilyToken` | Familia tipografica final. |

Este modelo existe para que `ButtonNavigationMrcy` no tenga que calcular colores finales.

## tokensrender/ButtonNavigationTokensRenderResolver.kt

### `ButtonNavigationTokensRenderResolver`

Responsabilidad: **traducir tokens base + estado visual a tokens de render**.

| Funcion | Responsabilidad |
| --- | --- |
| `resolve(...)` | Calcula colores, borde, icono final y tokens tipograficos listos para render. |
| `resolveIconToken(...)` | Decide si se muestra icono normal, icono loading o ningun icono. |

### `resolve(...)`

| Entrada | Uso |
| --- | --- |
| `tokens` | Tokens base resueltos por `ButtonNavigationTokensResolver`. |
| `enabled` | Determina estado visual disabled. |
| `pressed` | Determina estado visual pressed. |
| `isLoading` | Mantiene colores activos aunque el boton de Material este disabled por loading. |
| `canClick` | Evita pintar pressed cuando no se puede hacer click. |
| `showIcon` | Permite ocultar icono sin tocar tokens base. |

Reglas visuales:

| Estado | Container | Content | Icon |
| --- | --- | --- | --- |
| Disabled externo | `disabledContainerColor` | `disabledContentColor` | `disabledContentColor` |
| Pressed clickable | `hoverContainerColor` | `contentPressColor` | `contentPressColor` |
| Default | `containerColor` | `contentColor` | `iconColor` |
| Loading | Mantiene color activo como disabled de Material | Mantiene color activo como disabled de Material | `loadingIconToken` |

> El caso loading es especial: Compose Material recibe `enabled = false` para bloquear clicks, pero visualmente debe conservar los colores activos mientras carga.

## Matriz De Responsabilidad Por Funcion

| Funcion | Capa | Debe hacer | No debe hacer |
| --- | --- | --- | --- |
| `ButtonNavigationMrcy(...)` | Render | Orquestar resolvers, convertir tokens a Compose y renderizar `Button`. | Resolver tokens base, animaciones o estado interno. |
| `ButtonNavigationContent(...)` | Render interno | Pintar icono/texto con datos ya resueltos. | Leer `LocalLibraryIcons` o decidir variantes. |
| `ButtonNavigationBehaviorResolver.resolve(...)` | Behavior resolver | Resolver click, loading, disabled y estado final. | Resolver colores o iconos. |
| `resolveLoadingRotationDegrees(...)` | Behavior resolver | Animar rotacion del icono de loading. | Cambiar tokens o callbacks. |
| `ButtonNavigationVariantResolver.resolve(...)` | Variant resolver | Mapear a `ButtonVariant`. | Crear roles, posiciones o tokens. |
| `ButtonNavigationTokens.merge(...)` | Tokens | Aplicar overrides manteniendo defaults. | Leer Compose state. |
| `ButtonNavigationTokensResolver.resolve(...)` | Tokens resolver | Resolver tokens base y aplicar override. | Resolver pressed/loading final. |
| `hasLibraryTokens()` | Tokens resolver | Detectar composiciones disponibles. | Crear UI. |
| `fromLibrary(...)` | Tokens resolver | Adaptar tokens del DS al componente. | Resolver estado de interaccion. |
| `fromMaterial(...)` | Tokens resolver | Proveer fallback visual. | Duplicar logica de render. |
| `ButtonNavigationTokensRenderResolver.resolve(...)` | Render tokens resolver | Resolver colores e icono finales. | Leer LocalComposition o mapear variantes a ButtonVariant. |
| `resolveIconToken(...)` | Render tokens resolver | Elegir icono normal/loading/null. | Animar o ejecutar callbacks. |
| `ButtonNavigationLoadingScope.stopLoading()` | Behavior support | Finalizar loading local. | Exponer mutable state interno. |

## Ejemplos De Uso

### Boton primario

```kotlin
ButtonNavigationMrcy(
    label = "Continuar",
    variant = ButtonNavigationVariant.PRIMARY,
    onClick = { /* continuar */ },
)
```

### Boton secundario

```kotlin
ButtonNavigationMrcy(
    label = "Volver",
    variant = ButtonNavigationVariant.SECONDARY,
    onClick = { /* volver */ },
)
```

### Submit con icono custom

```kotlin
ButtonNavigationMrcy(
    label = "Submit",
    variant = ButtonNavigationVariant.PRIMARY,
    buttonNavigationTokensOverride = ButtonNavigationTokensOverride(
        iconToken = LocalLibraryIcons.current.general.check,
    ),
    onClick = { /* enviar */ },
)
```

### Loading iniciado por click

```kotlin
ButtonNavigationMrcy(
    label = "Submit",
    variant = ButtonNavigationVariant.PRIMARY,
    buttonNavigationBehavior = ButtonNavigationBehavior.Loading,
    onClick = { /* iniciar carga */ },
    onLoadingClick = {
        // cuando termine la carga:
        stopLoading()
    },
)
```

### Loading controlado desde afuera

```kotlin
ButtonNavigationMrcy(
    label = "Cargando",
    variant = ButtonNavigationVariant.PRIMARY,
    buttonNavigationBehavior = ButtonNavigationBehavior(
        isLoading = true,
    ),
    onClick = { /* accion */ },
)
```

## Checklist Para Cambios Futuros

Antes de modificar `ButtonNavigation`, valida:

| Pregunta | Si la respuesta es "si", toca |
| --- | --- |
| Necesitas agregar una nueva variante publica? | `ButtonNavigationVariant.kt` y `ButtonNavigationVariantResolver.kt` |
| Necesitas heredar otro estilo base? | `ButtonNavigationVariantResolver.kt` |
| Necesitas cambiar colores, tipografia o iconos base? | `ButtonNavigationTokensResolver.kt` |
| Necesitas permitir override de un token nuevo? | `ButtonNavigationTokens.kt` |
| Necesitas cambiar colores por pressed/disabled/loading? | `ButtonNavigationTokensRenderResolver.kt` |
| Necesitas cambiar cuando se bloquea el click? | `ButtonNavigationBehaviorResolver.kt` |
| Necesitas cambiar como se detiene loading? | `ButtonNavigationLoadingScope.kt` |
| Necesitas cambiar solo layout interno? | `ButtonNavigationMrcy.kt` |

## Anti-Patrones

Evitar:

- Crear modelos como `ResolvedVariant` cuando la variante solo hereda `ButtonVariant`.
- Poner logica de loading dentro de `ButtonNavigationBehavior`.
- Resolver colores pressed/disabled dentro de `ButtonNavigationMrcy`.
- Leer `LocalLibraryIcons` fuera de `ButtonNavigationTokensResolver`.
- Mezclar `TokensResolver` con `TokensRenderResolver`.
- Usar `ButtonNavigationTokensOverride` para expresar comportamiento.
- Hacer que `ButtonNavigationContent` sea API publica.

## Resumen

`ButtonNavigationMrcy` debe sentirse simple porque sus capas hacen el trabajo correcto:

| Capa | Pregunta que responde |
| --- | --- |
| **Variant** | Que variante publica pidio el developer? |
| **VariantResolver** | Que `ButtonVariant` base se hereda? |
| **Behavior** | Que comportamiento pidio el developer? |
| **BehaviorResolver** | Como se comporta ahora mismo el boton? |
| **Tokens** | Cuales son los valores visuales base? |
| **TokensResolver** | De donde salen esos tokens base? |
| **TokensRender** | Que valores finales necesita el render? |
| **TokensRenderResolver** | Que color/icono se usa en este estado? |
| **Mrcy** | Como se dibuja el componente final? |
