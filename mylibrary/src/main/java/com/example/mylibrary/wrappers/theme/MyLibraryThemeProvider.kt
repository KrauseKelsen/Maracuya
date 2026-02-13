package com.example.mylibrary.wrappers.theme

import android.view.View
import android.view.ViewParent

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