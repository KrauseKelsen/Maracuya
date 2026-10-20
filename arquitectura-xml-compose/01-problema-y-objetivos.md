# Arquitectura XML–Compose: problema y objetivos

Fecha: 20 de octubre de 2026  
Estado: planteamiento inicial basado en el código del proyecto  
Etapa 1: definición del problema y del resultado esperado

## 1. Propósito

Desarrollar una arquitectura para que los componentes Maracuya puedan configurarse desde XML con claridad, autonomía y coherencia con su API Compose. El trabajo abarcará los recursos Android y la capa `wrappers`, tomando `LabelWrp` y `LabelMrcy` como primer caso de estudio.

Esta carpeta documentará el proceso: diagnóstico, investigación, decisiones, propuestas y validación. Este documento inicia el proceso; no selecciona todavía una solución técnica ni implementa cambios.

## 2. Descripción del problema

El proyecto ya tiene un puente funcional entre XML y Compose, pero ese puente no ofrece una experiencia declarativa suficiente para personalizar componentes desde XML.

En Compose, el consumidor puede entregar un `LabelTokensOverride` directamente a `LabelMrcy`, modificar únicamente las propiedades necesarias y conservar los valores restantes del tema. En XML, `LabelWrp` permite declarar texto y estados, pero la personalización de tokens depende de objetos Kotlin o de un nombre asociado a un registro global.

Para una modificación visual sencilla, el consumidor puede terminar manteniendo una clave, construyendo un override en Kotlin, registrándolo y coordinando su disponibilidad con el uso del layout. La intención visual queda repartida entre archivos y depende de una conexión que no está expresada como un recurso Android tipado.

**El problema central es la ausencia de un contrato declarativo completo y predecible entre recursos XML, atributos del wrapper y tokens del componente Compose.**

La dificultad observada es de desarrollo, mantenimiento y trazabilidad. No se ha medido un problema de rendimiento en ejecución; esa evaluación requeriría evidencia adicional.

## 3. Alcance y ubicación

La arquitectura debe contemplar:

| Área | Responsabilidad por estudiar |
|---|---|
| `res/layout/` | Declaración de componentes, contenido, estados y personalizaciones locales. |
| `res/values/strings.xml` | Contenido textual reutilizable y traducible. |
| `res/values/colors.xml` y `res/color/` | Colores simples y recursos de color con estados. |
| `res/font/` | Archivos de fuentes y familias tipográficas. |
| `res/values/attrs.xml` y estilos en `res/values/` | Contratos de atributos, estilos reutilizables y valores de tema. |
| `wrappers/` | Lectura, resolución y adaptación de la configuración hacia Compose. |

En Android, `string` y `color` son tipos de recursos; no todas esas categorías corresponden a carpetas con el mismo nombre. Las cadenas se declaran habitualmente en `res/values/strings.xml`, mientras que las fuentes sí tienen su directorio `res/font/`.

El alcance inmediato es documentar y estudiar. La implementación y la migración de componentes pertenecen a etapas posteriores.

## 4. Caso comparativo: una etiqueta de texto

Se usará una intención común: mostrar «Nombre completo» y personalizar color, tamaño y familia tipográfica.

Los siguientes ejemplos son ilustrativos y omiten imports. Los recursos `nombre_completo` y `label_color` son nombres propuestos para el ejemplo, no recursos creados por este documento. `mulish_regular` sí existe en la biblioteca.

### 4.1. Android Views: `TextView` en XML

```xml
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/nombre_completo"
    android:textColor="@color/label_color"
    android:textSize="16sp"
    android:fontFamily="@font/mulish_regular" />
```

La intención visual queda declarada en el layout mediante atributos y referencias a recursos. Para esta configuración estática, el consumidor no necesita registrar un objeto Kotlin ni coordinar una clave global.

La referencia de fuente se asigna mediante `android:fontFamily`; `android:font` se utiliza en declaraciones de fuentes dentro de una familia y no debe presentarse como el atributo equivalente de `TextView`.

### 4.2. Maracuya: `LabelWrp` en XML, API actual

```xml
<cruxui.android.maracuya.wrappers.components.labels.LabelWrp
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:text="@string/nombre_completo"
    app:error="false"
    app:labelTokensOverride="nombre_completo_override" />
```

Este XML expresa el contenido y el nombre del override. La personalización visual debe construirse en Kotlin, por ejemplo:

```kotlin
private const val NOMBRE_OVERRIDE = "nombre_completo_override"

fun registrarEstiloNombre() {
    LabelTokensOverrideRegistry.register(NOMBRE_OVERRIDE) {
        colors, typography, _, _ ->
        LabelTokensOverride(
            foregroundDefault = colors.fgDefault,
            labelTypography = typography.label.copy(fontSize = 16f),
            fontFamily = FontFamilyToken.MULISH,
        )
    }
}
```

