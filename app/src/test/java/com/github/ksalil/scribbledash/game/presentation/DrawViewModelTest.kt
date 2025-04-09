package com.github.ksalil.scribbledash.game.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.github.ksalil.scribbledash.core.Constants
import com.github.ksalil.scribbledash.core.TestDispatcherProvider
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DrawViewModelTest {

    private lateinit var viewModel: DrawViewModel
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        // Given: A test environment with test dispatchers
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = DrawViewModel(TestDispatcherProvider(testDispatcher))
    }

    @After
    fun tearDown() {
        // Clean up test environment
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is empty`() {
        // Given: A newly initialized view model

        // When: We get the initial state
        val initialState = viewModel.state.value

        // Then: The state should have default values
        assertEquals(Color.Black, initialState.selectedColor)
        assertNull(initialState.currentPathData)
        assertTrue(initialState.pathDataList.isEmpty())
        assertTrue(initialState.undoStack.isEmpty())
        assertTrue(initialState.redoStack.isEmpty())
    }

    @Test
    fun `onNewPathStart creates new path with current color`() {
        // Given: A view model with default state

        // When: We start a new path
        viewModel.onAction(DrawingAction.OnNewPathStart)

        // Then: A new path with current color should be created
        val state = viewModel.state.value
        val currentPath = state.currentPathData

        assertEquals(Color.Black, currentPath?.color)
        assertTrue(currentPath?.paths?.isEmpty() ?: false)
    }

    @Test
    fun `onDraw adds point to current path`() {
        // Given: A view model with a new path started
        viewModel.onAction(DrawingAction.OnNewPathStart)

        // When: We add points to the path
        val offset1 = Offset(10f, 20f)
        val offset2 = Offset(15f, 25f)

        viewModel.onAction(DrawingAction.OnDraw(offset1))
        viewModel.onAction(DrawingAction.OnDraw(offset2))

        // Then: The points should be added to the current path
        val currentPath = viewModel.state.value.currentPathData
        assertEquals(2, currentPath?.paths?.size)
        assertEquals(offset1, currentPath?.paths?.get(0))
        assertEquals(offset2, currentPath?.paths?.get(1))
    }

    @Test
    fun `onPathEnd adds current path to pathDataList and undoStack`() = runTest {
        // Given: A view model with a started path containing a point
        viewModel.onAction(DrawingAction.OnNewPathStart)

        val offset = Offset(10f, 20f)
        viewModel.onAction(DrawingAction.OnDraw(offset))

        // When: We end the path
        viewModel.onAction(DrawingAction.OnPathEnd)

        // Ensure coroutines complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: The path should be added to pathDataList and undoStack, and currentPath should be null
        val state = viewModel.state.value

        // Current path should be null
        assertNull(state.currentPathData)

        // Path should be in pathDataList
        assertEquals(1, state.pathDataList.size)
        assertEquals(offset, state.pathDataList[0].paths[0])

        // Path should be in undoStack
        assertEquals(1, state.undoStack.size)

        // Redo stack should be empty
        assertTrue(state.redoStack.isEmpty())
    }

    @Test
    fun `onClearCanvasClick clears all paths and stacks`() {
        // Given: A view model with a completed path
        viewModel.onAction(DrawingAction.OnNewPathStart)
        viewModel.onAction(DrawingAction.OnDraw(Offset(10f, 20f)))
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        // When: We clear the canvas
        viewModel.onAction(DrawingAction.OnClearCanvasClick)

        // Then: All paths and stacks should be cleared
        val state = viewModel.state.value
        assertNull(state.currentPathData)
        assertTrue(state.pathDataList.isEmpty())
        assertTrue(state.undoStack.isEmpty())
        assertTrue(state.redoStack.isEmpty())
    }

    @Test
    fun `onUndo removes last path and adds to redoStack`() = runTest {
        // Given: A view model with a completed path
        viewModel.onAction(DrawingAction.OnNewPathStart)
        viewModel.onAction(DrawingAction.OnDraw(Offset(10f, 20f)))
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        // Get the path for comparison
        val addedPath = viewModel.state.value.pathDataList[0]

        // When: We undo the path
        viewModel.onAction(DrawingAction.OnUndo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: The path should be removed from pathDataList and undoStack, and added to redoStack
        val state = viewModel.state.value

        // Path should be removed from pathDataList
        assertTrue(state.pathDataList.isEmpty())

        // Path should be removed from undoStack
        assertTrue(state.undoStack.isEmpty())

        // Path should be in redoStack
        assertEquals(1, state.redoStack.size)
        assertEquals(addedPath, state.redoStack.last())
    }

    @Test
    fun `onRedo adds path back from redoStack`() = runTest {
        // Given: A view model with an undone path
        viewModel.onAction(DrawingAction.OnNewPathStart)
        viewModel.onAction(DrawingAction.OnDraw(Offset(10f, 20f)))
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onAction(DrawingAction.OnUndo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Get the path from redoStack for comparison
        val pathToRedo = viewModel.state.value.redoStack.last()

        // When: We redo the path
        viewModel.onAction(DrawingAction.OnRedo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: The path should be added back to pathDataList and undoStack, and removed from redoStack
        val state = viewModel.state.value

        // Path should be added back to pathDataList
        assertEquals(1, state.pathDataList.size)
        assertEquals(pathToRedo, state.pathDataList[0])

        // Path should be added back to undoStack
        assertEquals(1, state.undoStack.size)
        assertEquals(pathToRedo, state.undoStack.last())

        // Path should be removed from redoStack
        assertTrue(state.redoStack.isEmpty())
    }

    @Test
    fun `undoStack has maximum capacity`() = runTest {
        // Given: A view model with paths exceeding the undo capacity
        // Create and finish paths up to capacity + 1
        repeat(Constants.UNDO_REDO_CAPACITY + 1) { index ->
            viewModel.onAction(DrawingAction.OnNewPathStart)
            viewModel.onAction(DrawingAction.OnDraw(Offset(index.toFloat(), index.toFloat())))
            viewModel.onAction(DrawingAction.OnPathEnd)
            testDispatcher.scheduler.advanceUntilIdle()
        }

        // When: We check the undoStack

        // Then: UndoStack should have reached maximum capacity and first path should be removed
        assertEquals(Constants.UNDO_REDO_CAPACITY, viewModel.state.value.undoStack.size)

        // First path should have been removed from undoStack
        // The first path in pathDataList should not be in undoStack
        val firstPath = viewModel.state.value.pathDataList.first()
        assertTrue(!viewModel.state.value.undoStack.contains(firstPath))
    }

    @Test
    fun `onUndo does nothing when undoStack is empty`() = runTest {
        // Given: A view model with empty undoStack
        val initialState = viewModel.state.value

        // When: We try to undo with empty stack
        viewModel.onAction(DrawingAction.OnUndo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: State should remain unchanged
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `onRedo does nothing when redoStack is empty`() = runTest {
        // Given: A view model with empty redoStack
        val initialState = viewModel.state.value

        // When: We try to redo with empty stack
        viewModel.onAction(DrawingAction.OnRedo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: State should remain unchanged
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `onDraw does nothing when no current path`() {
        // Given: A view model with no current path
        val initialState = viewModel.state.value

        // When: We try to draw without a current path
        viewModel.onAction(DrawingAction.OnDraw(Offset(10f, 20f)))

        // Then: State should remain unchanged
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `onPathEnd does nothing when no current path`() = runTest {
        // Given: A view model with no current path
        val initialState = viewModel.state.value

        // When: We try to end path without a current path
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: State should remain unchanged
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `redoStack gets cleared when starting a new path after undo`() = runTest {
        // Given: A view model with a path that has been undone
        viewModel.onAction(DrawingAction.OnNewPathStart)
        viewModel.onAction(DrawingAction.OnDraw(Offset(10f, 20f)))
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.onAction(DrawingAction.OnUndo)
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify redoStack has an item
        assertEquals(1, viewModel.state.value.redoStack.size)

        // When: We start a new path after undoing
        viewModel.onAction(DrawingAction.OnNewPathStart)
        viewModel.onAction(DrawingAction.OnDraw(Offset(30f, 40f)))
        viewModel.onAction(DrawingAction.OnPathEnd)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then: redoStack should be empty
        assertTrue(viewModel.state.value.redoStack.isEmpty())
    }
}