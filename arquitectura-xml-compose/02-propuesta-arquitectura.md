# Arquitectura implementada del puente XML–Compose

Actualizado: 2 de noviembre de 2026. Este documento describe código existente.

## Fuente y generación

`LibraryColorTokens`, `LibraryTypographyTokens`, `LibraryIconTokens` y el catálogo de fuentes contienen las definiciones. `LibraryThemes` configura las marcas y sus modos. `LibraryThemeSet.resolve` entrega un `LibraryThemeSnapshot`; no se reconstruye una segunda biblioteca en wrappers.

El exportador JVM descubre las propiedades de las Library durante el build y genera colecciones tipadas, roles y aliases de compatibilidad. A continuación exporta recursos Android y referencias Kotlin estáticas. Los escritores de colores, tipografías, iconos, fuentes y estilos opcionales tienen responsabilidades independientes. `ExportLibraryTokens` conecta estos escritores; agregar un componente que consuma los mismos recursos no exige modificarlo.

`CorporateTypography` y `CorporateIcons` son aliases generados para compatibilidad de imports Kotlin. No contienen políticas ni colecciones duplicadas. La API actual utiliza `LibraryTypography` y `LibraryIcons` generados en `theme.collections`.

## Flujo en ejecución

```text
LibraryThemes → LibraryThemeSnapshot
  ├─ MyLibraryTheme → CompositionLocal → LibraryTheme.current → componente Compose
  └─ MyLibraryThemeWrp → AndroidThemeResolver → componente nativo

TokensOverrideSource<T> + proveedor de tema
  → ComponentTokenBinding<T> → receta del componente
  → defaults + receta + XML + parche Kotlin
  → adaptación de plataforma → render
```

El registro conserva recetas, no un tema global ni Views. Cada instancia resuelve el proveedor más cercano. Los wrappers que alojan Compose utilizan `wrappers/adapters/compose`; el TextView nativo utiliza el resolutor Android.

## Distribución de responsabilidades

| Ubicación | Responsabilidad |
|---|---|
| `core/components/<componente>` | Tokens, defaults, variantes y políticas compartidas sin Android ni Compose. |
| `tokens/spacings` | Medidas reutilizables entre componentes, como el objetivo interactivo mínimo. |
| `ui/components/<componente>` | Lectura de locals, adaptación y render Compose. |
| `wrappers/core` | Registro, vínculo de tema, fuentes de valores, recursos y gestos de View. |
| `wrappers/capabilities` | Texto y drawables relativos como capacidades opcionales reutilizables. |
| `wrappers/components/<componente>` | Lector XML, estado, adaptación, render y callbacks específicos. |
| `token-exporter` | Descubrimiento y generación durante la compilación; sin responsabilidades de interacción. |

Las capacidades no importan componentes concretos. Un Button puede usar texto, iconos y recursos sin depender del TextView del DS. Cada componente conserva su propio registro, variantes y comportamiento.

## Configuración declarativa

Los recursos generados se usan con `?attr/libraryColorBrandPrimary`, `?attr/libraryTypographyHeadline24` y `?attr/libraryIconAlertsInfo` bajo el overlay correspondiente. Las referencias Kotlin están agrupadas en `LibraryXmlReferences`.

Una receta `TextViewTokensOverride` permite compartir configuración dinámica entre Compose y XML. XML la selecciona con `app:textViewTokensOverride`. Los valores nativos específicos se expresan mediante atributos estándar y `TextViewNativeOverrides`; no se incluyen listeners en tokens.

La precedencia es **parche Kotlin > XML/style/TextAppearance resueltos > receta > defaults**. Un cambio del proveedor reevalúa recetas. Las referencias `?attr` ya resueltas requieren recrear o reinflar con el overlay adecuado para cambiar su tema Android.

## Validación y documentación relacionada

Las pruebas verifican límites de dependencias, recursos generados, precedencia, ámbitos, gestos, medidas y línea base. El [manual TextView](03-textview-implementado.md) explica la API; [escalabilidad](04-escalabilidad-y-responsabilidades.md) explica cómo agregar otro componente; [tipografía](05-tipografia-fuente-unica.md) y [colores e iconos](06-colores-iconos-fuente-unica.md) explican el catálogo. [Paridad](07-paridad-textview-compose-xml.md) documenta el contrato de presentación.