Este ejemplo usa roles del tema y una familia del sistema de diseño; no representa todavía una lectura de `@color/label_color` o `@font/mulish_regular` desde el layout.

El consumidor debe invocar el registro antes de que el componente resuelva esa clave y definir quién mantiene o retira ese registro. La función auxiliar y la constante son decisiones de organización, no requisitos sintácticos de la API: la dependencia del registro es lo que introduce la coordinación adicional.

También existe una alternativa programática: asignar `labelTokensOverride` directamente a una instancia de `LabelWrp`. Evita la búsqueda por nombre, pero sigue dejando la configuración fuera del XML.

**Limitación verificada:** el parser actual de `LabelWrp` no traduce `android:textColor`, `android:textSize` ni `android:fontFamily` a los tokens del texto Compose. Agregar esos atributos al layout no establece por sí mismo ese puente.

### 4.3. Jetpack Compose: `Text`

```kotlin
@Composable
fun NombreConText() {
    Text(
        text = stringResource(R.string.nombre_completo),
        color = colorResource(R.color.label_color),
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.mulish_regular)),
    )
}
```

`Text` recibe directamente las propiedades visuales. Los recursos Android pueden participar mediante sus adaptadores Compose. La configuración queda junto a la invocación del componente.

### 4.4. Maracuya en Compose: `LabelMrcy`

```kotlin
@Composable
fun NombreConLabelMrcy() {
    val colors = LocalLibraryColorTokens.current
    val typography = LocalLibraryTypography.current

    LabelMrcy(
        text = stringResource(R.string.nombre_completo),
        labelTokensOverride = LabelTokensOverride(
            foregroundDefault = colors.fgDefault,
            labelTypography = typography.label.copy(fontSize = 16f),
            fontFamily = FontFamilyToken.MULISH,
        ),
    )
}
```

Este ejemplo supone un tema que provee los tokens de la biblioteca. La personalización se entrega directamente al componente, sin registrar un nombre global. `LabelTokensResolver` combina el override parcial con los tokens base.

`LabelMrcy` expresa la personalización mediante tokens del sistema de diseño, mientras que `Text` acepta propiedades Compose. No tienen APIs idénticas: `LabelMrcy` también incorpora estado de error, texto opcional e icono.

La arquitectura XML debe preservar esa semántica y hacerla accesible declarativamente, sin obligar al consumidor a reconstruir manualmente el puente.

### 4.5. Resumen de la comparación

| Capacidad | `TextView` XML | `LabelWrp` XML actual | `Text` Compose | `LabelMrcy` Compose |
|---|---|---|---|---|
| Declarar contenido | `android:text` | `app:text` | `text` | `text` |
| Personalizar color | Atributo y recurso | Override Kotlin, directo o registrado | Parámetro `color` | `LabelTokensOverride` |
| Personalizar fuente | `android:fontFamily` | Token Kotlin de familia | Parámetro `fontFamily` | Token `fontFamily` |
| Personalizar tamaño | `android:textSize` | Token tipográfico Kotlin | Parámetro `fontSize` | Token `labelTypography` |
| Conexión por nombre global para el ejemplo | No | Sí, con la vía XML actual | No | No |
| Conservar semántica Maracuya | No incorporada | Sí, mediante `LabelMrcy` | No incorporada | Sí |

## 5. Diagnóstico del puente actual

El flujo observado es:

```text
Layout XML
  → LabelAttributeParser
  → LabelAttributes
  → estado de LabelWrp
  → override directo o búsqueda en LabelTokensOverrideRegistry
  → LabelMrcy
  → LabelTokensResolver
  → adaptadores y render Compose
```

Hay una base aprovechable: parser separado, modelo de atributos, estado observable, tema para wrappers y resolución central de tokens. La nueva arquitectura debe evaluar cómo extender o reorganizar estas responsabilidades.

Los puntos que motivan el cambio son:

1. **Personalización repartida.** El layout declara una clave, mientras que Kotlin define su significado visual.
2. **Dependencia global.** El registry almacena providers por nombre; el consumidor coordina su registro, reemplazo y retirada.
3. **Errores silenciosos.** Una clave inexistente devuelve `null`; el resolver continúa sin ese override, lo que puede ocultar un error de configuración.
4. **Recursos sin un puente visual suficiente.** El parser admite texto mediante `getString`, pero no convierte recursos de color o fuente en personalizaciones del label.
5. **Familias restringidas por el modelo actual.** `FontFamilyToken` es un enum con `MULISH` y `DAVIVIENDA`; admitir una referencia arbitraria `@font/...` requiere estudiar el modelo y sus adaptadores.
6. **Contrato de estilos incompleto por estudiar.** El parser utiliza `obtainStyledAttributes(attrs, styleable)`, pero no recibe el `defStyleAttr` del constructor. Debe definirse expresamente la resolución de estilos y defaults.

