package nsu.titov.ledcontroller.ui.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class CanvasEditorViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<EditorUiState> = MutableStateFlow(EditorUiState.Default)
    val uiState = _uiState.asStateFlow()

    private val selectedColor = Color.Yellow

    fun onTransform(
        centroid: Offset, pan: Offset, zoom: Float, rotation: Float
    ) {
        if (rotation != 0f || zoom != 1f) {
            onTransformMultitouch(centroid, pan, zoom)
        } else {
            onDraw(centroid)
        }
    }

    fun onPixelTapped(offset: Offset) = onDraw(offset)

    private fun onTransformMultitouch(centroid: Offset, pan: Offset, zoom: Float) {
        if (zoom != 0f || pan != Offset.Zero) {
            val newInitialOffset =
                pan + (uiState.value.canvasModifiers.initialOffset - centroid) * zoom + centroid
            val newRectSize = uiState.value.canvasModifiers.rectSize * zoom
            val newRectOffset = uiState.value.canvasModifiers.rectSpacing * zoom
            val newCornerRadius = uiState.value.canvasModifiers.cornerRadius * zoom

            _uiState.value = _uiState.value.copy(
                canvasModifiers = EditorUiState.CanvasModifiers(
                    initialOffset = newInitialOffset,
                    rectSize = newRectSize,
                    rectSpacing = newRectOffset,
                    cornerRadius = newCornerRadius,
                )
            )
        }
    }

    private fun onDraw(tapCoords: Offset) = uiState.value.canvasModifiers.run {
        val tap = tapCoords - initialOffset
        val targetingPixelX = (tap.x / (rectSpacing.x + rectSize.width)).roundToInt()
        val targetingPixelY = (tap.y / (rectSpacing.y + rectSize.height)).roundToInt()

        if (uiState.value.canvas[targetingPixelX, targetingPixelY] != selectedColor) {
            val oldPixels = uiState.value.canvas.pixels.toMutableList()
            oldPixels[targetingPixelY * uiState.value.canvas.height + targetingPixelX] =
                selectedColor

            _uiState.value = uiState.value.copy(
                canvas = uiState.value.canvas.copy(pixels = oldPixels)
            )
        }
    }
}