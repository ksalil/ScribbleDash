package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.graphics.Color

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPathData: PathData? = null,
    val pathDataList: List<PathData> = emptyList(),
    val undoStack: List<PathData> = emptyList(),
    val redoStack: List<PathData> = emptyList()
)