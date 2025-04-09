package com.github.ksalil.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ksalil.scribbledash.core.Constants.UNDO_REDO_CAPACITY
import com.github.ksalil.scribbledash.core.DefaultDispatcherProvider
import com.github.ksalil.scribbledash.core.DispatcherProvider
import com.github.ksalil.scribbledash.core.extensions.clone
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingAction
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingState
import com.github.ksalil.scribbledash.game.presentation.mvi.PathData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DrawViewModel(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

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
        val undoStack = _state.value.undoStack
        if (undoStack.isEmpty()) return

        viewModelScope.launch(dispatchers.default) {
            val newUndoStack = undoStack.clone()
            val undoPathData = newUndoStack.removeLast()

            val newRedoStack = _state.value.redoStack.clone()
            newRedoStack.addLast(undoPathData)

            withContext(dispatchers.main) {
                _state.update {
                    it.copy(
                        pathDataList = it.pathDataList - undoPathData,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
        }
    }

    private fun onRedo() {
        val redoStack = _state.value.redoStack
        if (redoStack.isEmpty()) return

        viewModelScope.launch(dispatchers.default) {
            val newRedoStack = redoStack.clone()
            val redoPathData = newRedoStack.removeLast()

            val newUndoStack = _state.value.undoStack.clone()
            newUndoStack.addLast(redoPathData)

            withContext(dispatchers.main) {
                _state.update {
                    it.copy(
                        pathDataList = it.pathDataList + redoPathData,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
        }
    }

    private fun onPathEnd() {
        val currentPathData = _state.value.currentPathData ?: return

        viewModelScope.launch(dispatchers.default) {
            val newUndoStack = _state.value.undoStack.clone()
            if (newUndoStack.size == UNDO_REDO_CAPACITY) {
                newUndoStack.removeFirst()
            }
            newUndoStack.addLast(currentPathData)

            withContext(dispatchers.main) {
                _state.update {
                    it.copy(
                        currentPathData = null,
                        pathDataList = it.pathDataList + currentPathData,
                        undoStack = newUndoStack,
                        redoStack = ArrayDeque(UNDO_REDO_CAPACITY)
                    )
                }
            }
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
                undoStack = ArrayDeque(UNDO_REDO_CAPACITY),
                redoStack = ArrayDeque(UNDO_REDO_CAPACITY)
            )
        }
    }
}

