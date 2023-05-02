package nsu.titov.ledcontroller.ui.effects

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.data.repository.LedPanelRepositoryImpl
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIS
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasView

@Composable
fun EffectsScreen() {
    val context = LocalContext.current
    val viewModel = viewModel {
        EffectsViewModel(LedPanelRepositoryImpl(context))
    }

    val colorSelectionUiState by viewModel.effectsEditorOpened.collectAsState()
    val toolsUiState by viewModel.toolsUiState.collectAsState()
    val canvasUIState by viewModel.canvasUiState.collectAsState(
        PixelCanvasUIS.withPattern(
            PixelatedCanvas.Default
        )
    )

    val config = LocalConfiguration.current
    val density = LocalDensity.current

    LaunchedEffect(canvasUIState.canvas.height, canvasUIState.canvas.width) {
        val screenWidth = config.screenWidthDp
        val screenHeight = config.screenHeightDp

        viewModel.calculateInitialOffset(screenWidth, screenHeight, density)
    }

    if (colorSelectionUiState) {
        EffectsEditDialog(
            onDismissRequest = viewModel::onCloseEffects,
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        PixelCanvasView(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures(
                        panZoomLock = true,
                        onGesture = viewModel::onTransform,
                    )
                },
            source = canvasUIState,
        )

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
            Row {
                if (toolsUiState.paused) {
                    Icon(
                        modifier = Modifier
                            .padding(Spacing.Single)
                            .clickable(onClick = viewModel::onResume),
                        painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                        contentDescription = "",
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .padding(Spacing.Single)
                            .clickable(onClick = viewModel::onPause),
                        painter = painterResource(id = R.drawable.ic_baseline_pause_24),
                        contentDescription = "",
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(Spacing.Single)
                        .clickable(onClick = viewModel::onStop),
                    painter = painterResource(id = R.drawable.ic_baseline_stop_24),
                    contentDescription = "",
                )
                Icon(
                    modifier = Modifier
                        .padding(Spacing.Single)
                        .clickable(onClick = viewModel::onOpenEffects),
                    painter = painterResource(id = R.drawable.ic_baseline_auto_fix_high_24),
                    contentDescription = "",
                )
                Icon(
                    modifier = Modifier
                        .padding(Spacing.Single)
                        .clickable(onClick = viewModel::onAddEffect),
                    painter = painterResource(id = R.drawable.ic_baseline_playlist_add_24),
                    contentDescription = "",
                )
            }
        }

    }
}

@Preview
@Composable
fun EffectsScreenPreview() {
    EffectsScreen()
}