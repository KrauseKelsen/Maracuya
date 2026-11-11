package cruxui.android.lib.wrappers.previews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import cruxui.android.lib.R
import cruxui.android.lib.wrappers.components.buttons.simple.Button

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción: ButtonGalleryPreview conecta los dos controles del ejemplo XML interactivo.
 *
 * Justificación: Los layouts muestran estados; este contenedor demuestra cómo el host inicia y
 * termina una operación.
 *
 * Se comunica con: button_wrp_preview.xml incluye galerías claro/oscuro y los botones start/finish.
 *
 * Uso:
 * ```kotlin
 * val gallery = android.view.LayoutInflater.from(context)
 *     .inflate(cruxui.android.lib.R.layout.button_wrp_preview, parent, false)
 * parent.addView(gallery)
 * ```
 *
 * En el editor se ven estados estáticos; en ejecución pulsar Iniciar carga y después Finalizar
 * operación. La segunda acción representa la respuesta del host, sin temporizador dentro del
 * componente.
 *
 * Objetivos:
 * - Mantener ejemplos de integración fuera del código de producción del botón.
 *
 * Flujo: tema y entrada del host → resolución compartida → adaptador de plataforma → render.
 *
 * @param context Context del layout.
 * @param attrs Atributos de LinearLayout, incluida su orientación.
 * @author Krause Kelsen
 * @since 11-07-2026
 * @version 0.0.14
 */
class ButtonGalleryPreview
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    /** Conecta callbacks una vez inflados los controles del ejemplo. */
    override fun onFinishInflate() {
        super.onFinishInflate()
        val start = findViewById<Button>(R.id.buttonPreviewStart)
        start.setOnClickListener { start.isLoading = true }
        findViewById<Button>(R.id.buttonPreviewFinish).setOnClickListener {
            start.isLoading = false
        }
    }
}
