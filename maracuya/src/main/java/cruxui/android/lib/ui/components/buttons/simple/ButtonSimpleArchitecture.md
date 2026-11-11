# Button simple: arquitectura y migración

**s4121779 — Krause Kelsen · 11-07-2026 · 0.0.14**

Button sustituye a ButtonMrcy. Compose y XML comparten contratos puros en `cruxui.android.lib.core.components.buttons`. La implementación XML es nativa y contiene el TextView del DS; no aloja Compose. `ButtonWrp` conserva únicamente el nombre antiguo como subclase deprecada de Button.

## Responsabilidades y dependencias

```text
core/components/buttons/
  behavior/      ButtonBehavior, ButtonBehaviorState, ButtonBehaviorResolver
  tokens/        ButtonTokens, ButtonTokensOverride, merge, ButtonTokenDefaults,
                 ButtonLayoutTokens, ButtonLoadingTokens
  tokensrender/  ButtonTokensRender, ButtonTokensRenderResolver
  variant/       ButtonVariant

ui/components/buttons/simple/
  Button.kt
  behavior/      contrato core + adaptación de interacción y reloj Compose
  tokens/        contratos core + lectura de LibraryTheme.current
  tokensrender/  aliases internos de la resolución compartida
  variant/       contrato core + selección del TextView interno
  render/        superficie Compose, TextView DS e indicador

wrappers/components/buttons/simple/
  Button.kt, ButtonWrp.kt
  attributes/    lectura propia y reutilización de TextViewAttributeReader
  behavior/      contrato core + ObjectAnimator y ciclo de vida Android
  tokens/        contratos core, capas de resolución y registro por ámbito
  tokensrender/  aliases internos de la resolución compartida
  variant/       contrato core + selección del TextView interno
  render/        superficie nativa, TextView DS e ImageView decorativo
```

Cada capa tiene una responsabilidad: los tokens describen apariencia; behavior normaliza estados, protege eventos y controla el motor de animación; tokensrender selecciona colores; render dibuja los resultados. Los adaptadores dependen de los contratos del core. El core no depende de Android, Compose ni registros de Views. Los aliases conservan la identidad de tipo: no hay dos modelos que sincronizar.

La reutilización de TextView es por composición. Button entrega un `TextViewTokensOverride` con `typography = library.typography.buttons`, familia e iconos. TextView conserva su resolución de fuentes, tintes, espaciado, tamaño de los assets y dirección start/end. Todas las variantes del botón usan TextView DEFAULT, con tipografía buttons y sin subrayado. Button no introduce otro color para iconos ni reconstruye una fila de iconos por su cuenta.

## Variantes, estados y medidas

El enum contiene exactamente tres variantes, disponibles también como `primary`, `secondary` y `terciary` en XML:

| Variante | Superficie normal en tema claro | Contenido |
|---|---|---|
| `PRIMARY` | Fondo rojo de marca | Texto e iconos blancos |
| `SECONDARY` | Fondo base blanco y borde neutro negro | Texto e iconos neutros |
| `TERCIARY` | Fondo base blanco y borde rojo de marca | Texto e iconos de `fgOnInverseAux` |

Los valores se adaptan al modo oscuro usando el catálogo del DS. No existe `text` ni una variante hypertext de Button. HIPERTEXT y los iconos con acciones independientes pertenecen exclusivamente a TextView. Las tres variantes de Button aceptan iconos decorativos opcionales; pulsarlos activa la misma acción que el resto del botón.

| Prioridad | Entrada | Apariencia y acción |
|---|---|---|
| 1 | `enabled = false` o `behavior.isDisabled` | Colores disabled, etiqueta e iconos visibles, sin activación |
| 2 | `behavior.isLoading` | Colores normales, solo indicador giratorio visible, sin activación |
| 3 | Press real o `behavior.isPressed` | Colores pressed; soltar completa el gesto habitual |
| 4 | Sin flags | Apariencia normal y callbacks disponibles |

Deshabilitar suspende la representación de loading, pero no borra la solicitud del host. Al habilitar vuelve la carga si el host todavía la mantiene. Pulsar nunca activa loading por sí solo. No hay timeout, petición de red ni simulación en el componente.

La rotación se resuelve en behavior: una vuelta de 360 grados cada 1.000 ms, lineal, durante el tiempo que dure loading. Compose crea el motor únicamente en esa rama de composición; XML cancela el animator al desmontar, ocultar la View/ventana o salir de loading. El recurso fijo pertenece al catálogo: `LibraryGeneralIconsTokens.general.loading` (`ic_loading`). No existe un parámetro para sustituir el indicador por instancia. El tinte siempre procede del texto resuelto.

