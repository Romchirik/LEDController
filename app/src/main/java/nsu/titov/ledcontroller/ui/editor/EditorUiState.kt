package nsu.titov.ledcontroller.ui.editor

import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIState

data class EditorUiState(
    val canvas: PixelCanvasUIState,

    val canvasModifiers: CanvasModifiers,
    val tools: List<ToolUIModel>,
) {

    data class CanvasModifiers(
        val initialOffset: Offset,
        val rectSize: Size,
        val rectSpacing: Offset,
        val cornerRadius: CornerRadius,
    ) {

        companion object {

            val Default = CanvasModifiers(
                initialOffset = Offset.Zero,
                rectSize = Size(Spacing.Quad.value, Spacing.Quad.value),
                rectSpacing = Offset(Spacing.Double.value, Spacing.Double.value),
                cornerRadius = CornerRadius(Spacing.Default.value),
            )
        }
    }

    companion object {

        val Default = EditorUiState(
            canvas = PixelCanvasUIState.Test16x8,
            canvasModifiers = CanvasModifiers.Default,
            tools = emptyList(),
        )
    }
}

data class ToolUIModel(
    @field:DrawableRes val icon: Int,
    val selected: Boolean = false,
)