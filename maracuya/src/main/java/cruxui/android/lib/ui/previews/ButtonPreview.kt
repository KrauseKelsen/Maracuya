package cruxui.android.lib.ui.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cruxui.android.lib.compositions.LocalLibraryIcons
import cruxui.android.lib.core.components.buttons.behavior.ButtonBehavior
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.theme.MyLibraryTheme
import cruxui.android.lib.theme.ThemeStyle
import cruxui.android.lib.ui.components.buttons.simple.Button
import cruxui.android.lib.ui.components.buttons.simple.variant.ButtonVariant
import cruxui.android.lib.utils.composeadapters.ColorComposeAdapter

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonPreview contiene las galerías Compose equivalentes a las referencias y al XML.
 *
 * Justificación: Cada variante muestra dos columnas, claro/oscuro, y cuatro filas: normal, pressed,
 * loading y disabled.
 *
 * Se comunica con: Usa los componentes públicos y los tokens del tema; no introduce colores ni
 * animaciones de demostración.
 *
 * Uso:
 * ```kotlin
 * // Android Studio: abrir ButtonPreview.kt y activar Interactive Preview en ButtonImplementationPreview.
 * // Pulsar Iniciar carga y luego Finalizar operación: solo el host cambia el estado.
 * ```
 *
 * Las vistas de referencia usan texto Button, forma de cápsula y los colores efectivos del
 * catálogo. La galería adicional verifica iconos start, end, ambos y ausencia en las tres
 * variantes.
 *
 * Objetivos:
 * - Hacer revisables estados, modos e integración controlada del host.
 *
 * Flujo: tema y entrada del host → resolución compartida → adaptador de plataforma → render.
 *
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
@Preview(name = "Primary · light / dark", showBackground = true)
@Composable
fun ButtonPrimaryPreview() = ButtonReferencePreview(ButtonVariant.PRIMARY)

/** Secondary muestra fondo base y borde neutro en normal, pressed, loading y disabled. */
@Preview(name = "Secondary · light / dark", showBackground = true)
@Composable
fun ButtonSecondaryPreview() = ButtonReferencePreview(ButtonVariant.SECONDARY)

/**
 * Terciary usa la receta de borde rojo; pressed, loading y disabled resuelven sus propios tokens.
 */
@Preview(name = "Terciary · light / dark", showBackground = true)
@Composable
fun ButtonTerciaryPreview() = ButtonReferencePreview(ButtonVariant.TERCIARY)

/**
 * @param variant Receta que se representa en columnas claro/oscuro, sin mezclar estado con
 *   variante.
 */
@Composable
private fun ButtonReferencePreview(variant: ButtonVariant) {
    Row {
        for (dark in listOf(false, true)) {
            MyLibraryTheme(style = if (dark) ThemeStyle.DARK else ThemeStyle.LIGHT) {
                Column(
                    Modifier.width(150.dp)
                        .background(
                            ColorComposeAdapter.toComposeColor(LibraryTheme.current.colors.bgBase)
                        )
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(36.dp),
                ) {
                    for (behavior in
                        listOf(
                            ButtonBehavior(),
                            ButtonBehavior(isPressed = true),
                            ButtonBehavior(isLoading = true),
                            ButtonBehavior(isDisabled = true),
                        )) {
                        Button(
                            text = "Button",
                            onClick = {},
                            modifier = Modifier.fillMaxWidth(),
                            variant = variant,
                            behavior = behavior,
                        )
                    }
                }
            }
        }
    }
}

/** Galería de las cuatro disposiciones de iconos, con su mismo tinte textual y dirección lógica. */
@Preview(name = "Iconos · none / start / end / both", showBackground = true)
@Composable
fun ButtonIconsPreview() {
    MyLibraryTheme {
        val icon = LibraryTheme.current.icons.alerts.info
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            for (variant in ButtonVariant.entries) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (position in 0..3) Button(
                        text = "Button",
                        onClick = {},
                        variant = variant,
                        iconStart = icon.takeIf { position == 1 || position == 3 },
                        iconEnd = icon.takeIf { position == 2 || position == 3 },
                    )
                }
            }
        }
    }
}

/**
 * Ejemplo interactivo: simula únicamente las decisiones del host; el componente no tiene timeout.
 */
@Preview(name = "Implementación · carga controlada", showBackground = true)
@Composable
fun ButtonImplementationPreview() {
    MyLibraryTheme {
        var loading by remember { mutableStateOf(false) }
        val icon = LibraryTheme.current.icons.alerts.info
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                text = "Iniciar carga",
                onClick = { loading = true },
                behavior = ButtonBehavior(isLoading = loading),
                iconStart = icon,
                iconEnd = icon,
            )
            Button(
                text = "Finalizar operación",
                onClick = { loading = false },
                variant = ButtonVariant.SECONDARY,
            )
        }
    }
}
