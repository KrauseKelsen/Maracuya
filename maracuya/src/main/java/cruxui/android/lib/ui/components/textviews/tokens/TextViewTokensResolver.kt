package cruxui.android.lib.ui.components.textviews.tokens

import androidx.compose.runtime.Composable
import cruxui.android.lib.theme.LibraryTheme
import cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
import cruxui.android.lib.ui.components.textviews.variant.TextViewVariant

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensResolver` conecta el tema del ámbito Compose con la receta semántica compartida.
 *
 * Justificación:
 * Es el borde que puede leer CompositionLocals; las decisiones visuales permanecen en TextViewTokenDefaults.
 *
 * Se comunica con:
 * Obtiene `LibraryTheme.current` y delega en `TextViewTokenDefaults.resolve`; TextView consume el resultado.
 *
 * Uso:
 * Objeto público del DS. El host normalmente entrega tokensOverride a TextView y no invoca este resolutor.
 * Para una integración Compose propia se puede llamar `TextViewTokensResolver.resolve(variant, override)` dentro
 * de MyLibraryTheme.
 * `default` y `hipertext` son propiedades composables de conveniencia. Fuera de composición usar
 * TextViewTokenDefaults con un snapshot explícito.
 *
 * Objetivos:
 * - Aislar la lectura de locals.
 * - Reutilizar la receta de ambas plataformas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
object TextViewTokensResolver {
    /**
     * Obtiene los tokens de la variante DEFAULT en el ámbito Compose actual, sin override. Requiere los
     * proveedores del tema.
     */
    @get:Composable val default: TextViewTokens get() = resolve()
    /**
     * Obtiene los tokens de HIPERTEXT en el ámbito Compose actual, sin override. La decoración se aplica en el
     * resolutor de variante.
     */
    @get:Composable val hipertext: TextViewTokens get() = resolve(TextViewVariant.HIPERTEXT)

    /**
     * Lee la biblioteca del ámbito Compose y resuelve la receta compartida.
     *
     * @param variant Presentación visual solicitada.
     * @param override Personalización parcial; null conserva defaults.
     * @return Tokens completos del tema activo. Debe llamarse dentro de composición.
     */
    @Composable
    fun resolve(variant: TextViewVariant = TextViewVariant.DEFAULT, override: TextViewTokensOverride? = null): TextViewTokens =
        TextViewTokenDefaults.resolve(LibraryTheme.current, variant, override)
}
