package cruxui.android.maracuya.wrappers.components.textviews

import android.os.Bundle
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.widget.AppCompatTextView
import cruxui.android.maracuya.R
import cruxui.android.maracuya.core.text.TextInteraction
import cruxui.android.maracuya.core.text.TextInteractionState
import cruxui.android.maracuya.tokens.base.IconToken
import cruxui.android.maracuya.wrappers.components.textviews.attributes.TextViewAttributeReader
import cruxui.android.maracuya.wrappers.components.textviews.behavior.TextViewBehavior
import cruxui.android.maracuya.wrappers.components.textviews.behavior.TextViewIconBehavior
import cruxui.android.maracuya.wrappers.components.textviews.behavior.TextViewBehaviorResolver
import cruxui.android.maracuya.wrappers.core.behavior.CompoundDrawableBehaviorController
import cruxui.android.maracuya.wrappers.core.behavior.CompoundDrawableSlot
import cruxui.android.maracuya.wrappers.components.textviews.render.TextViewRenderer
import cruxui.android.maracuya.wrappers.components.textviews.tokens.*
import cruxui.android.maracuya.wrappers.core.tokens.*
import cruxui.android.maracuya.ui.components.labels.tokens.LabelTokensOverride
import cruxui.android.maracuya.tokens.base.FontFamilyToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.wrappers.components.textviews.tokensrender.TextViewTokensRenderResolver
import cruxui.android.maracuya.wrappers.components.textviews.variant.TextViewVariant
import cruxui.android.maracuya.wrappers.components.textviews.variant.TextViewVariantResolver
import cruxui.android.maracuya.wrappers.core.theme.AndroidThemeAware
import cruxui.android.maracuya.wrappers.core.theme.AndroidThemeResolver
import cruxui.android.maracuya.wrappers.core.theme.AndroidThemeSnapshot
import kotlin.math.roundToInt

/**
 * Native Maracuya text primitive. XML, standard setters and partial token overrides share
 * one resolution pipeline. Label semantics are shared with Compose; rendering uses native
 * Android APIs and does not require an override registry.
 */
class TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.textViewStyle,
) : AppCompatTextView(context, attrs, defStyleAttr), AndroidThemeAware {
    // Framework constructors invoke virtual setters. Until ready, they must remain native.
    private var ready = false
    private var applying = false
    private val initial = TextViewAttributeReader.read(context, attrs, defStyleAttr)
    private val xmlTokens = initial.tokens
    private var runtimeTokens = TextViewTokensOverride()
    private var currentVariant = initial.variant
    private var hasExplicitText = initial.hasText
    private var hasErrorState = initial.error
    private var currentBehavior = TextViewBehavior(
        iconStart = TextViewIconBehavior(contentDescription = initial.iconStartDescription),
        iconEnd = TextViewIconBehavior(contentDescription = initial.iconEndDescription),
    )
    private var componentTokens: TextViewTokens? = null
    private var componentTokensOverride: LabelTokensOverride? = null
    private var explicitTheme: AndroidThemeSnapshot? = null
    private var themeTokens = AndroidThemeResolver.resolve(this)
    private val renderer = TextViewRenderer()
    private val iconBehavior = CompoundDrawableBehaviorController(this,
        canClick = { TextViewBehaviorResolver.canIconClick(currentBehavior, interactionState(), it) },
        canLongClick = { TextViewBehaviorResolver.canIconLongClick(currentBehavior, interactionState(), it) },
    )
    private val initialText: CharSequence = text

    init {
        ready = true
        refreshDrawableState()
        renderTokens()
        renderDefaultText()
        syncIconBehavior()
    }

    fun getVariant(): TextViewVariant = currentVariant
    fun setVariant(variant: TextViewVariant) {
        currentVariant = variant
        renderTokens()
        renderDefaultText()
    }

    fun setTokens(tokens: TextViewTokens?) { componentTokens = tokens; renderTokens() }
    fun setComponentTokensOverride(override: LabelTokensOverride?) { componentTokensOverride = override; renderTokens() }

    fun getTokensOverride(): TextViewTokensOverride = runtimeTokens
    fun setTokensOverride(tokens: TextViewTokensOverride) {
        runtimeTokens = runtimeTokens.merge(tokens)
        renderTokens()
    }

    fun clearOverrides() { runtimeTokens = TextViewTokensOverride(); renderTokens() }
    fun clearColorOverride() { runtimeTokens = runtimeTokens.withoutColors(); renderTokens() }
    fun clearTypographyOverrides() { runtimeTokens = runtimeTokens.withoutTypography(); renderTokens() }

    fun setColor(@ColorRes resource: Int) = setTokensOverride(TextViewTokensOverride(textColor = TextColor.Resource(resource)))
    fun setColorRole(role: TextColorRole) = setTokensOverride(TextViewTokensOverride(textColor = TextColor.Role(role)))
    fun setFont(@FontRes resource: Int) = setTokensOverride(TextViewTokensOverride(font = TextFont.Resource(resource)))
    fun setTypography(role: TextTypographyRole) = setTokensOverride(TextViewTokensOverride(typography = TextTypography.Role(role)))
    fun setTypography(token: TypographyToken) = setTokensOverride(TextViewTokensOverride(typography = TextTypography.Value(token)))
    fun setFont(family: FontFamilyToken) = setTokensOverride(TextViewTokensOverride(font = TextFont.Family(family)))
    fun setIconStart(icon: IconToken?) = setIconStart(icon?.let(ViewIcon::Token) ?: ViewIcon.None)
    fun setIconEnd(icon: IconToken?) = setIconEnd(icon?.let(ViewIcon::Token) ?: ViewIcon.None)
    fun setIconStart(icon: ViewIcon) = setTokensOverride(TextViewTokensOverride(iconStart = icon, showIconStart = icon != ViewIcon.None))
    fun setIconEnd(icon: ViewIcon) = setTokensOverride(TextViewTokensOverride(iconEnd = icon, showIconEnd = icon != ViewIcon.None))
    fun setShowIconStart(show: Boolean) = setTokensOverride(TextViewTokensOverride(showIconStart = show))
    fun setShowIconEnd(show: Boolean) = setTokensOverride(TextViewTokensOverride(showIconEnd = show))
    fun setTextSizeSp(size: Float) = setTextSize(TypedValue.COMPLEX_UNIT_SP, size)

    /** A scoped custom theme can be supplied without changing global state. Null restores inheritance. */
    fun setThemeTokens(theme: AndroidThemeSnapshot?) { explicitTheme = theme; refreshTheme() }
    fun getThemeTokens(): AndroidThemeSnapshot = themeTokens

    fun getBehavior(): TextViewBehavior = currentBehavior
    fun setBehavior(behavior: TextViewBehavior) {
        if (currentBehavior.onClick !== behavior.onClick) super.setOnClickListener(behavior.onClick)
        if (currentBehavior.onLongClick !== behavior.onLongClick) super.setOnLongClickListener(behavior.onLongClick)
        currentBehavior = behavior
        iconBehavior.cancel()
        syncIconBehavior()
        invalidate()
        sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        if (ready) currentBehavior = currentBehavior.copy(onClick = listener)
        super.setOnClickListener(listener)
    }

    override fun setOnLongClickListener(listener: OnLongClickListener?) {
        if (ready) currentBehavior = currentBehavior.copy(onLongClick = listener)
        super.setOnLongClickListener(listener)
    }

    fun setOnIconStartClickListener(listener: OnClickListener?) = setBehavior(currentBehavior.copy(iconStart = currentBehavior.iconStart.copy(onClick = listener)))
    fun setOnIconEndClickListener(listener: OnClickListener?) = setBehavior(currentBehavior.copy(iconEnd = currentBehavior.iconEnd.copy(onClick = listener)))
    fun setOnIconStartLongClickListener(listener: OnLongClickListener?) = setBehavior(currentBehavior.copy(iconStart = currentBehavior.iconStart.copy(onLongClick = listener)))
    fun setOnIconEndLongClickListener(listener: OnLongClickListener?) = setBehavior(currentBehavior.copy(iconEnd = currentBehavior.iconEnd.copy(onLongClick = listener)))
    fun setIconStartContentDescription(description: CharSequence?) = setBehavior(currentBehavior.copy(iconStart = currentBehavior.iconStart.copy(contentDescription = description)))
    fun setIconEndContentDescription(description: CharSequence?) = setBehavior(currentBehavior.copy(iconEnd = currentBehavior.iconEnd.copy(contentDescription = description)))
    fun performIconStartClick(): Boolean = iconBehavior.performClick(CompoundDrawableSlot.Start)
    fun performIconEndClick(): Boolean = iconBehavior.performClick(CompoundDrawableSlot.End)
    fun performIconStartLongClick(): Boolean = iconBehavior.performLongClick(CompoundDrawableSlot.Start)
    fun performIconEndLongClick(): Boolean = iconBehavior.performLongClick(CompoundDrawableSlot.End)

    private fun syncIconBehavior() {
        for (slot in CompoundDrawableSlot.entries) {
            val config = TextViewBehaviorResolver.icon(currentBehavior, slot)
            iconBehavior.setOnClickListener(slot, config.onClick, config.contentDescription)
            iconBehavior.setOnLongClickListener(slot, config.onLongClick, config.contentDescription)
        }
    }

    // Icon gestures have independent accessible actions; calling performClick would activate the text.
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean =
        if (ready && iconBehavior.onTouchEvent(event)) true else super.onTouchEvent(event)

    override fun performAccessibilityAction(action: Int, arguments: Bundle?): Boolean =
        (ready && iconBehavior.performAccessibilityAction(action, arguments)) || super.performAccessibilityAction(action, arguments)

    override fun onDetachedFromWindow() {
        iconBehavior.cancel()
        super.onDetachedFromWindow()
    }

    fun hasError(): Boolean = hasErrorState
    fun setErrorState(error: Boolean) {
        if (hasErrorState == error) return
        hasErrorState = error
        if (!error && getError() != null) super.setError(null, null)
        refreshDrawableState()
        sendAccessibilityEvent(android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
    }

    override fun setError(error: CharSequence?, icon: Drawable?) {
        super.setError(error, icon)
        if (ready) setErrorState(error != null)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (ready) renderColors()
    }

    override fun performClick(): Boolean =
        if (!ready || TextViewBehaviorResolver.canClick(currentBehavior, interactionState())) super.performClick() else false

    override fun callOnClick(): Boolean =
        if (!ready || TextViewBehaviorResolver.canClick(currentBehavior, interactionState())) super.callOnClick() else false

    override fun performLongClick(): Boolean =
        if (!ready || TextViewBehaviorResolver.canLongClick(currentBehavior, interactionState())) super.performLongClick() else false

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        if (!ready) return
        iconBehavior.onInitializeAccessibilityNodeInfo(info)
        info.isContentInvalid = hasErrorState
        if (hasErrorState && info.error.isNullOrEmpty()) info.error = context.getString(R.string.text_view_error)
        if (!TextViewBehaviorResolver.canClick(currentBehavior, interactionState())) {
            info.isClickable = false
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
        }
        if (!TextViewBehaviorResolver.canLongClick(currentBehavior, interactionState())) {
            info.isLongClickable = false
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK)
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        if (hasErrorState) mergeDrawableStates(state, intArrayOf(R.attr.state_error))
        return state
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (ready && !applying) renderColors()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (ready && !applying) hasExplicitText = true
        super.setText(text, type)
    }

    // These framework APIs rebind the existing text internally; they do not supply content.
    override fun setAllCaps(allCaps: Boolean) = applying { super.setAllCaps(allCaps) }
    override fun setTextIsSelectable(selectable: Boolean) = applying { super.setTextIsSelectable(selectable) }
    override fun setSingleLine(singleLine: Boolean) = applying { super.setSingleLine(singleLine) }

    fun clearTextOverride() {
        hasExplicitText = initial.hasText
        if (initial.hasText) {
            applying { super.setText(initialText) }
        } else renderDefaultText()
    }

    override fun setTextColor(color: Int) {
        if (!ready || applying) super.setTextColor(color)
        else setTokensOverride(TextViewTokensOverride(textColor = TextColor.Value(color)))
    }

    override fun setTextColor(colors: ColorStateList) {
        if (!ready || applying) super.setTextColor(colors)
        else setTokensOverride(TextViewTokensOverride(textColor = TextColor.States(colors)))
    }

    override fun setTextSize(unit: Int, size: Float) {
        if (!ready || applying) super.setTextSize(unit, size)
        else setTokensOverride(TextViewTokensOverride(textSize = TextDimension(size, unit)))
    }

    override fun setTypeface(tf: Typeface?) {
        if (!ready || applying) super.setTypeface(tf)
        else setTokensOverride(TextViewTokensOverride(
            font = TextFont.Value(tf ?: Typeface.DEFAULT), weight = (tf ?: Typeface.DEFAULT).weight,
            italic = tf?.isItalic ?: false,
        ))
    }

    override fun setTypeface(tf: Typeface?, style: Int) {
        if (!ready || applying) super.setTypeface(tf, style)
        else setTokensOverride(TextViewTokensOverride(
            font = TextFont.Value(tf ?: Typeface.DEFAULT),
            weight = if (style and Typeface.BOLD != 0) 700 else 400,
            italic = style and Typeface.ITALIC != 0,
        ))
    }

    override fun setLetterSpacing(letterSpacing: Float) {
        if (!ready || applying) super.setLetterSpacing(letterSpacing)
        else setTokensOverride(TextViewTokensOverride(letterSpacingEm = letterSpacing))
    }

    override fun setLineHeight(lineHeight: Int) {
        if (!ready || applying) super.setLineHeight(lineHeight)
        else setTokensOverride(TextViewTokensOverride(lineHeight = TextDimension(lineHeight.toFloat(), TypedValue.COMPLEX_UNIT_PX)))
    }

    override fun setLineHeight(unit: Int, lineHeight: Float) {
        val dimension = TextDimension(lineHeight, unit)
        if (!ready || applying) super.setLineHeight(dimension.pixels(context).roundToInt())
        else setTokensOverride(TextViewTokensOverride(lineHeight = dimension))
    }

    override fun setLineSpacing(add: Float, mult: Float) {
        if (!ready || applying) super.setLineSpacing(add, mult)
        else setTokensOverride(TextViewTokensOverride(lineSpacing = TextLineSpacing(add, mult)))
    }

    override fun setTextAppearance(resId: Int) = setTextAppearance(context, resId)

    @Suppress("DEPRECATION")
    override fun setTextAppearance(context: Context, resId: Int) {
        if (!ready || applying) { super.setTextAppearance(context, resId); return }
        // Native TextAppearance also controls properties outside the DS (allCaps, shadows...).
        applying { super.setTextAppearance(context, resId) }
        setTokensOverride(TextViewAttributeReader.readAppearance(context, resId))
    }

    override fun onAttachedToWindow() { super.onAttachedToWindow(); refreshTheme() }
    override fun onConfigurationChanged(newConfig: Configuration) {
        applying { super.onConfigurationChanged(newConfig) }
        refreshTheme()
        renderDefaultText()
    }

    override fun refreshTheme() {
        if (!ready) return
        themeTokens = explicitTheme ?: AndroidThemeResolver.resolve(this)
        renderTokens()
    }

    private fun interactionState() = TextInteractionState(isEnabled, hasErrorState)
    private fun resolvedTokens() = TextViewTokensResolver.resolve(
        themeTokens, currentVariant, componentTokens, componentTokensOverride, xmlTokens, runtimeTokens,
    )

    private fun renderTokens() {
        if (!ready || applying) return
        val resolved = TextViewTokensRenderResolver.resolve(context, themeTokens, resolvedTokens())
        applying { renderer.render(this, resolved) }
        renderColors()
    }

    private fun renderColors() {
        if (!ready || applying) return
        val color = TextViewTokensRenderResolver.color(context, themeTokens, resolvedTokens(),
            TextInteraction.visualState(interactionState()), drawableState, xmlTokens, runtimeTokens)
        applying { renderer.color(this, color) }
        iconBehavior.onDrawableStateChanged()
    }

    private fun renderDefaultText() {
        if (!hasExplicitText) applying {
            super.setText(TextViewVariantResolver.defaultText(context, currentVariant))
        }
    }

    private inline fun applying(action: () -> Unit) {
        val previous = applying
        applying = true
        try { action() } finally { applying = previous }
    }
}
