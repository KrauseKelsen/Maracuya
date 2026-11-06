# XML y Compose: problema y objetivos vigentes

Actualizado: 2 de noviembre de 2026. Implementación de referencia: TextView.

El DS define colores, tipografías, fuentes e iconos en las clases `Library*`. Compose los recibe mediante `CompositionLocal`; XML necesita recursos Android y un proveedor de tema que funcione fuera de una composición. Mantener dos catálogos produciría diferencias al modificar una marca.

La solución implementada comparte el catálogo, los contratos puros y las decisiones visuales. `token-exporter` deriva recursos XML durante el build. `LibraryThemeSnapshot` transporta el tema resuelto a ambos frontends. Las recetas de `TokensOverride` reciben ese mismo contrato.

## Objetivos

- Modificar cada token en su Library y regenerar sus referencias al compilar.
- Permitir referencias XML a colores, TextAppearances, fuentes e iconos, además de recetas tipadas.
- Conservar los valores y comportamientos de Compose como referencia para el TextView nativo.
- Compartir separación de iconos y tamaño interactivo; diferenciar esas medidas del padding explícito del host.
- Mantener núcleo, capacidades opcionales y componentes independientes para incorporar nuevos controles.
- Documentar precedencia, cambios de tema y límites de cada API; comprobarlos con pruebas de recursos, comportamiento y render real.

## Alcance actual

`wrappers/components/textviews/TextView` hereda de `AppCompatTextView`. Los wrappers de Button, Label y TextField que alojan Compose siguen usando su adaptador de composición; no se han convertido en controles nativos.

El TextView conserva texto y spans, selección, setters Android y drawables relativos. Comparte con Compose tokens, variantes, colores por estado y medidas base. Las diferencias propias de plataforma, como los menús de selección o la presentación de acciones accesibles, se delegan a Android y Compose respectivamente.

La propuesta inicial basada en Label fue sustituida por esta implementación. La arquitectura vigente está en [estructura](02-propuesta-arquitectura.md), el manual en [TextView](03-textview-implementado.md), la extensión a otros componentes en [responsabilidades](04-escalabilidad-y-responsabilidades.md) y el contrato visual en [paridad](07-paridad-textview-compose-xml.md).