Existe además un límite importante: `android:background` y `android:foreground` pertenecen a la View contenedora y no equivalen a `foregroundDefault` del texto. Su posible efecto sobre el contenedor no demuestra que exista una conversión hacia tokens Compose. La arquitectura debe especificar qué superficie modifica cada atributo.

### Evolución prevista de `LabelMrcy`: texto libre y variantes

Actualmente, `LabelMrcy` escribe `"(Opcional)"` como literal interno. Este caso debe contemplarse como una variante del componente en la arquitectura futura, además de considerar su localización mediante recursos de cadenas.

`LabelMrcy` evolucionará para ofrecer un uso equivalente al de un `TextView` para mostrar texto libre, junto con variantes que permitan representar labels predefinidos como «Opcional». La arquitectura deberá contemplar las responsabilidades de `variant`, `varianttokens` y `render`:

- **`variant`:** expresa la variante del label, distinguiendo el uso de texto libre de los labels con una intención y contenido predefinidos, como «Opcional».
- **`varianttokens`:** define la configuración visual correspondiente a cada variante y su relación con el tema y los overrides parciales.
- **`render`:** representa el label a partir de la variante, el contenido resuelto y sus tokens.

Los labels predefinidos podrán resolver su contenido desde recursos de cadenas; que una variante represente «Opcional» no implica mantener ese texto hardcodeado en Kotlin. La estrategia de cadenas deberá contemplar tanto el texto proporcionado por el consumidor como el contenido propio de cada variante.

El puente XML–Compose deberá permitir seleccionar esas variantes declarativamente y resolver sus tokens sin exigir un registro Kotlin por cada uso. En etapas posteriores se definirá cómo migrar el comportamiento actual de `optionalText`, que agrega un texto secundario, hacia esta organización de variantes. Los nombres definitivos de las APIs, las reglas de composición y la estructura de archivos quedan pendientes de diseño.

## 6. Objetivo general

Diseñar una arquitectura declarativa y extensible para los componentes XML de Maracuya, configurable desde layouts y Kotlin, con variantes por defecto, tokens personalizables y una capa de comportamiento. La lógica de recursos y del contrato XML debe ser independiente de Compose, con puentes explícitos hacia el núcleo Kotlin compartido y el renderizado Compose, para reutilizar recursos y reglas sin duplicarlos y sostener una estructura escalable y mantenible.

## 7. Objetivos específicos

1. Permitir personalizaciones visuales comunes desde XML sin un registro Kotlin por cada estilo.
2. Integrar recursos de cadenas, colores y fuentes, así como estilos y atributos de tema.
3. Conservar overrides parciales: cambiar una propiedad debe mantener las demás provenientes del tema o de la configuración base.
4. Definir la precedencia entre tema, estilos, atributos locales y actualizaciones programáticas.
5. Separar lectura de recursos, validación, resolución semántica, adaptación y renderizado.
6. Distinguir propiedades del contenedor View de propiedades internas del componente Compose.
7. Producir diagnósticos útiles ante recursos, referencias o combinaciones no admitidas.
8. Reducir duplicación entre wrappers mediante contratos compartidos donde corresponda.
9. Mantener estados, accesibilidad, localización y adaptación a cambios de configuración.
10. Permitir una migración gradual desde los wrappers y registros existentes.
11. Establecer una variante `Default` por componente que determine su apariencia base, con tokens estables y personalizables; representar labels predefinidos, como «Opcional», mediante variantes.
12. Incorporar una capa `behavior` independiente de `variant` y de los tokens visuales. Debe resolver capacidades y comportamiento ante estados como `error` y `disabled`, coordinando funciones básicas del paquete `core` Kotlin. Un label pasivo puede usar un comportamiento mínimo, sin imponerle validación o interacción innecesarias.
13. Compartir capacidades de configuración entre componentes compatibles: texto, color, fuente y tipografía deben poder modificarse mediante atributos XML y setters Kotlin que actualicen la misma configuración observable.
14. Permitir overrides parciales con colores y tipografías del tema activo, recursos Android o valores explícitos, conservando una regla documentada para su relación con la variante y los estados.
15. Separar la resolución de recursos Android y las APIs del wrapper de la integración Compose. Compartir contratos y reglas puras de `core`, manteniendo `Context`, `TypedArray`, `CompositionLocal` y tipos de renderizado en sus respectivos adaptadores.
16. Reutilizar cadenas, colores, fuentes, estilos y tokens existentes en la medida de lo posible. Los puentes XML–Compose deben evitar mantener dos definiciones manuales del mismo tema y permitir extender componentes sin multiplicar registros globales ni lógica duplicada.