`loading` sigue el contrato del resto de General Icons: `val loading: IconToken` es un campo obligatorio del constructor y su valor se declara en el catálogo `companion object.general`. `LibraryIconTokens.Default` incorpora esa categoría a Library; al compilar se generan `LibraryTheme.current.icons.general.loading`, `LibraryIconRole.GeneralLoading` y `?attr/libraryIconGeneralLoading`. No se define un token por defecto en el constructor ni se mantiene un registro especial para Button.

El texto e iconos laterales dejan de ser visibles durante carga, pero siguen medidos para conservar el ancho y alto intrínsecos. Su semántica deja de exponerse como contenido duplicado; el botón conserva su etiqueta accesible y comunica “Cargando”. Al terminar se recuperan los iconos que el host tenga configurados en ese momento.

`ButtonLayoutTokens` centraliza el tamaño estándar: altura mínima 48 dp, ancho mínimo 112 dp, padding horizontal 24 dp, vertical 12 dp, borde 1 dp y radio 100 dp. No son variantes. La altura puede crecer con el escalado de fuente; no se fija a 48 dp. El espaciado texto/icono sigue siendo el del TextView. El host controla ancho/posición con Modifier o layout params. Puede cambiar la forma con `shape`, `setCornerRadius` o `app:buttonCornerRadius`.

## Tokens públicos

Todos los colores predeterminados proceden del tema Library. PRIMARY conserva la receta roja anterior; TERCIARY conserva la receta anterior de borde rojo; SECONDARY formaliza la receta de borde neutro que antes se mostraba como override en el preview. Se eliminó el fallback que fabricaba colores Material. Los overrides se aplican después de la receta y antes de resolver el estado.

| Campo | Función | PRIMARY | SECONDARY | TERCIARY |
|---|---|---|---|---|
| `background` | Fondo normal y de loading | `brandPrimary` | `bgBase` | `bgBase` |
| `textColor` | Texto e iconos normales y loading | `fgOnInverse` | `fgDefault` | `fgOnInverseAux` |
| `pressedTextColor` | Tinte textual pressed | `fgOnInverse` | `bgBase` | `fgOnInverse` |
| `pressedBackground` | Fondo pressed | `brandPrimaryHover` | `fgDefault` | `brandPrimaryHover` |
| `disabledBackground` | Fondo disabled | `bgMuted` | `bgMuted` | `bgMuted` |
| `disabledTextColor` | Texto e iconos disabled | TextView: `fgSubtle` | Igual | Igual |
| `border` | Borde normal y loading | `brandPrimary` | `fgDefault` | `brandPrimary` |
| `pressedBorder` | Borde pressed, independiente del fondo | `brandPrimaryHover` | `fgDefault` | `brandPrimaryHover` |
| `disabledBorder` | Borde disabled | `borderDefault` | `borderDefault` | `borderDefault` |
| `typography` | Métricas del TextView | `typography.buttons` | Igual | Igual |
| `fontFamily` | Familia heredada de TextView, separada de métricas | `library.fontFamily` | Igual | Igual |
| `iconStart` | Icono decorativo inicial, relativo a RTL | null | null | null |
| `iconEnd` | Icono decorativo final, relativo a RTL | null | null | null |

`ButtonTokens` contiene la receta completa. `ButtonTokensOverride` tiene los mismos campos opcionales; null significa conservar la capa inferior. El resultado interno `ButtonTokensRender` expone `background`, `border`, `textColor`, `typography`, `fontFamily`, **`iconStart` e `iconEnd`**. Sus propiedades `iconStartColor` e `iconEndColor` derivan siempre de `textColor`; `text` construye el override que se entrega al TextView. No se mantienen dos recetas ni se decide de nuevo el estado en cada renderer.

Compose admite iconos directamente en `Button(iconStart = ..., iconEnd = ...)` o en `tokensOverride`. Los parámetros directos toman por defecto el valor del override; un null explícito retira ese icono aunque exista en el override. XML dispone de `setIconStart(null)` / `setIconEnd(null)` para retirar iconos de receta o XML. Texto e iconos siempre comparten tinte, incluso si el IconToken traía un color propio. No hay callbacks independientes para los iconos.

## Uso Compose completo: operación real

