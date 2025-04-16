package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.graphics.Color
import com.github.ksalil.scribbledash.core.Constants.UNDO_REDO_CAPACITY
import com.github.ksalil.scribbledash.game.presentation.model.PathData

data class DrawingState(
    val selectedColor: Color = Color.Black,
    val currentPathData: PathData? = null,
    val pathDataList: List<PathData> = emptyList(),
    val undoStack: ArrayDeque<PathData> = ArrayDeque(initialCapacity = UNDO_REDO_CAPACITY),
    val redoStack: ArrayDeque<PathData> = ArrayDeque(initialCapacity = UNDO_REDO_CAPACITY),
)