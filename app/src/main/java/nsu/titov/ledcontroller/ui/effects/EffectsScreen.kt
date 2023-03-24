package nsu.titov.ledcontroller.ui.effects

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasView

@Composable
fun EffectsScreen(viewModel: EffectsViewModel = viewModel()) {

    val canvasUIState by viewModel.canvasUiState.collectAsState()

    val size = canvasUIState.getMinSizeDp(LocalDensity.current)

    Box(modifier = Modifier.fillMaxSize()) {
        PixelCanvasView(
            modifier = Modifier
                .requiredSize(size.x, size.y)
                .padding(Spacing.Double)
                .align(Alignment.TopCenter),
            source = canvasUIState,
        )
    }
}

@Preview
@Composable
fun EffectsScreenPreview() {
    EffectsScreen()
}