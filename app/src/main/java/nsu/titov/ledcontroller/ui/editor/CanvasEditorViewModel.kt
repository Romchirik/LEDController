package nsu.titov.ledcontroller.ui.editor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.ledcontroller.domain.edit.CanvasEditManager
import nsu.titov.ledcontroller.domain.edit.tools.ColorChangeListener
import nsu.titov.ledcontroller.domain.edit.tools.ColorSelector
import nsu.titov.ledcontroller.domain.edit.tools.ToolType
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIS
import nsu.titov.ledcontroller.ui.utils.update

class CanvasEditorViewModel(
    private val colorTool: ColorSelector = ColorSelector(),
    private val editor: CanvasEditManager = CanvasEditManager(PixelatedCanvas.Default),
) : ViewModel(), ColorChangeListener {

    private val _canvasUiState: MutableStateFlow<PixelCanvasUIS> =
        MutableStateFlow(PixelCanvasUIS.withPattern(editor.getLast()))
    val canvasUiState = _canvasUiState.asStateFlow()

    private val _colorSelectionOpened: MutableStateFlow<ColorSelectorUiState> =
        MutableStateFlow(ColorSelectorUiState.Default)
    val colorSelectionOpened = _colorSelectionOpened.asStateFlow()

    private val _toolsUiState: MutableStateFlow<ToolsUIS> =
        MutableStateFlow(ToolsUIS.Default)
    val toolsUiState = _toolsUiState.asStateFlow()

    init {
        editor.onColorChange(colorTool.currentColor)
    }

    fun onTransform(
        centroid: Offset, pan: Offset, zoom: Float, rotation: Float,
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (rotation != 0f || zoom != 1f) {
            onTransformMultitouch(centroid, pan, zoom)
        }
    }

    fun onEditAreaTap(offset: Offset) = viewModelScope.launch {
        onDraw(offset)
    }

    fun onDismissColorSelectorDialog() =
        _colorSelectionOpened.update { ColorSelectorUiState.Hidden }

    fun onColorSelectorClicked() =
        _colorSelectionOpened.update { ColorSelectorUiState.Shown(colorTool.currentColor) }

    fun onEditAreaFastSwipe(historicalPoints: List<Offset>) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (point in historicalPoints) {
                    CanvasCoordsHelper.toDomainCoords(canvasUiState.value, point)?.let { tap ->
                        editor.consumePoint(tap.x, tap.y)
                    }
                }
            }
            pushNewCanvasIfNecessary(editor.getLast())
        }

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

    private suspend fun onDraw(tapCoords: Offset) = withContext(Dispatchers.IO) {
        CanvasCoordsHelper.toDomainCoords(canvasUiState.value, tapCoords)?.let { tap ->
            editor.consumePoint(tap.x, tap.y)
            pushNewCanvasIfNecessary(editor.getLast())
        }
    }

    fun onPress() {
        editor.onPress()
        pushNewCanvasIfNecessary(editor.getLast())
    }

    fun onCancelPress() {
        editor.onCancelPress()
        pushNewCanvasIfNecessary(editor.getLast())
    }

    fun onDepress() {
        editor.onDepress()
        pushNewCanvasIfNecessary(editor.getLast())
        pushToolsState()
    }

    fun onUndo() {
        editor.undo()
        pushNewCanvasIfNecessary(editor.getLast())
        pushToolsState()
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
                    canvasUiState.value.getMinSizePx()
                )
            )
        }

    override fun onColorChange(newColor: Color) {
        colorTool.currentColor = newColor
        editor.onColorChange(newColor)
        _toolsUiState.value = toolsUiState.value.copy(
            selectedColor = colorTool.currentColor
        )
    }

    private fun pushToolsState() {
        _toolsUiState.value = ToolsUIS(
            selectedColor = colorTool.currentColor,
            undoAvailable = editor.isUndoAvailable(),
            selectedTool = editor.selectedTool(),
        )
    }

    private fun pushNewCanvasIfNecessary(canvas: PixelatedCanvas) {
        //todo optimize this
        _canvasUiState.value = canvasUiState.value.copy(
            canvas = PixelCanvasMapper.toUi(canvas)
        )
    }

    private fun getCanvasInitialOffset(
        screenWidth: Float, screenHeight: Float, canvasSize: Offset,
    ): Offset = (Offset(screenWidth, screenHeight) - canvasSize) / 2f

    fun onToolClicked(toolType: ToolType) {
        when (toolType) {
            ToolType.ColorSelector -> {
                //todo add color selection
            }
            else -> {
                editor.selectTool(toolType)
                _toolsUiState.value = toolsUiState.value.copy(
                    selectedTool = toolType
                )
            }
        }
    }
}