package nsu.titov.ledcontroller.ui.effects

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.ledcontroller.domain.edit.effects.EffectsManager
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIS
import nsu.titov.ledcontroller.ui.editor.PixelCanvasMapper

class EffectsViewModel : ViewModel() {

    private val effectsManager: EffectsManager = EffectsManager(viewModelScope)

    private val _canvasUiState = MutableStateFlow(PixelCanvasUIS.Default)
    val canvasUiState = _canvasUiState.asStateFlow()

    private val _toolsUiState = MutableStateFlow(PreviewToolsUIS.Default)
    val toolsUiState = _toolsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            effectsManager.canvas.collect(::onCanvasFromEffectsManager)
        }
    }

    fun onTransform(
        centroid: Offset, pan: Offset, zoom: Float, rotation: Float,
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (rotation != 0f || zoom != 1f) {
            onTransformMultitouch(centroid, pan, zoom)
        }
    }

    private fun onCanvasFromEffectsManager(pixelatedCanvas: PixelatedCanvas) =
        _canvasUiState.update {
            it.copy(
                canvas = PixelCanvasMapper.toUi(pixelatedCanvas)
            )
        }

    fun calculateInitialOffset(screenWidth: Int, screenHeight: Int, density: Density) =
        viewModelScope.launch(Dispatchers.IO) {
            val screenWidthPx = with(density) { screenWidth.dp.toPx() }
            val screenHeightPx = with(density) { screenHeight.dp.toPx() }

            _canvasUiState.value = canvasUiState.value.copy(
                initialOffset = getCanvasInitialOffset(
                    screenWidthPx,
                    screenHeightPx,
                    canvasUiState.value.getMinSizePx()
                )
            )
        }

    private fun getCanvasInitialOffset(
        screenWidth: Float, screenHeight: Float, canvasSize: Offset,
    ): Offset = (Offset(screenWidth, screenHeight) - canvasSize) / 2f

    private suspend fun onTransformMultitouch(centroid: Offset, pan: Offset, zoom: Float) =
        withContext(Dispatchers.IO) {
            if (zoom != 0f || pan != Offset.Zero) {
                val newInitialOffset =
                    pan + (canvasUiState.value.initialOffset - centroid) * zoom + centroid
                val newRectSize = canvasUiState.value.rectSize * zoom
                val newRectOffset = canvasUiState.value.rectSpacing * zoom
                val newCornerRadius = canvasUiState.value.cornerRadius * zoom

                _canvasUiState.value = _canvasUiState.value.copy(
                    initialOffset = newInitialOffset,
                    rectSize = newRectSize,
                    rectSpacing = newRectOffset,
                    cornerRadius = newCornerRadius,
                )
            }
        }

    fun onPause() {
        effectsManager.pausePreview()
        _toolsUiState.update { PreviewToolsUIS(true) }
    }

    fun onResume() {
        effectsManager.resumePreview()
        _toolsUiState.update { PreviewToolsUIS(false) }
    }

    fun onStop() {
        effectsManager.stopPreview()
        _toolsUiState.update { PreviewToolsUIS(true) }
    }

    fun onOpenEffects() {
    }

    fun onAddEffect() {
    }
}