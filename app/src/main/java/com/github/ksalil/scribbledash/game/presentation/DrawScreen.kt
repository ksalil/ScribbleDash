package com.github.ksalil.scribbledash.game.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.ksalil.scribbledash.R
import com.github.ksalil.scribbledash.game.DifficultyLevel
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingAction
import com.github.ksalil.scribbledash.game.presentation.mvi.DrawingState
import com.github.ksalil.scribbledash.game.presentation.mvi.PathData
import com.github.ksalil.scribbledash.ui.theme.ScribbleDashTheme
import com.github.ksalil.scribbledash.ui.theme.Success
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawScreen(
    modifier: Modifier = Modifier,
    difficultyLevel: DifficultyLevel,
    onCloseClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = onCloseClicked
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close_circle),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color(0xFFFFF1E2),
                        )
                    )
                )
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val viewModel = viewModel<DrawViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            Text(
                text = stringResource(R.string.draw_screen_title),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            DrawingCanvas(
                modifier = Modifier
                    .width(360.dp)
                    .height(360.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background
                    ),
                currentPath = state.currentPathData,
                onAction = viewModel::onAction,
                paths = state.currentPathDataList
            )
            DrawingControls(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 24.dp),
                state = state,
                onRedoClick = {
                    viewModel.onAction(action = DrawingAction.OnRedo)
                },
                onUndoClick = {
                    viewModel.onAction(action = DrawingAction.OnUndo)
                },
                onCanvasClearClick = {
                    viewModel.onAction(action = DrawingAction.OnClearCanvasClick)
                }
            )
        }
    }
}

@Composable
private fun DrawingCanvas(
    paths: List<PathData>,
    currentPath: PathData?,
    onAction: (DrawingAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridImage = painterResource(id = R.drawable.ic_grid)
    ElevatedCard(
        modifier = modifier
            .aspectRatio(1f)
            .background(color = Color.Transparent),
        colors = CardDefaults.outlinedCardColors(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(330.dp)
                    .height(330.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                            .copy(
                                alpha = 0.2f
                            ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = modifier
                        .background(color = Color.White)
                        .drawBehind {
                            with(gridImage) {
                                draw(
                                    size = Size(size.width, size.height)
                                )
                            }
                        }
                        .clipToBounds()
                        .pointerInput(true) {
                            detectDragGestures(
                                onDragStart = {
                                    onAction(DrawingAction.OnNewPathStart)
                                },
                                onDrag = { change, _ ->
                                    onAction(DrawingAction.OnDraw(change.position))
                                },
                                onDragEnd = {
                                    onAction(DrawingAction.OnPathEnd)
                                },
                                onDragCancel = {
                                    onAction(DrawingAction.OnPathEnd)
                                }
                            )
                        }
                ) {
                    paths.fastForEach { pathData ->
                        drawPath(
                            path = pathData.paths,
                            color = pathData.color,
                        )
                    }
                    currentPath?.let {
                        drawPath(
                            path = it.paths,
                            color = it.color
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawingControls(
    state: DrawingState,
    onCanvasClearClick: () -> Unit,
    onUndoClick: () -> Unit,
    onRedoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilledIconButton(
            modifier = Modifier.size(64.dp),
            enabled = state.undoPaths.isNotEmpty(),
            colors = IconButtonDefaults.filledIconButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(
                    alpha = 0.4f
                ),
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            shape = RoundedCornerShape(24.dp),
            onClick = onUndoClick
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_undo),
                contentDescription = "Undo",
                contentScale = ContentScale.Crop
            )
        }
        FilledIconButton(
            modifier = Modifier.size(64.dp),
            enabled = state.redoPaths.isNotEmpty(),
            colors = IconButtonDefaults.iconButtonColors(
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow.copy(
                    alpha = 0.4f
                ),
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            shape = RoundedCornerShape(24.dp),
            onClick = onRedoClick
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_redo),
                contentDescription = "Redo",
                contentScale = ContentScale.Crop
            )
        }
        ScribbleDashButton(
            enabled = state.currentPathDataList.isNotEmpty(),
            onClick = onCanvasClearClick
        )
    }
}

@Composable
fun ScribbleDashButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 4.dp,
            color = Color.White
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Success,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = stringResource(R.string.clear_canvas),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.8f
            )
        )
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for (i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DrawScreenPreview() {
    ScribbleDashTheme {
        DrawScreen(
            difficultyLevel = DifficultyLevel.BEGINNER,
            onCloseClicked = {}
        )
    }
}