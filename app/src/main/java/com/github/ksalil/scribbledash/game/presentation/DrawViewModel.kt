package com.github.ksalil.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.github.ksalil.scribbledash.core.Constants.UNDO_REDO_COUNT
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingAction
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingState
import com.github.ksalil.scribbledash.game.presentation.mvi.PathData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class DrawViewModel : ViewModel() {

    private val _state = MutableStateFlow(DrawingState())
    val state = _state.asStateFlow()

    fun onAction(action: DrawingAction) {
        when (action) {
            DrawingAction.OnClearCanvasClick -> onClearCanvasClick()
            DrawingAction.OnNewPathStart -> onNewPathStart()
            DrawingAction.OnPathEnd -> onPathEnd()
            DrawingAction.OnRedo -> onRedo()
            DrawingAction.OnUndo -> onUndo()
            is DrawingAction.OnDraw -> onDraw(action.offset)
        }
    }

    private fun onUndo() {
        if (_state.value.undoStack.isEmpty()) return

        val undoPathData = _state.value.undoStack.last()
        _state.update {
            it.copy(
                pathDataList = it.pathDataList - undoPathData,
                undoStack = it.undoStack.dropLast(1),
                redoStack = it.redoStack + undoPathData
            )
        }
    }

    private fun onRedo() {
        if (_state.value.redoStack.isEmpty()) return

        val redoPathData = _state.value.redoStack.last()
        _state.update {
            it.copy(
                pathDataList = it.pathDataList + redoPathData,
                undoStack = it.undoStack + redoPathData,
                redoStack = it.redoStack.dropLast(1)
            )
        }
    }

    private fun onPathEnd() {
        val currentPathData = _state.value.currentPathData ?: return

        // Handle the undo paths list - drop oldest if at capacity
        val updatedUndoStack = if (_state.value.undoStack.size == UNDO_REDO_COUNT) {
            _state.value.undoStack.drop(1) + currentPathData
        } else {
            _state.value.undoStack + currentPathData
        }

        _state.update {
            it.copy(
                currentPathData = null,
                pathDataList = it.pathDataList + currentPathData,
                undoStack = updatedUndoStack,
                redoStack = emptyList()
            )
        }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = _state.value.currentPathData ?: return
        _state.update {
            it.copy(
                currentPathData = currentPathData.copy(
                    paths = currentPathData.paths + offset
                )
            )
        }
    }

    private fun onNewPathStart() {
        _state.update {
            it.copy(
                currentPathData = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = it.selectedColor,
                    paths = emptyList()
                )
            )
        }
    }

    private fun onClearCanvasClick() {
        _state.update {
            it.copy(
                currentPathData = null,
                pathDataList = emptyList(),
                undoStack = emptyList(),
                redoStack = emptyList()
            )
        }
    }
}