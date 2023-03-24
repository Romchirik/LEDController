package nsu.titov.ledcontroller.domain.edit.tools

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

abstract class AbstractTool(
    val source: PixelatedCanvas,
    protected var selectedColor: Color,
) : Tool {

    protected val initial = source.copy()
    override var overlay: PixelatedCanvas = source.copy()

    protected fun resetOverlay() {
        overlay = initial.copy()
    }

    override fun onColorChange(newColor: Color) {
        selectedColor = newColor
    }
}