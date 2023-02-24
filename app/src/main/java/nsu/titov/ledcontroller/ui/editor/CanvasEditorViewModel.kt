package nsu.titov.ledcontroller.ui.editor

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.domain.edit.CanvasEditManager
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class CanvasEditorViewModel(
    private val editor: CanvasEditManager = CanvasEditManager(PixelatedCanvas.Default)

) : ViewModel() {

    private val _uiState: MutableStateFlow<EditorUiState> = MutableStateFlow(EditorUiState.Default)
    val uiState = _uiState.asStateFlow()

    private val selectedColor = Color.Yellow

    init {
        _uiState.value = uiState.value.copy(
            canvasModifiers = uiState.value.canvasModifiers.copy(
                canvas = PixelCanvasMapper.toUi(editor.getLast()),
            ),
            tools = generateTools()
        )
    }

    private fun generateTools(): List<ToolUIModel> {
        return listOf(
            ToolUIModel.ColorSelector(
                icon = R.drawable.ic_baseline_color_lens_24,
                selected = true,
                onSelect = this::onToolClicked,
                color = Color.Yellow
            )
        )
    }

    fun onTransform(
        centroid: Offset, pan: Offset, zoom: Float, rotation: Float
    ) {
        if (rotation != 0f || zoom != 1f) {
            onTransformMultitouch(centroid, pan, zoom)
        } else {
            onDraw(centroid)
        }
    }

    fun onEditAreaTapped(offset: Offset) = onDraw(offset)

    private fun onTransformMultitouch(centroid: Offset, pan: Offset, zoom: Float) {
        if (zoom != 0f || pan != Offset.Zero) {
            val newInitialOffset =
                pan + (uiState.value.canvasModifiers.initialOffset - centroid) * zoom + centroid
            val newRectSize = uiState.value.canvasModifiers.rectSize * zoom
            val newRectOffset = uiState.value.canvasModifiers.rectSpacing * zoom
            val newCornerRadius = uiState.value.canvasModifiers.cornerRadius * zoom

            _uiState.value = _uiState.value.copy(
                canvasModifiers = EditorUiState.CanvasUiState(
                    canvas = _uiState.value.canvasModifiers.canvas,
                    initialOffset = newInitialOffset,
                    rectSize = newRectSize,
                    rectSpacing = newRectOffset,
                    cornerRadius = newCornerRadius,
                )
            )
        }
    }

    private fun onToolClicked(tool: ToolUIModel) {
    }

    private fun onDraw(tapCoords: Offset) = uiState.value.canvasModifiers.run {
        val tap = CanvasCoordsHelper.toDomainCoords(uiState.value.canvasModifiers, tapCoords)

        Log.i("Drawing", "Draw detected on: $tap")
    }
}