package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.graphics.Color
import com.github.ksalil.scribbledash.core.Constants

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPathData: PathData? = null,
    val pathDataList: List<PathData> = emptyList(),
    val undoStack: ArrayDeque<PathData> = ArrayDeque(Constants.UNDO_REDO_COUNT),
    val redoStack: ArrayDeque<PathData> = ArrayDeque(Constants.UNDO_REDO_COUNT),
)