package nsu.titov.ledcontroller.ui.editor

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.ui.root.utils.UIModel

@Immutable
data class PixelCanvasUIState(
    val width: Int,
    val height: Int,
    val pixels: List<Color>,
) : UIModel {

    operator fun get(x: Int, y: Int): Color = pixels[width * y + x]

    companion object {

        val Empty: PixelCanvasUIState = PixelCanvasUIState(
            width = 0, height = 0, pixels = emptyList()
        )

        val Test16x8: PixelCanvasUIState = PixelCanvasUIState(
            width = 16, height = 8, pixels = List(16 * 8) { Color.Transparent }
        )

        val Test16x16: PixelCanvasUIState = PixelCanvasUIState(
            width = 16, height = 16, pixels = List(16 * 16) { Color.Transparent }
        )

        val Test32x16: PixelCanvasUIState = PixelCanvasUIState(
            width = 16, height = 8, pixels = List(32 * 16) { Color.Transparent }
        )
    }
}