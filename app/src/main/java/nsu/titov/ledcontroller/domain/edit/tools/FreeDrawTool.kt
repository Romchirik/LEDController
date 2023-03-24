package nsu.titov.ledcontroller.domain.edit.tools

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class FreeDrawTool(
    source: PixelatedCanvas,
    selectedColor: Color,
) : AbstractTool(source, selectedColor) {

    override val type: ToolType = ToolType.FreeDraw
    private var ready = false

    override fun consumePoint(x: Int, y: Int) {
        if (overlay.fit(x, y)) {
            if (overlay[x, y] != selectedColor) {
                overlay[x, y] = selectedColor
                ready = true
            }
        }
    }

    override fun isOverlayReady(): Boolean = ready
}