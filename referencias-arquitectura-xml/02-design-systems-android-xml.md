# Referencias gratuitas de sistemas Android con XML

Consulta: 20 de octubre de 2026. Fuentes técnicas primarias: documentación oficial y código público.

## 1. Material Components for Android — Google

**Qué arquitectura muestra:** componentes basados en Views, configuración con recursos y atributos de tema, estilos tipográficos reutilizables y adaptación del contexto mediante overlays.

Fuentes:

- [Guía de temas](https://github.com/material-components/material-components-android/blob/master/docs/theming.md).
- [Tipografía y TextAppearance](https://github.com/material-components/material-components-android/blob/master/docs/theming/Typography.md).
- [Implementación de MaterialTextView](https://github.com/material-components/material-components-android/blob/master/lib/java/com/google/android/material/textview/MaterialTextView.java).

La documentación tipográfica vincula roles del tema con estilos `TextAppearance`, configurables mediante recursos de fuente y propiedades de texto. Eso permite cambiar una apariencia reutilizable desde XML.

`MaterialTextView` hereda de `AppCompatTextView`. Su constructor usa `android.R.attr.textViewStyle` como atributo de estilo por defecto y envuelve el contexto con `MaterialThemeOverlay`. Su resolución de altura de línea comprueba atributos de la vista antes de usar el `TextAppearance`.

**Aplicación a estudiar en Maracuya:** resolver estilos y recursos antes de convertirlos a tokens. El mecanismo de resolución puede inspirar al wrapper, aunque heredar de una View de texto no es equivalente a renderizar `LabelMrcy` dentro de Compose.

**Límite:** este caso no demuestra una conversión automática de cualquier atributo XML a cualquier token Compose.

## 2. Backpack Android — Skyscanner

**Qué arquitectura muestra:** APIs para Android Views y Compose, con artefactos separados y tokens expuestos de forma idiomática en cada tecnología.

Fuente principal: [repositorio y documentación de Backpack Android](https://github.com/Skyscanner/backpack-android).

El README documenta:

- `backpack-android` para Views y `backpack-compose` para Compose.
- Tema `BpkTheme` en ambas superficies.
- Tokens públicos como recursos Android: colores, colores semánticos, dimensiones y estilos de texto.
- Tokens Compose mediante su paquete correspondiente y propiedades del tema.
- Una aplicación de demostración para consultar variantes y usos.

**Aplicación a estudiar en Maracuya:** exponer una superficie de recursos XML para los conceptos del sistema de diseño, en lugar de obligar a que todos se consuman como objetos Kotlin.

**Límite:** la coexistencia de ambas APIs no demuestra que la implementación XML use Compose internamente. Tampoco prueba, por sí sola, que toda configuración se genere desde una única fuente.

## 3. Fluent UI Android — Microsoft

**Qué arquitectura muestra:** módulos agrupados por familias de controles, con controles XML v1 y controles Compose v2 identificados por separado.

Fuentes:

- [Repositorio, catálogo y modularización](https://github.com/microsoft/fluentui-android).
- [Guía oficial de integración de Fluent](https://fluent2.microsoft.design/get-started/develop).

El repositorio describe módulos como `fluentui_core`, `fluentui_persona`, `fluentui_listitem` y `fluentui_drawer`. Permite consumir módulos individuales y documenta un uso XML de `AvatarView` con atributos propios.

**Aplicación a estudiar en Maracuya:** separar infraestructura común de familias de componentes y documentar qué contrato soporta cada superficie.

**Límite importante para nuestro caso:** el catálogo consultado ubica `Label` entre los controles Compose v2. No se presenta ese `Label` como ejemplo de un label XML ni se supone paridad automática entre v1 y v2.

## 4. Referencias complementarias

### Paris — Airbnb

[Repositorio y documentación](https://github.com/airbnb/paris).

Es una biblioteca de estilos, no un sistema de diseño completo. Permite definir y aplicar estilos a Views programáticamente, usar estilos XML y generar APIs mediante anotaciones.

**Interés para el proyecto:** estudiar cómo reducir código repetitivo de configuración y ofrecer APIs coherentes para XML y Kotlin. No resuelve por sí sola el puente a los tokens Compose de Maracuya.

### MDC-Android Compose Theme Adapter — antecedente histórico

[Documentación original y aviso de deprecación](https://material-components.github.io/material-components-android-compose-theme-adapter/).

Documenta la conversión de temas XML de Material a temas Compose, incluyendo colores, tipografía y formas. Es un antecedente directo de adaptación entre tecnologías.

**Estado:** la página declara deprecados estos artefactos y describe su traslado a Accompanist. Se incluye para estudiar el mecanismo; no como recomendación de dependencia vigente.

**Límite:** adaptar un tema no equivale a adaptar todos los atributos locales, estados y variantes de cada componente.

### Contrato de Android Views — documentación de plataforma

- [Crear una View personalizada](https://developer.android.com/develop/ui/views/layout/custom-views/create-view): atributos personalizados, `declare-styleable`, lectura mediante `TypedArray` y aplicación de propiedades.
- [Estilos y temas](https://developer.android.com/develop/ui/views/theming/themes): estilos reutilizables, temas y relación entre mecanismos de configuración.

Estas fuentes sustentan el contrato XML que debe respetar una biblioteca. No describen por sí mismas la organización interna de Maracuya.

## 5. Comparación para orientar la investigación

Las siguientes relaciones con Maracuya son inferencias de trabajo a partir de las fuentes anteriores; todavía no son decisiones de arquitectura.

| Referencia | Mecanismo observado | Pregunta para Maracuya |
|---|---|---|
| Material | Estilos, atributos de tema y resolución dentro del componente View | ¿Cómo convertir esa configuración resuelta a overrides parciales? |
| Backpack | Tokens disponibles como recursos Android y API Compose | ¿Qué recursos XML públicos deben representar nuestros tokens semánticos? |
| Fluent | Módulos de controles y superficies XML/Compose diferenciadas | ¿Qué infraestructura compartir y qué contratos mantener separados? |
| Paris | Generación y aplicación de estilos | ¿Qué repetición de los wrappers puede automatizarse? |
| Theme Adapter | Traducción de tema XML a tema Compose | ¿Cómo separar adaptación global del tema y configuración local del componente? |

Una hipótesis a contrastar es este flujo:

```text
Recursos + tema + estilo + atributos XML
                  ↓
        Configuración resuelta y validada
                  ↓
         Variante + tokens parciales
                  ↓
          Resolución del componente
                  ↓
               Render Compose
```

Este flujo es una propuesta de estudio propia. No se atribuye como arquitectura literal a ninguno de los sistemas citados.

## 6. Relación con el futuro LabelMrcy

Para el componente de texto libre y sus variantes, como «Opcional», estudiar tres responsabilidades:

1. **Contenido:** texto del consumidor o recurso de cadena de una variante predefinida.
2. **Apariencia:** tipografía, fuente y colores, con valores base y personalización parcial.
3. **Renderizado:** representación Compose de la configuración resultante.

Material aporta evidencia sobre apariencia tipográfica y resolución XML; Backpack, sobre exposición de tokens como recursos. La propuesta de `variant`, `varianttokens` y `render` sigue siendo una decisión del proyecto, pendiente de diseño y validación.

## 7. Orden sugerido de lectura

1. `MaterialTextView` y la guía de `TextAppearance`, por su cercanía al caso de texto.
2. Backpack, para estudiar una API de tokens accesible desde XML.
3. Theme Adapter, para comprender las fronteras de la traducción XML–Compose.
4. Fluent y Paris, para evaluar modularidad y reducción de código repetitivo.

La investigación posterior deberá fijar versiones, examinar código de componentes concretos y comparar precedencia, estados, fuentes y variantes mediante un prototipo. Esta recopilación no establece todavía qué arquitectura será superior para el proyecto.
