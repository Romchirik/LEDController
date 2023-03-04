package nsu.titov.ledcontroller.ui.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.domain.model.tools.ToolType
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvas
import nsu.titov.ledcontroller.ui.custom.icons.ColorSelectorIcon
import nsu.titov.ledcontroller.ui.custom.icons.SelectableIcon

@Composable
@Preview
fun FrameEditorScreen(
    viewModel: CanvasEditorViewModel = viewModel(),
) {

    val canvasUiState by viewModel.canvasUiState.collectAsState()
    val toolsUiState by viewModel.toolsUiState.collectAsState()

    val config = LocalConfiguration.current
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        val screenWidth = config.screenWidthDp
        val screenHeight = config.screenHeightDp

        viewModel.calculateInitialOffset(screenWidth, screenHeight, density)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PixelCanvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures(
                        panZoomLock = true,
                        onGesture = viewModel::onTransform,
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(onTap = viewModel::onEditAreaTapped)
                },
            source = canvasUiState,
        )


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
                        .clickable { viewModel.onToolClicked(ToolType.ColorSelector) },
                ) {
                    ColorSelectorIcon(
                        modifier = Modifier.padding(4.dp),
                        color = Color.Yellow
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
                    selected = toolsUiState.selectedTool == ToolType.DrawCircle,
                    modifier = Modifier
                        .padding(end = Spacing.Half)
                        .clickable { viewModel.onToolClicked(ToolType.DrawCircle) },
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