Este ejemplo se puede copiar a un archivo Kotlin del host. El host inyecta `save`, una operación suspendida real; la carga finaliza en `finally`, incluso cuando se cancela. No usa demoras artificiales.

```kotlin
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.ui.components.buttons.simple.Button
import cruxui.android.lib.ui.components.buttons.simple.behavior.ButtonBehavior
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariant
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@Composable
fun SaveChangesButton(save: suspend () -> Unit, onError: (Throwable) -> Unit) {
    MyLibraryTheme {
        val scope = rememberCoroutineScope()
        var saving by remember { mutableStateOf(false) }
        val library = LibraryTheme.current
        Button(
            text = "Guardar cambios",
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.PRIMARY,
            behavior = ButtonBehavior(isLoading = saving),
            iconEnd = library.icons.alerts.info,
            onClick = {
                if (!saving) {
                    saving = true
                    scope.launch {
                        try {
                            save()
                        } catch (cancelled: CancellationException) {
                            throw cancelled
                        } catch (failure: Exception) {
                            onError(failure)
                        } finally {
                            saving = false
                        }
                    }
                }
            },
        )
    }
}
```

En una pantalla que ya tenga `MyLibraryTheme`, usar su ámbito existente. Para operaciones que sobreviven a la pantalla, conservar el estado en el ViewModel y entregar su valor como `behavior.isLoading`.

| Parámetro Compose | Cómo se usa |
|---|---|
| `text` | String requerido, etiqueta visible y nombre accesible de la carga |
| `onClick` | Acción requerida de toda la superficie, incluidos sus iconos decorativos |
| `modifier` | Ancho, disposición, test tags y posición del host |
| `enabled` | Habilitación estándar; false tiene máxima prioridad |
| `variant` | Receta semántica; PRIMARY si se omite |
| `tokensOverride` | Personalización parcial del tema; null conserva defaults |
| `iconStart`, `iconEnd` | IconToken opcional en cada extremo. Por defecto toman los iconos de tokensOverride; null explícito los retira |
| `iconStartContentDescription`, `iconEndContentDescription` | Descripciones opcionales entregadas al TextView. No crean acciones separadas; omitirlas para iconos puramente decorativos |
| `behavior` | Flags controlados `isLoading`, `isDisabled`, `isPressed` |
| `shape` | Forma Compose; por defecto cápsula estándar |
| `interactionSource` | Fuente estable para observar interacciones reales con `collectIsPressedAsState` |
| `onLongClick` | Acción larga opcional; se bloquea durante loading/disabled |
| `onClickLabel` | Descripción localizada opcional de la acción para accesibilidad |

La selección de texto y las acciones independientes de iconos de TextView no se publican en Button: el botón representa una única acción. Se reutiliza su lógica visual y sus iconos decorativos.

## Uso XML completo

Guardar el siguiente layout en el host. Los recursos de tema, tipografía e iconos se exportan desde Library al construir el AAR. No hay que mantener IDs a mano.

```xml
<?xml version="1.0" encoding="utf-8"?>
<cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/ThemeOverlay.Maracuya"
    app:themeName="Maracuya"
    app:themeStyle="auto">
    <cruxui.android.lib.wrappers.components.buttons.simple.Button
        android:id="@+id/saveButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Guardar cambios"
        android:enabled="true"
        android:textAppearance="?attr/libraryTypographyButtons"
        app:buttonVariant="primary"
        app:iconEnd="?attr/libraryIconAlertsInfo"
        app:isLoading="false" />
</cruxui.android.lib.wrappers.theme.MyLibraryThemeWrp>
```

El literal se usa para que el ejemplo pueda copiarse sin crear recursos auxiliares; las pantallas de producción pueden localizarlo con `@string/...`. El proveedor semántico y el overlay Android deben representar la misma marca/modo. Los atributos XML explícitos se resuelven al inflar; para cambios de marca en ejecución, usar recetas dependientes del snapshot.

Este controlador es copiable a un archivo Kotlin del host y recibe una View ya inflada y el scope de la pantalla. Llamarlo desde el hilo principal y pasar un scope que cancele sus trabajos al destruir la pantalla.

