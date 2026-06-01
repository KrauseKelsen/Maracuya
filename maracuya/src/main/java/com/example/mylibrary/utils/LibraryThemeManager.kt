package com.example.mylibrary.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.mylibrary.theme.LibraryThemeSet
import com.example.mylibrary.utils.composeadapters.ColorComposeAdapter

/**
 * Administra la sincronización del tema de la librería con los elementos visuales
 * del sistema Android (status bar, navigation bar, fondo de ventana).
 *
 * ### Propósito
 * - Asegurar coherencia visual entre el Design System (DS) y el sistema.
 * - Permitir aplicar colores del tema sin depender de Compose.
 * - Resolver el modo oscuro/claro según parámetros o configuración del sistema.
 *
 * ### Características
 * - Independiente de Compose.
 * - Seguro de invocar desde cualquier capa (solo requiere un `Context`).
 * - Ajusta colores e iconografía de barras del sistema automáticamente.
 *
 * ### Uso recomendado (desde Compose):
 * ```kotlin
 * LibraryThemeManager.syncSystemBars(
 *     context = context,
 *     themeSet = LibraryThemes.Maracuya,
 *     isDark = true
 * )
 * ```
 *
 * @author Krause Kelsen
 * @since 11-2025
 * @version 1.5.3
 *
 * @see com.example.mylibrary.utils.LibraryThemeManager
 */
object LibraryThemeManager {

    /**
     * Sincroniza las barras del sistema con el color base del tema actual.
     *
     * ### Lógica de resolución del modo:
     * 1. Si `isDark` ≠ null → se usa directamente.
     * 2. Si `isDark` = null → se infiere del modo del sistema (`UI_MODE_NIGHT`).
     *
     * ### Acciones realizadas:
     * - Setea color de `statusBarColor`, `navigationBarColor` y `decorView`.
     * - Ajusta iconos claros/oscuros según el fondo.
     * - Ajusta el comportamiento de `decorFitsSystemWindows`.
     *
     * @param context Contexto actual; si no es una `Activity` → no hace nada.
     * @param themeSet Tema definido por la librería para obtener tokens light/dark.
     * @param isDark Si `true` aplica tokens dark; si `false` tokens light; si `null` se resuelve automáticamente.
     *
     * @see WindowInsetsControllerCompat
     * @see LibraryThemeSet
     */
    fun syncSystemBars(
        context: Context,
        themeSet: LibraryThemeSet,
        isDark: Boolean? = null
    ) {
        val activity = context as? Activity ?: return
        val window = activity.window

        val finalDark = when {
            isDark != null -> isDark
            else -> {
                val uiMode = activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                uiMode == Configuration.UI_MODE_NIGHT_YES
            }
        }

        val backgroundColorInt = if (finalDark) {
            ColorComposeAdapter.toArgb(themeSet.darkTokens.bgBase)
        } else {
            ColorComposeAdapter.toArgb(themeSet.lightTokens.bgBase)
        }

        window.statusBarColor = backgroundColorInt
        window.navigationBarColor = backgroundColorInt
        window.decorView.setBackgroundColor(backgroundColorInt)

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !finalDark
        insetsController.isAppearanceLightNavigationBars = !finalDark

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}