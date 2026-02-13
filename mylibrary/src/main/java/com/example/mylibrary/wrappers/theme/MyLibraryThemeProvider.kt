package com.example.mylibrary.wrappers.theme

import android.view.View
import android.view.ViewParent

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * `MyLibraryThemeProvider` localiza el host de tema (`MyLibraryThemeWrp`) en tiempo de
 * ejecución recorriendo la jerarquía padre de una View wrapper.
 *
 * Permite que cada componente wrapper permanezca desacoplado de la estructura exacta
 * del layout, siempre que exista un `MyLibraryThemeWrp` en algún nivel superior.
 *
 * Función:
 * - Buscar de forma ascendente (`parent -> parent`) dentro del árbol de Views.
 * - Retornar la primera instancia de `MyLibraryThemeWrp` encontrada.
 * - Devolver `null` cuando no hay host de tema y habilitar fallback sin theming explícito.
 *
 * Relación con otras clases:
 * - `PrimaryButtonWrp` invoca `findFrom(this)` antes de renderizar Compose.
 * - `MyLibraryThemeWrp` es el contenedor objetivo de búsqueda.
 *
 * @author Krause Kelsen
 * @since 11-13-2025
 * @version 1.5.4
 *
 * @see com.example.mylibrary.wrappers.theme.MyLibraryThemeWrp
 */
internal object MyLibraryThemeProvider {

    fun findFrom(view: View): MyLibraryThemeWrp? {
        var currentParent: ViewParent? = view.parent
        while (currentParent != null) {
            if (currentParent is MyLibraryThemeWrp) {
                return currentParent
            }
            currentParent = currentParent.parent
        }
        return null
    }
}
