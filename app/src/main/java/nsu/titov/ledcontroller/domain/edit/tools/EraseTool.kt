package nsu.titov.ledcontroller.domain.edit.tools

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class EraseTool(
    source: PixelatedCanvas,
    selectedColor: Color,
) : AbstractTool(source, selectedColor) {

    override val type: ToolType = ToolType.Erase
    private var ready = false

    override fun consumePoint(x: Int, y: Int) {
        if (overlay.fit(x, y) && overlay[x, y] != Color.Unspecified) {
            overlay[x, y] = Color.Unspecified
            ready = true
        }
    }

    override fun isOverlayReady(): Boolean = ready
}