La API prevista debe admitir usos como `setColor(R.color.label_color)` y `setFont(R.font.mulish_regular)`, con tipos de recurso explícitos. Su equivalente declarativo será `android:textColor="@color/label_color"` y `android:fontFamily="@font/mulish_regular"`; `android:setColor` no es un atributo Android válido. Los atributos propios usarán el namespace `app:`.

El wrapper actual hereda de `AbstractComposeView`, no de `TextView`. Por tanto, ofrecer capacidades familiares de un componente Android requiere implementarlas y conectarlas explícitamente; no implica heredar automáticamente todos los métodos de `TextView`. La separación y el alcance de esa compatibilidad se desarrollan en la [propuesta de arquitectura](02-propuesta-arquitectura.md).

La organización Compose —tokens, overrides, children, variantes, resolvers y render— es una referencia de responsabilidades. La organización XML deberá justificarse por sus propias necesidades de recursos, estilos, inflación y ciclo de vida.

## 8. Criterios iniciales de aceptación

La propuesta posterior deberá demostrar, con un prototipo de label, que:

- Un layout puede declarar texto, color y fuente mediante recursos sin registrar un override específico en Kotlin.
- Un estilo reutilizable puede aplicarse a dos labels sin duplicar configuración programática.
- Cambiar solamente el color conserva las otras propiedades resueltas del componente.
- La precedencia produce un resultado documentado y verificable cuando dos fuentes configuran la misma propiedad.
- El estado de error tiene una relación explícita con el color configurado y con el tema.
- Una actualización programática se refleja en Compose conforme al contrato definido.
- Los cambios de tema y configuración resuelven los recursos según el comportamiento acordado.
- El resultado visual de XML y Compose es equivalente cuando ambos expresan la misma configuración semántica.
- La migración tiene un tratamiento definido para los nombres de registry usados actualmente.
- Sin personalización, el componente usa su variante `Default` y los tokens del tema activo.
- XML y setters Kotlin equivalentes llegan a la misma resolución, sin estados visuales independientes que puedan divergir.
- `behavior` coordina operaciones de `core` y bloquea las acciones deshabilitadas, sin decidir colores o tipografías.
- La combinación de variante, error, deshabilitado y overrides tiene un resultado verificable, tanto visual como funcional.
- Cambiar la integración de fuentes o recursos no exige modificar las reglas puras de comportamiento.

Se comparará el esfuerzo del consumidor usando el mismo caso antes y después: archivos que debe editar, declaraciones auxiliares, pasos de inicialización y posibilidades de error. No se fijan todavía metas numéricas ni conclusiones de rendimiento.

## 9. Preguntas para la etapa 2

La recopilación inicial de fuentes y la propuesta se documentan por separado. Sigue pendiente su evaluación mediante prototipo. La investigación deberá responder:

- ¿Qué atributos Android conviene reutilizar y cuáles necesitan un contrato propio?
- ¿Cómo representar roles semánticos y recursos concretos sin ambigüedad?
- ¿Cómo resolver colores con estados y asociarlos a los estados del componente?
- ¿Cómo representar fuentes Android y familias del sistema de diseño en un modelo compatible?
- ¿Qué mecanismos de estilos y tema satisfacen la precedencia requerida?
- ¿Qué resolver durante la inflación y qué resolver dentro de la composición?
- ¿Qué responsabilidades pueden compartirse entre wrappers?
- ¿Cómo verificar previews, localización, accesibilidad y cambios de configuración?

Estas preguntas delimitan la investigación; no constituyen decisiones de implementación.

## 10. Evidencia local revisada

Rutas relativas a la raíz del repositorio:

- `maracuya/src/main/res/values/attrs.xml`: contrato XML actual de `LabelWrp`.
- `maracuya/src/main/res/layout/label_wrp_preview.xml`: consumo XML y referencia `mytokensoverride`.
- `maracuya/src/main/java/cruxui/android/maracuya/wrappers/components/labels/`: wrapper, parser, modelo y registry.
- `maracuya/src/main/java/cruxui/android/maracuya/wrappers/core/MrcyXmlComposeView.kt`: integración con Views y tema.
- `maracuya/src/main/java/cruxui/android/maracuya/ui/components/labels/`: componente, tokens y resolución.
- `maracuya/src/main/java/cruxui/android/maracuya/ui/previews/LabelPreview.kt`: ejemplo real de registro y retirada del override.
- `maracuya/src/main/java/cruxui/android/maracuya/tokens/base/`: modelos de color, tipografía y familia.
- `maracuya/src/main/res/font/`: fuentes disponibles en la biblioteca.

El diagnóstico inicial se basa en lectura del código. Los ejemplos no se han incorporado a la aplicación ni ejecutado como pruebas. Las fuentes externas y las decisiones propuestas se registran en [referencias](../referencias-arquitectura-xml/README.md) y en la [propuesta de arquitectura](02-propuesta-arquitectura.md).
