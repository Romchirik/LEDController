package nsu.titov.ledcontroller.ui.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.domain.edit.tools.ToolType
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasView
import nsu.titov.ledcontroller.ui.custom.icons.ColorSelectorIcon
import nsu.titov.ledcontroller.ui.custom.icons.SelectableIcon

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview
fun FrameEditorScreen(
    viewModel: CanvasEditorViewModel = viewModel(),
) {

    val canvasUiState by viewModel.canvasUiState.collectAsState()
    val toolsUiState by viewModel.toolsUiState.collectAsState()
    val colorSelectionUiState by viewModel.colorSelectionOpened.collectAsState()

    val config = LocalConfiguration.current
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        val screenWidth = config.screenWidthDp
        val screenHeight = config.screenHeightDp

        viewModel.calculateInitialOffset(screenWidth, screenHeight, density)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        colorSelectionUiState.let {
            if (it is ColorSelectorUiState.Shown) {
                ColorSelectorDialog(
                    onDismissRequest = viewModel::onDismissColorSelectorDialog,
                    onColorChange = viewModel::onColorChange,
                    initialColor = it.initialColor
                )
            }
        }

        PixelCanvasView(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        var pointers = 0
                        var canceled = false
                        while (true) {
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> {
                                    when (pointers) {
                                        0 -> {
                                            viewModel.onPress()
                                            canceled = false
                                        }
                                        1 -> {
                                            viewModel.onCancelPress()
                                            canceled = true
                                        }
                                    }
                                    pointers++
                                }
                                PointerEventType.Release -> {
                                    when (pointers) {
                                        1 -> {
                                            viewModel.onDepress()
                                            canceled = false
                                        }
                                    }
                                    pointers--
                                }
                                PointerEventType.Move -> {
                                    if (pointers == 1 && !canceled) {
                                        val change = event.changes[0]
                                        if (change.historical.isEmpty()) {
                                            viewModel.onEditAreaTap(change.position)
                                        } else {
                                            viewModel.onEditAreaFastSwipe(change.historical.map { it.position })
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                .pointerInput(Unit) {
                    detectTransformGestures(
                        panZoomLock = true,
                        onGesture = viewModel::onTransform,
                    )
                },
            source = canvasUiState,
        )


        if (toolsUiState.undoAvailable) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(Spacing.Double)
                    .size(Spacing.Triple),
                onClick = viewModel::onUndo,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_undo_24),
                    contentDescription = "",
                )
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Spacing.Double)
                .size(Spacing.Triple),
            onClick = viewModel::onReject,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = "",
            )
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Spacing.Double)
                .size(Spacing.Triple),
            onClick = viewModel::onApply,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_check_24),
                contentDescription = "",
            )
        }

        Surface(
            modifier = Modifier
                .padding(Spacing.Double)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = CircleShape
        ) {
            Row(
                modifier = Modifier.padding(
                    start = Spacing.Half,
                    top = Spacing.Half,
                    bottom = Spacing.Half,
                )
            ) {
                SelectableIcon(
                    selected = false,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable(onClick = viewModel::onColorSelectorClicked),
                ) {
                    ColorSelectorIcon(
                        modifier = Modifier.padding(4.dp), color = Color.Yellow
                    )
                }
                SelectableIcon(
                    selected = toolsUiState.selectedTool == ToolType.FreeDraw,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.FreeDraw) },
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_draw_24),
                        contentDescription = "",
                    )
                }
                SelectableIcon(
                    selected = toolsUiState.selectedTool == ToolType.DrawRect,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.DrawRect) },
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_square_24),
                        contentDescription = "",
                    )
                }
                SelectableIcon(
                    selected = toolsUiState.selectedTool == ToolType.DrawLine,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.DrawLine) },
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_line_24),
                        contentDescription = "",
                    )
                }
                SelectableIcon(
                    selected = toolsUiState.selectedTool == ToolType.Erase,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.Erase) },
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_edit_off_24),
                        contentDescription = "",
                    )
                }
                SelectableIcon(
                    selected = toolsUiState.selectedTool == ToolType.Ellipse,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.Ellipse) },
                ) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_empty_circle_24),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}