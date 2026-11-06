package cruxui.android.lib.wrappers.previews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import cruxui.android.lib.R
import cruxui.android.lib.wrappers.components.textviews.TextView

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * TextViewGalleryPreview conecta las acciones de ejemplo de la galería XML equivalente a Compose.
 *
 * Justificación:
 * XML declara apariencia y selección, pero los callbacks se asignan en Kotlin. Este contenedor mantiene
 * esa configuración fuera del componente y permite que el editor reserve las mismas celdas de iconos.
 * No registra recetas, inventa colores ni añade comportamiento a TextView en producción.
 *
 * Uso:
 * Contenedor de demostración para res/layout/text_view_preview.xml. El host puede inflar ese layout para
 * inspeccionarlo. Para una pantalla propia usar TextView y asignar callbacks reales desde su controlador.
 * Las acciones aquí son vacías, igual que en TextViewGalleryPreview de Compose.
 *
 * Objetivos:
 * - Mostrar los mismos casos visuales e interactivos en ambas galerías.
 * - Separar la conexión de ejemplos del comportamiento del componente.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 */
class TextViewGalleryPreview @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    /** Asigna acciones después de que existan todas las Views y sus descripciones localizadas. */
    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<TextView>(R.id.nativeTextIcons).apply {
            setOnIconStartClickListener {}
            setOnIconStartLongClickListener { true }
            setOnIconEndClickListener {}
            setOnIconEndLongClickListener { true }
        }
        findViewById<TextView>(R.id.nativeTextLabel).setOnIconEndClickListener {}
        findViewById<TextView>(R.id.nativeTextGestures).apply {
            setOnClickListener {}
            setOnDoubleClickListener {}
            setOnLongClickListener { true }
            ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
                context.getString(R.string.text_view_preview_click_label)) { _, _ -> performClick() }
            ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_LONG_CLICK,
                context.getString(R.string.text_view_preview_long_click_label)) { _, _ -> performLongClick() }
        }
        findViewById<TextView>(R.id.nativeTextHipertext).apply {
            setOnClickListener {}
            ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
                context.getString(R.string.text_view_preview_link_label)) { _, _ -> performClick() }
        }
    }
}