```kotlin
import cruxui.android.lib.wrappers.components.buttons.simple.Button
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun bindSaveButton(
    button: Button,
    screenScope: CoroutineScope,
    save: suspend () -> Unit,
    onError: (Throwable) -> Unit,
) {
    button.setOnClickListener {
        if (!button.isLoading) {
            button.isLoading = true
            screenScope.launch(Dispatchers.Main.immediate) {
                try {
                    save()
                } catch (cancelled: CancellationException) {
                    throw cancelled
                } catch (failure: Exception) {
                    onError(failure)
                } finally {
                    button.isLoading = false
                }
            }
        }
    }
}
```

`android:onClick` conserva la resolución estándar de métodos públicos del Context Android. También están disponibles `setOnClickListener`, `setOnLongClickListener`, `performClick`, `callOnClick`, teclado y accesibilidad. La política del behavior bloquea las activaciones programáticas durante loading/disabled, además de los gestos. El texto interno no roba el evento del botón.

### API de Views y atributos

| API Kotlin / XML | Contrato |
|---|---|
| `text`, `setText(@StringRes)`, `android:text` | CharSequence, admite spans y recursos |
| `isEnabled`, `android:enabled` | Habilitación Android; alias anterior `app:isEnabled` sigue aceptado |
| `isPressed`, `app:isPressed` | Press físico o estado inicial controlado de la galería |
| `isLoading`, `app:isLoading` | Solicitud de carga; se mantiene hasta que el host la retire |
| `isDisabled` | Consulta disabled efectivo; el setter modifica behavior.isDisabled; isEnabled sigue independiente |
| `behavior` | Sustituye el contrato completo, sin borrar listeners |
| `variant`, `app:buttonVariant` | Únicamente primary, secondary o terciary |
| `tokensOverrideName`, `app:buttonTokensOverride` | Nombre de receta registrado antes de inflate |
| `setTokensOverride`, `getTokensOverride` | Sustituye/consulta el override semántico de ejecución; null lo retira |
| `clearOverrides()` | Retira overrides e iconos de ejecución y restaura XML/receta |
| `setTypography` | Cambia métricas con un TypographyToken y reemplaza tamaños tipográficos explícitos inferiores |
| `setIconStart(IconToken?)`, `setIconEnd(IconToken?)` | Cambian o retiran iconos, incluso los provenientes de XML; null los elimina |
| `setIconStart(ViewIcon)`, `setIconEnd(ViewIcon)` | Reutilizan las fuentes de TextView: Token, Role, Semantic o None |
| `setIconStartContentDescription`, `setIconEndContentDescription` | Descripciones entregadas al TextView y agrupadas en la única acción accesible del botón |
| `app:iconStartContentDescription`, `app:iconEndContentDescription` | Equivalentes XML de las descripciones opcionales |
| `app:iconPadding` | Separación entre texto e iconos, leída y aplicada por TextView |
| `app:iconStart`, `app:iconEnd` | Recursos de los lados; usa la lectura compartida de TextView |
| `android:drawableStart/End`, `android:drawablePadding` | Alternativas nativas para iconos/espaciado; los atributos app de icono prevalecen |
| `android:textAppearance`, `fontFamily`, `textSize`, etc. | Reutilizan TextViewAttributeReader para las métricas y estilos textuales |
| `android:maxLines` | Una línea por defecto con elipsis; el host puede permitir más líneas en XML |
| `android:textColor` | Color normal; usar pressedTextColor/disabledTextColor para esos estados |
| `app:buttonBackground`, `app:buttonBorder` | Color normal del fondo/borde, conserva la forma tokenizada |
| `app:pressedBackground`, `pressedBorder`, `pressedTextColor` | Colores pressed explícitos |
| `app:disabledBackground`, `disabledBorder`, `disabledTextColor` | Colores disabled explícitos |
| `android:background` / setter nativo | Drawable completo del host; reemplaza la superficie gestionada por tokens |
| `clearBackgroundOverride()` | Recupera el fondo y borde gestionados por el DS |
| `setCornerRadius`, `app:buttonCornerRadius` | Radio en dp, independiente de variante |
| `setThemeTokens`, `getThemeTokens`, `refreshTheme` | Tema por instancia, consulta del snapshot y actualización de recetas |

Los colores específicos de Button se decodifican como colores, no como selectores de estado Android. Los estados se configuran con sus tokens/atributos correspondientes. El resto de atributos y operaciones de View (ID, padding, foco, layout params, visibilidad, contentDescription) mantiene su funcionamiento nativo. Los estados, recetas y callbacks no se serializan como una operación pendiente: el host restaura su modelo al recrear la pantalla.

