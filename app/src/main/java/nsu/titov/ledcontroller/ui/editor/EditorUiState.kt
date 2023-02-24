package nsu.titov.ledcontroller.ui.editor

import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.ui.Spacing

data class EditorUiState(
    val canvasModifiers: CanvasUiState,
    val tools: List<ToolUIModel>,
) {

    data class CanvasUiState(
        val canvas: PixelCanvasUIState,
        val initialOffset: Offset,
        val rectSize: Size,
        val rectSpacing: Offset,
        val cornerRadius: CornerRadius,
    ) {

        companion object {

            val Default = CanvasUiState(
                canvas = PixelCanvasUIState.Test16x8,
                initialOffset = Offset.Zero,
                rectSize = Size(Spacing.Quad.value, Spacing.Quad.value),
                rectSpacing = Offset(Spacing.Double.value, Spacing.Double.value),
                cornerRadius = CornerRadius(Spacing.Default.value),
            )
        }
    }

    companion object {

        val Default = EditorUiState(
            canvasModifiers = CanvasUiState.Default,
            tools = emptyList(),
        )
    }
}

sealed class ToolUIModel(
    @field:DrawableRes val icon: Int,
    val selected: Boolean = false,
    val onSelect: (ToolUIModel) -> Unit,
) {

    class ColorSelector(
        icon: Int,
        selected: Boolean = false,
        onSelect: (ToolUIModel) -> Unit,
        val color: Color
    ) : ToolUIModel(icon, selected, onSelect)
}