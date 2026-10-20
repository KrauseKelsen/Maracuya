# Fundamentos de la propuesta

Consulta: 20 de octubre de 2026. Referencias primarias gratuitas seleccionadas para la [propuesta XML–Compose](../arquitectura-xml-compose/02-propuesta-arquitectura.md).

## Investigación científica

### Parnas — descomposición modular

D. L. Parnas (1972), *On the Criteria To Be Used in Decomposing Systems into Modules*. Communications of the ACM, 15(12), 1053–1058. [Texto completo institucional](https://www.cs.lafayette.edu/~gexia/cs301/resources/parnas.html) · [DOI](https://doi.org/10.1145/361598.361623).

Compara descomposición por etapas de procesamiento con módulos que ocultan decisiones susceptibles de cambiar. Su ejemplo permite razonar sobre propagación de cambios. Aplicación propuesta: encapsular carga de fuentes, lectura XML y renderizado detrás de contratos, en vez de hacer que cada wrapper conozca todos los detalles.

Límite: no estudia Android ni demuestra que aumentar el número de capas mejore siempre el sistema.

### FODA — aspectos comunes y variables

Kyo C. Kang, Sholom G. Cohen, James A. Hess, William E. Novak y A. Spencer Peterson (1990), *Feature-Oriented Domain Analysis (FODA) Feasibility Study*, CMU/SEI-90-TR-021. [Ficha SEI](https://www.sei.cmu.edu/library/feature-oriented-domain-analysis-foda-feasibility-study/) · [PDF gratuito](https://www.sei.cmu.edu/documents/1011/1990_005_001_15872.pdf).

Modela características comunes, opcionales y alternativas de familias de sistemas; incluye un ejemplo de administradores de ventanas. Aplicación propuesta: compartir capacidades como texto/color/fuente sin imponer edición, validación o carga a todos los componentes.

Límite: es un informe de factibilidad; no equivale a una validación experimental de nuestra biblioteca. El propio informe señala trabajo pendiente de modelado arquitectónico y validación.

### Harel — comportamiento y estados

David Harel (1987), *Statecharts: A Visual Formalism for Complex Systems*. Science of Computer Programming, 8, 231–274. [PDF gratuito del autor](https://www.weizmann.ac.il/math/harel/sites/math.harel/files/users/user50/Statecharts.pdf).

Presenta un formalismo para comportamiento reactivo con jerarquía y concurrencia de estados. Aplicación propuesta: modelar `enabled` y `error` como dimensiones del estado, independientes de una variante visual como `Optional`.

Límite: no prescribe nuestra precedencia de estados ni exige un framework de máquinas de estados para un label. Para el primer componente bastan datos y funciones de transición explícitas.

## Contratos técnicos de Android

| Fuente oficial | Contrato usado en la propuesta |
|---|---|
| [Crear una View personalizada](https://developer.android.com/develop/ui/views/layout/custom-views/create-view) | Atributos declarados, resolución con `TypedArray` y APIs para actualizaciones posteriores. |
| [Resources.Theme](https://developer.android.com/reference/android/content/res/Resources.Theme) | Prioridad de atributos, `style`, estilo por defecto y tema; `defStyleRes` como fallback cuando no se resuelve `defStyleAttr`. |
| [TextView](https://developer.android.com/reference/android/widget/TextView) | `setTextColor(Int)` recibe ARGB, no un identificador `R.color`; también existe una sobrecarga para `ColorStateList`. |
| [ColorStateList](https://developer.android.com/reference/android/content/res/ColorStateList) | Selección de color según estados; se debe preservar la lista al adaptar recursos. |
| [Fuentes XML](https://developer.android.com/develop/ui/views/text-and-emoji/fonts-in-xml) | Recursos de fuente y familias reutilizables desde XML. |
| [Compose dentro de Views](https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/compose-in-views) | Integración y disposición de la composición según el ciclo de vida del host. |
| [State holders](https://developer.android.com/topic/architecture/ui-layer/stateholders) | Estado explícito y flujo unidireccional; un holder puede ser una clase simple. |

La [recopilación de design systems](02-design-systems-android-xml.md) aporta además Material y Backpack como antecedentes de estilos y tokens públicos. Estas referencias justifican principios y mecanismos. La combinación concreta de capas, nombres de API y prioridades propuesta para Maracuya es una decisión de diseño que todavía debe probarse.
