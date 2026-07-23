package cruxui.android.maracuya.ui.components.buttons.navigation.group.variant

import cruxui.android.maracuya.ui.components.buttons.navigation.simple.variant.ButtonNavigationVariant

/**
 * Resuelve decisiones directas de las variantes fijas del grupo.
 *
 * No crea modelos intermedios: cada funcion responde una pregunta concreta que
 * necesita el render final sin duplicar la logica nativa de `ButtonNavigationMrcy`.
 */
internal object ButtonNavigationGroupVariantResolver {

    /** Define la variante nativa del boton izquierdo. */
    fun resolveLeadingButtonVariant(): ButtonNavigationVariant = ButtonNavigationVariant.SECONDARY

    /** Define la variante nativa del boton derecho. */
    fun resolveTrailingButtonVariant(): ButtonNavigationVariant = ButtonNavigationVariant.PRIMARY

    /** Resuelve el texto izquierdo cuando el developer no envia label. */
    fun resolveLeadingLabel(
        variant: ButtonNavigationGroupVariant,
        explicitLabel: String?,
    ): String =
        explicitLabel ?: when (variant) {
            ButtonNavigationGroupVariant.BACK_CONTINUE,
            ButtonNavigationGroupVariant.BACK_SUBMIT -> BackLabel

            ButtonNavigationGroupVariant.SKIP_CONTINUE,
            ButtonNavigationGroupVariant.SKIP_SUBMIT -> SkipLabel
        }

    /** Resuelve el texto derecho base cuando el developer no envia label. */
    fun resolveTrailingDefaultLabel(
        variant: ButtonNavigationGroupVariant,
    ): String =
        when (variant) {
            ButtonNavigationGroupVariant.BACK_CONTINUE,
            ButtonNavigationGroupVariant.SKIP_CONTINUE -> ContinueLabel

            ButtonNavigationGroupVariant.BACK_SUBMIT,
            ButtonNavigationGroupVariant.SKIP_SUBMIT -> SubmitLabel
        }

    /** Indica si el boton izquierdo debe mostrar el icono nativo. */
    fun resolveLeadingIconVisibility(
        variant: ButtonNavigationGroupVariant,
    ): Boolean =
        when (variant) {
            ButtonNavigationGroupVariant.BACK_CONTINUE,
            ButtonNavigationGroupVariant.BACK_SUBMIT -> true

            ButtonNavigationGroupVariant.SKIP_CONTINUE,
            ButtonNavigationGroupVariant.SKIP_SUBMIT -> false
        }

    /** Indica si el boton derecho debe reemplazar su icono nativo por submit. */
    fun resolveTrailingSubmitIcon(
        variant: ButtonNavigationGroupVariant,
    ): Boolean =
        when (variant) {
            ButtonNavigationGroupVariant.BACK_SUBMIT,
            ButtonNavigationGroupVariant.SKIP_SUBMIT -> true

            ButtonNavigationGroupVariant.BACK_CONTINUE,
            ButtonNavigationGroupVariant.SKIP_CONTINUE -> false
        }

    private const val BackLabel = "Back"
    private const val SkipLabel = "Skip"
    private const val ContinueLabel = "Continue"
    private const val SubmitLabel = "Submit"
}
