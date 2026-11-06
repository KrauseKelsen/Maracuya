# Componentes XML independientes y responsabilidades compartidas

El catálogo de `theme/LibraryThemes` sigue siendo la fuente del DS. La exportación a recursos Android y la
resolución de recetas durante la ejecución son procesos separados. Un componente nuevo no debe modificar un
registro global, agregar condiciones por tipo en el núcleo ni depender del TextView del DS.

## Capas implementadas

| Capa | Responsabilidad | No debe conocer |
|---|---|---|
| `token-exporter/catalog` | Leer las fuentes puras y entregar un catálogo tipado | Views, eventos, registros |
| `token-exporter/android`, `kotlin` | Escribir una familia de recursos o enlaces | Componentes concretos |
| `token-exporter/components/text` | Estilos opcionales de widgets Android con texto | Recetas o eventos |
| `wrappers/core/registry` | Almacenar/consultar recetas tipadas | Android, Compose, comportamiento |
| `wrappers/core/tokens` | Conservar fuentes de valores y vincular tema/receta por instancia | Renderizadores, componentes |
| `wrappers/core/resources` | Convertir fuentes a valores Android | Variantes, listeners, estados de negocio |
| `wrappers/core/behavior` | Gestos opcionales sobre cualquier View | Tokens o políticas de selección |
| `wrappers/capabilities/text` | Leer, combinar, resolver y aplicar tipografía | Tokens o variantes de TextView/Button |
| `wrappers/capabilities/compounddrawables` | Dibujo, celdas, disposición intrínseca, geometría y acciones de iconos relativos | Tema o recetas |
| `wrappers/adapters/compose` | Adaptar locals y medidas Android para wrappers Compose | Componentes concretos |
| `wrappers/components/<componente>` | Definir atributos, variantes, tokens, comportamiento y coordinación propios | Internos de otro componente |

Los archivos históricos de `core/behavior/CompoundDrawableBehaviorController` conservan una fachada de
compatibilidad Kotlin/Java. La implementación opcional está en `capabilities/compounddrawables`.
`AndroidTokenAdapter` también conserva su API y delega a cargadores separados.
Los nombres públicos `TextColor`, `TextFont` y `TextDimension` se conservan: describen fuentes reutilizables,
no una dependencia de la clase TextView del DS. Sus modelos ya no cargan recursos.

## Decisiones SOLID

- Responsabilidad única: los lectores decodifican; los resolutores convierten; los renderizadores escriben;
  behavior controla interacciones. El punto de entrada del exportador solo coordina.
- Abierto a extensión: cada `CatalogWriter` produce su propia salida. Una adaptación opcional no modifica los
  escritores generales. Cada componente declara su registro con su tipo de override.
- Sustitución: cualquier `TokensOverrideSource<T>` que cumpla consulta/resolución puede alimentar el vínculo;
  cualquier `ExportOutput` puede recibir la misma exportación. Las pruebas usan salida en memoria.
- Segregación: los consumidores de recetas reciben un contrato de lectura, no operaciones de registro/borrado.
  Un componente sin texto no incorpora la capacidad tipográfica ni sus atributos.
- Inversión de dependencias: `ComponentTokenBinding<T>` recibe la fuente de recetas y una función que obtiene
  el tema heredado. No consulta una View ni un CompositionLocal. El adaptador de cada plataforma aporta esos datos.

No se añadió una superclase que imponga texto, iconos, error, selección y eventos a todos los componentes.
Cada uno compone sus capacidades. Esto evita una jerarquía central con decenas de opciones irrelevantes.

## Incorporar otro componente

Una estructura de componente nativo mantiene el mismo patrón del TextView:

```text
components/buttons/
  Button.kt                 # API nativa, setters y ciclo de vida
  attributes/               # Styleable propio y decodificación
  core/                     # Estado exclusivo del Button
  tokens/                   # Override, registro y resolución de capas
  tokensrender/             # Valores Android para ese Button
  render/                   # Aplicación de valores a su View
  variant/                  # Variantes visuales del Button
  behavior/                 # Capacidades y reglas de interacción propias
```

