package cruxui.android.maracuya.wrappers.components.buttons.simple

import androidx.compose.runtime.Composable
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensOverride
import java.util.concurrent.ConcurrentHashMap

/**
 * Registry used by XML wrappers to resolve a named ButtonTokensOverride built in Kotlin.
 *
 * XML cannot instantiate Kotlin objects directly. Instead, the host app registers a
 * composable provider once and references it by name from XML:
 *
 * ```kotlin
 * ButtonTokensOverrideRegistry.register("mytokensoverride") {
 *     val colors = LocalLibraryColorTokens.current
 *     ButtonTokensOverride(
 *         contentColor = colors.fgDefault,
 *         borderContainerColor = colors.fgDefault,
 *     )
 * }
 * ```
 *
 * ```xml
 * app:buttonTokensOverride="mytokensoverride"
 * ```
 */
object ButtonTokensOverrideRegistry {

    private val providers = ConcurrentHashMap<String, @Composable () -> ButtonTokensOverride>()

    /**
     * Registers or replaces a named override provider.
     *
     * Use this before inflating an XML layout that references the same name with
     * `app:buttonTokensOverride`. The provider is composable so it can read
     * CompositionLocals such as `LocalLibraryColorTokens.current` when the wrapper renders.
     *
     * Example:
     * ```kotlin
     * ButtonTokensOverrideRegistry.register("mytokensoverride") {
     *     val colors = LocalLibraryColorTokens.current
     *     ButtonTokensOverride(
     *         contentColor = colors.fgDefault,
     *         borderContainerColor = colors.fgDefault,
     *     )
     * }
     * ```
     *
     * XML usage:
     * ```xml
     * app:buttonTokensOverride="mytokensoverride"
     * ```
     */
    fun register(
        name: String,
        tokensOverride: ButtonTokensOverride,
    ) {
        providers[name.normalizedKey()] = { tokensOverride }
    }

    /**
     * Removes a previously registered provider.
     *
     * Use this when a preview, screen, or test owns a temporary override and should not leave
     * global state behind after it is disposed.
     *
     * Example:
     * ```kotlin
     * ButtonTokensOverrideRegistry.unregister("mytokensoverride")
     * ```
     */
    fun unregister(name: String) {
        providers.remove(name.normalizedKey())
    }

    /**
     * Removes every registered provider.
     *
     * This is mostly useful for tests or demo screens that need a clean registry before
     * registering their own examples. Avoid calling it from feature code that does not own
     * all registered overrides.
     *
     * Example:
     * ```kotlin
     * ButtonTokensOverrideRegistry.clear()
     * ```
     */
    fun clear() {
        providers.clear()
    }

    @Composable
    fun resolve(name: String?): ButtonTokensOverride? {
        val key = name?.normalizedKey().orEmpty()
        if (key.isEmpty()) return null
        return providers[key]?.invoke()
    }

    private fun String.normalizedKey(): String = trim().lowercase()
}