La precedencia semántica es **defaults → receta → XML → override de ejecución**. Los setters explícitos de iconos/tipografía nativos se aplican por encima y se retiran con `clearOverrides`. El tema se resuelve por instancia mediante `ComponentTokenBinding`, igual que en TextView. No se instala ningún tema activo global.

La presencia de los iconos depende exclusivamente de su fuente: un icono configurado se muestra y un icono ausente o retirado con null no ocupa espacio. Button no expone controles de visibilidad independientes. El lector compartido reutiliza únicamente tipografía, fuentes y espaciado de iconos de TextView. Loading sigue sustituyendo temporalmente todo el contenido por el indicador.

### Iconos XML por código

```kotlin
import cruxui.android.lib.wrappers.components.buttons.simple.Button
import cruxui.android.lib.wrappers.core.tokens.ViewIcon

fun configureButtonIcons(button: Button) {
    // La selección semántica sigue al tema cuando cambia el proveedor.
    button.setIconStart(ViewIcon.Semantic { icons -> icons.alerts.info })
    button.setIconEnd(button.getThemeTokens().icons.alerts.warning)
    button.setIconStart(null)     // retira explícitamente el icono inicial
    button.setIconEnd(null)        // retira explícitamente el icono final
}
```

Los iconos puramente decorativos no necesitan descripción. Cuando aporten información adicional, usar `setIconStartContentDescription` o `setIconEndContentDescription`; se anuncia dentro del mismo control, sin añadir acciones propias. `clearOverrides()` restablece las fuentes de iconos de XML y receta; las descripciones conservan el último valor asignado por sus setters.

### Receta compartida

```kotlin
import cruxui.android.lib.theme.LibraryThemeSnapshot
import cruxui.android.lib.core.components.buttons.tokens.ButtonTokensOverride
import cruxui.android.lib.wrappers.components.buttons.simple.tokens.ButtonTokensOverrideRegistry

fun confirmationRecipe(library: LibraryThemeSnapshot) = ButtonTokensOverride(
    iconEnd = library.icons.alerts.info,
    typography = library.typography.buttons,
)

fun registerButtonRecipes() {
    ButtonTokensOverrideRegistry.register("confirmation", ::confirmationRecipe)
}
```

Llamar `registerButtonRecipes()` antes de inflar y seleccionar `app:buttonTokensOverride="confirmation"`. En Compose usar `tokensOverride = confirmationRecipe(LibraryTheme.current)`. El registro admite register/resolve/contains/unregister/clear; una receta desconocida falla explícitamente durante ejecución. El editor omite recetas ausentes porque no ejecuta la inicialización de Application. Reemplazar una receta exige `refreshTheme()` en Views ya creadas.

## Migración desde ButtonMrcy

| Anterior | Nuevo |
|---|---|
| `simple.ButtonMrcy` | `simple.Button` |
| `simple.ButtonVariant` | `simple.variant.ButtonVariant` |
| `simple.ButtonTokensOverride` | `simple.tokens.ButtonTokensOverride` o contrato core |
| `buttonVariant` | `variant` |
| Antiguo `SECONDARY` con borde rojo | `TERCIARY` |
| Override blanco con borde neutro del preview antiguo | `SECONDARY` |
| `buttonTokensOverride` | `tokensOverride` |
| `buttonTokens` completo en el componente | Override parcial, con receta compartida si se necesita |
| `showProgressOnPress` | Retirado; `behavior = ButtonBehavior(isLoading = loading)` |
| `containerColor` | `background` |
| `contentColor` | `textColor` |
| `contentPressColor` | `pressedTextColor` |
| `hoverContainerColor` | `pressedBackground` |
| `disabledContainerColor` | `disabledBackground` |
| `disabledContentColor` | `disabledTextColor` |
| `borderContainerColor` | `border` |
| `borderDisabledColor` | `disabledBorder` |
| Borde pressed derivado del fondo | `pressedBorder` independiente |
| `textTypography` | `typography` |
| `fontFamilyToken` | `fontFamily` |
| Wrapper con Modifier/Shape/InteractionSource Compose | View nativa con layout params, radio y estados Android |

Se actualizaron los consumidores de la app, los adaptadores de ButtonNavigation y las pruebas de ComponentTokenBinding. La API anterior no se conserva como una segunda implementación. ButtonNavigation mantiene su contrato propio: sus variantes SECONDARY y SECONDARYSQUARE se mapean a TERCIARY del botón simple para conservar el borde rojo anterior.

## Previews y comprobación

