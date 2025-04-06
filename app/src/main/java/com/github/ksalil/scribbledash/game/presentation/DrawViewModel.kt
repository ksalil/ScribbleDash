package com.github.ksalil.scribbledash.game.presentation

import android.util.Log
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
        if (_state.value.undoPaths.isEmpty()) return
        val undoPathId = _state.value.undoPaths.last()
        val undoPathData = _state.value.pathDataList.find {
            it.id == undoPathId
        } ?: return
        _state.update {
            it.copy(
                currentPathDataList = it.currentPathDataList - undoPathData,
                undoPaths = it.undoPaths.dropLast(1),
                redoPaths = it.redoPaths + undoPathId
            )
        }
    }

    private fun onRedo() {
        if (_state.value.redoPaths.isEmpty()) return
        val redoPathId = _state.value.redoPaths.last()
        val redoPathData = _state.value.pathDataList.find {
            it.id == redoPathId
        } ?: return
        _state.update {
            it.copy(
                currentPathDataList = it.currentPathDataList + redoPathData,
                undoPaths = it.undoPaths + redoPathId,
                redoPaths = it.redoPaths.dropLast(1)
            )
        }
    }

    private fun onPathEnd() {
        val currentPathData = _state.value.currentPathData ?: return

        // Handle the undo paths list - drop oldest if at capacity
        val updatedUndoPaths = if (_state.value.undoPaths.size == UNDO_REDO_COUNT) {
            _state.value.undoPaths.drop(1) + currentPathData.id
        } else {
            _state.value.undoPaths + currentPathData.id
        }

        _state.update {
            it.copy(
                currentPathData = null,
                currentPathDataList = it.currentPathDataList + currentPathData,
                pathDataList = it.pathDataList + currentPathData,
                undoPaths = updatedUndoPaths,
                redoPaths = emptyList()
            )
        }

        Log.d("DrawViewModel", "onPathEnd Undo paths: ${_state.value.undoPaths}")
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
                currentPathDataList = emptyList(),
                pathDataList = emptyList(),
                undoPaths = emptyList(),
                redoPaths = emptyList()
            )
        }
    }
}