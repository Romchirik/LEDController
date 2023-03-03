package nsu.titov.ledcontroller.ui.editor

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nsu.titov.ledcontroller.domain.edit.CanvasEditManager
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.custom.PixelCanvasUIState
import nsu.titov.ledcontroller.ui.custom.PixelsSource

class CanvasEditorViewModel(
    private val editor: CanvasEditManager = CanvasEditManager(PixelatedCanvas.Default),
) : ViewModel() {

    private val _canvasUiState: MutableStateFlow<PixelCanvasUIState> =
        MutableStateFlow(PixelCanvasUIState.Default)
    val canvasUiState = _canvasUiState.asStateFlow()

    private val _toolsUiState: MutableStateFlow<ToolsUiState> =
        MutableStateFlow(ToolsUiState.Default)
    val toolsUiState = _toolsUiState.asStateFlow()

    fun onTransform(
        centroid: Offset, pan: Offset, zoom: Float, rotation: Float,
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (rotation != 0f || zoom != 1f) {
            onTransformMultitouch(centroid, pan, zoom)
        } else {
            onDraw(centroid)
        }
    }

    fun onEditAreaTapped(offset: Offset) = viewModelScope.launch(Dispatchers.IO) {
        onDraw(offset)
    }

    private fun onTransformMultitouch(centroid: Offset, pan: Offset, zoom: Float) {
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

    private fun onDraw(tapCoords: Offset) = canvasUiState.value.run {
        val tap = CanvasCoordsHelper.toDomainCoords(canvasUiState.value, tapCoords)

        Log.i("Drawing", "Draw detected on: $tap")
    }

    fun onUndo() = viewModelScope.launch(Dispatchers.IO) {
        editor.undo()
    }

    fun onFitCanvas() {
        //todo add screen fit
    }

    fun onReject() {
        //todo close screen
    }

    fun onApply() {
        //todo apply tool
    }

    fun calculateInitialOffset(screenWidth: Int, screenHeight: Int, density: Density) =
        viewModelScope.launch(Dispatchers.IO) {
            val screenWidthPx = with(density) { screenWidth.dp.toPx() }
            val screenHeightPx = with(density) { screenHeight.dp.toPx() }

            _canvasUiState.value = canvasUiState.value.copy(
                initialOffset = getCanvasInitialOffset(
                    screenWidthPx,
                    screenHeightPx,
                    canvasUiState.value.getMinSizePx()),
                canvas = PixelsSource.Test16x8)
        }

    private fun getCanvasInitialOffset(
        screenWidth: Float, screenHeight: Float, canvasSize: Offset,
    ): Offset = (Offset(screenWidth, screenHeight) - canvasSize) / 2f

}