- Compose: `ui/previews/ButtonPreview.kt`: Primary, Secondary, Terciary, iconos y ejemplo interactivo de inicio/fin de carga.
- XML: `button_primary_preview.xml`, `button_secondary_preview.xml`, `button_terciary_preview.xml`: dos columnas claro/oscuro y cuatro filas normal/pressed/loading/disabled.
- `button_wrp_preview.xml`: reúne las tres galerías, ausencia/start/end/ambos iconos y un ejemplo controlado por el host. `ButtonGalleryPreview` conecta solo los callbacks de demostración.
- Las referencias proporcionan el orden de estados y la forma de cápsula de PRIMARY y TERCIARY (borde rojo). SECONDARY usa el borde neutro solicitado. Las tres galerías incluyen loading y ambos modos. Los colores siguen Library; por ejemplo, el texto disabled oscuro usa el valor real de `fgSubtle`, sin inventar un gris para imitar la captura.

```shell
./gradlew :maracuya:testDebugUnitTest :maracuya:assembleDebug :app:assembleDebug
./gradlew :maracuya:connectedDebugAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.class=cruxui.android.lib.ui.components.buttons.ButtonInteractionTest
```

Las pruebas verifican combinaciones de estados, colores existentes, borde pressed independiente, bloqueo de acciones, TextView nativo, medidas en loading, temas, recuperación de iconos, render nativo en edit mode, descripciones de iconos y accesibilidad. Tocar cualquiera de los iconos Compose debe activar exclusivamente el callback del botón. Las pruebas instrumentadas guardan las seis capturas de referencia en filesDir del APK de pruebas. También se guardan capturas del botón con ambos iconos configurados en Compose y XML. La última validación completó 74 pruebas unitarias y compiló la librería/AAR y los consumidores de la app; incluye comprobar que Button no hereda controles de visibilidad de TextView. La validación visual instrumentada anterior completó 5 pruebas y ensambló la app. Las capturas recuperadas están en `captures/button-validation/` (salidas ignoradas por Git). El inventario de carpetas y archivos de esta migración está en `ButtonSimpleChanges.md` en la raíz del repositorio.

### Recuperar las capturas del dispositivo

`connectedDebugAndroidTest` puede desinstalar el APK de pruebas al finalizar y retirar su filesDir. Para conservar evidencia visual local, instalar el APK de pruebas y ejecutar directamente el runner; estos comandos requieren un único emulador de pruebas conectado (o `ANDROID_SERIAL` apuntando al que corresponda):

```shell
./gradlew :maracuya:assembleDebugAndroidTest
adb install -r maracuya/build/outputs/apk/androidTest/debug/maracuya-debug-androidTest.apk
adb shell am instrument -w -r \
  -e class cruxui.android.lib.ui.components.buttons.ButtonInteractionTest \
  cruxui.android.lib.test/androidx.test.runner.AndroidJUnitRunner
mkdir -p captures/button-validation
adb exec-out run-as cruxui.android.lib.test \
  cat files/button-primary-xml.png > captures/button-validation/button-primary-xml.png
```

Repetir la última extracción para primary/secondary/terciary en compose/xml, y para `button-icons-compose.png` y `button-icons-xml.png`. El runner directo conserva estos archivos hasta desinstalar o limpiar el APK. El inventario enumera los ocho nombres. En esta revisión se verificaron visualmente ambas implementaciones, además de los resultados automáticos; esto no implica una comparación automática píxel por píxel.

### Recursos y preview de Android Studio

Los previews usan las clases reales de Button y los recursos generados del catálogo; no reemplazan el contenido por líneas o placeholders. Los layouts están formateados con un atributo por línea y los iconos se resuelven mediante los adaptadores compartidos de TextView. La animación XML se mantiene estática en edit mode; durante ejecución el behavior la activa según visibilidad y loading.

Durante la revisión se encontró en el log del editor `NoSuchFieldError: R$string.button_loading`: el renderer del IDE estaba usando una clase R sin un recurso que sí existía en las salidas regeneradas por Gradle. Una compilación correcta no demuestra que el editor haya recargado esa clase. Si aparece ese error después de actualizar esta rama, ejecutar **Sync Project with Gradle Files**, compilar el módulo `maracuya` y usar **Build & Refresh** en el preview. Para XML, volver a abrir el layout después de sincronizar. No sustituir el string localizado ni el icono del catálogo por valores hardcodeados para ocultar un error de recursos del editor.
