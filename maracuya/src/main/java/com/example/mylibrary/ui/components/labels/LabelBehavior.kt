package com.example.mylibrary.ui.components.labels

internal data class LabelBehavior (
    val onClick: (() -> Unit)?,
    val onClickContentDescription: String?,
)