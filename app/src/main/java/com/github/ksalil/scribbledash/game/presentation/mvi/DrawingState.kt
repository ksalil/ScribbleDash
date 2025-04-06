package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.graphics.Color

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPathData: PathData? = null,
    val currentPathDataList: List<PathData> = emptyList(),
    // Stores all the paths to be drawn on the canvas.
    val pathDataList: List<PathData> = emptyList(),
    val undoPaths: List<String> = emptyList(),
    val redoPaths: List<String> = emptyList()
)