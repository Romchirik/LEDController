package nsu.titov.ledcontroller.ui.editor

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.PixelCanvas

@Composable
@Preview
fun FrameEditorScreen(
    viewModel: CanvasEditorViewModel = viewModel()
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
            source = canvasUiState,
        )


    }
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(
            modifier = Modifier.padding(Spacing.Default),
            painter = painterResource(id = R.drawable.ic_baseline_undo_24),
            contentDescription = "",
        )

    }
}