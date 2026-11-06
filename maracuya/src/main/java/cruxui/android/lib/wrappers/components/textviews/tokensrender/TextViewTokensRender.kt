package cruxui.android.lib.wrappers.components.textviews.tokensrender

import android.content.res.ColorStateList
import cruxui.android.lib.wrappers.capabilities.text.NativeTextStyle
import android.graphics.drawable.Drawable
import cruxui.android.lib.wrappers.capabilities.compounddrawables.CompoundDrawableRender

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewTokensRender` contiene las métricas, fuente e iconos preparados para el render nativo.
 *
 * Justificación:
 * Separa la conversión de tokens/recursos de las escrituras sobre AppCompatTextView. Los colores se entregan
 * aparte para responder a cambios de estado sin recalcular métricas.
 *
 * Se comunica con:
 * TextViewTokensRenderResolver lo crea; TextViewRenderer y los renderizadores comunes de párrafo/drawables lo
 * aplican.
 *
 * Uso:
 * Tipo interno del DS. El host usa atributos, recetas y setters. El renderer recibe este modelo durante la
 * sección protegida de aplicación para no registrar sus escrituras como nuevos overrides.
 *
 * Objetivos:
 * - Transportar valores nativos listos para aplicar.
 * - Separar métricas de actualizaciones de color.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param textStyle Métricas resueltas por la capacidad de texto Android.
 * @param iconStart Drawable inicial y política de gestión.
 * @param iconEnd Drawable final y política de gestión.
 * @param iconPaddingPx Separación de iconos en píxeles, o null si el renderer no la gestiona.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewTokensRender(
    val textStyle: NativeTextStyle,
    val iconStart: CompoundDrawableRender,
    val iconEnd: CompoundDrawableRender,
    val iconPaddingPx: Int?,
)

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `TextViewColorRender` agrupa el color de texto y superficies del estado nativo actual.
 *
 * Justificación:
 * Permite actualizar un selector o un estado de error sin volver a resolver la fuente y los iconos
 * estructurales.
 *
 * Se comunica con:
 * TextViewTokensRenderResolver.color lo construye y TextViewRenderer.color lo aplica.
 *
 * Uso:
 * Tipo interno del DS. El host configura colores mediante tokens, XML o setTextColor. Los ColorStateList
 * conservan sus reglas nativas; null en una superficie elimina su drawable.
 *
 * Objetivos:
 * - Separar el render de color de las métricas.
 * - Preservar selectores y superficies nativas.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param colors ColorStateList efectivo para el texto y el tinte de iconos.
 * @param background Drawable de fondo o null.
 * @param foreground Drawable superior o null.
 * @see cruxui.android.lib.core.components.textviews.tokens.TextViewTokenDefaults
 */
internal data class TextViewColorRender(
    val colors: ColorStateList,
    val background: Drawable?,
    val foreground: Drawable?,
)
