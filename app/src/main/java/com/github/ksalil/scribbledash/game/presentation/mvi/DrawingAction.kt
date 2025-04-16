package com.github.ksalil.scribbledash.game.presentation.mvi

import androidx.compose.ui.geometry.Offset

sealed interface DrawingAction {
    data object OnNewPathStart : DrawingAction
    data class OnDraw(val offset: Offset) : DrawingAction
    data object OnPathEnd : DrawingAction
    //data class OnSelectColor(val color: Color): DrawingAction
    data object OnClearCanvasClick : DrawingAction
    data object OnRedo : DrawingAction
    data object OnUndo: DrawingAction
}