1. Definir tokens, defaults y variantes semánticos independientes del frontend. Cuando existe una implementación
   Compose, sus defaults deben compartirse con el adaptador nativo, como ocurre con el núcleo actual del TextView.
2. Declarar un registro `TokensOverrideRegistry<ButtonTokensOverride>`. El mismo nombre de receta puede existir
   en otros registros; sus tipos y almacenamiento permanecen separados.
3. En una View nativa usar `ComponentTokenBinding` con ese registro y un proveedor de tema. Releer el ámbito al
   adjuntarse/cambiar configuración y al recibir `refreshTheme`. Cambiar una receta no notifica automáticamente.
4. Si el componente tiene texto, componer `NativeTextStyleOverrides`; declarar sus propios índices mediante
   `TextAttributeIndices` y usar el lector, resolutor y renderer tipográfico. No copiar `TextViewAttributeReader`.
   El modelo público `NativeTextStyleOverrides` permite exponer una propiedad `textStyle` en nuevos overrides.
   El TextView conserva sus campos planos públicos mediante adaptación para no romper consumidores existentes.
5. Mantener colores de estado, precedencia específica, variantes y behavior en el componente. No todo componente
   tiene error, selección, dos iconos o el mismo orden de estados. Los helpers compartidos no imponen esas decisiones.
6. Incorporar gestos, superficies o drawables relativos solo cuando corresponda. `ViewGestureController` recibe
   una política `active`; la selección del texto se decide en el behavior de TextView.
7. Para un wrapper que aloja Compose, conservar `MrcyXmlComposeView` y resolver recetas mediante
   `resolveInComposition`. Ese adaptador lee `LibraryTheme.current`; el registro sigue siendo independiente.

`ButtonWrp` existente conserva el render Compose. Sus recetas ya pasan por el adaptador común, al igual que las
recetas de navegación, Label y TextField. Esta refactorización no lo convierte en un Button nativo ni migra los
63 componentes por anticipado. Establece capacidades y contratos reutilizables para implementarlos sin depender
entre sí.

## Consumo existente de Button desde XML

Registrar antes de inflar, por ejemplo dentro de `Application.onCreate`:

```kotlin
ButtonTokensOverrideRegistry.register("accionPrincipal") { library: LibraryThemeSnapshot ->
    ButtonTokensOverride(
        contentColor = library.colors.fgDefault,
        textTypography = library.typography.headline24,
        fontFamilyToken = library.fontFamily,
    )
}
```

Dentro de un `MyLibraryThemeWrp` y un layout que declare los namespaces Android y app:

```xml
<cruxui.android.lib.wrappers.components.buttons.simple.ButtonWrp
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:text="Continuar"
    app:buttonTokensOverride="accionPrincipal" />
```

Este consumo no necesita nuevas variables en `token-exporter` ni utiliza tokens de TextView. La receta puede
ser una función compartida con el consumidor Compose, que le pasa `LibraryTheme.current`.

## Límites y verificación

El exportador trabaja con temas conocidos al construir; las recetas también admiten bibliotecas configuradas
en ejecución. Cambiar una biblioteca no crea nuevos IDs Android ni sustituye el Context de una View.

Las pruebas cubren recursos y estilos, cambios de ámbito, independencia de registros de tipos diferentes,
render tipográfico sobre `android.widget.Button`, gestos sobre una View sin texto y regresiones del TextView.
Las reglas de dependencias impiden importar componentes concretos desde núcleo, capacidades o adaptadores.

Las medidas comunes de TextView viven en `core/components/textviews/tokens/TextViewLayoutTokens`; el mínimo interactivo reutilizable vive en `tokens/spacings/InteractionSpacings`. Ver [paridad de render](07-paridad-textview-compose-xml.md) para la adaptación de iconos nativos sin alterar los defaults Compose.
