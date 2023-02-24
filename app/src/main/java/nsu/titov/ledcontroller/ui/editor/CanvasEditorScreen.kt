package nsu.titov.ledcontroller.ui.editor

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
@Preview
fun CanvasEditorScreen(
    viewModel: CanvasEditorViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState(EditorUiState.Default)

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
                    detectTapGestures(
                        onTap = viewModel::onEditAreaTapped
                    )
                },
            source = PixelCanvasUIState.Test16x8,
            initialOffset = uiState.canvasModifiers.initialOffset,
            rectSpacing = uiState.canvasModifiers.rectSpacing,
            rectSize = uiState.canvasModifiers.rectSize,
            cornerRadius = uiState.canvasModifiers.cornerRadius,
        )
        LazyRow(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            items(uiState.tools) { item: ToolUIModel ->
                FloatingActionButton(
                    onClick = item.onSelect
                ) {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}