package nsu.titov.ledcontroller.domain.edit.tools

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class RectTool(
    source: PixelatedCanvas,
    selectedColor: Color,
) : AbstractTool(source, selectedColor) {

    override val type: ToolType = ToolType.DrawRect

    private var startPoint: Point? = null
    private var endPoint: Point? = null

    override fun consumePoint(x: Int, y: Int) {
        if (startPoint == null) {
            applyStartPoint(x, y)
            applyEndPoint(x, y)
        } else {
            if (endPoint?.x == x && endPoint?.y == y) return
            applyEndPoint(x, y)
        }

        configureOverlay()
    }

    override fun isOverlayReady(): Boolean = (startPoint != null && endPoint != null)

    private fun configureOverlay() = startPoint?.let { start ->
        val end = endPoint ?: start
        resetOverlay()

        val rangeY = if (start.y <= end.y) {
            start.y..end.y
        } else {
            (end.y..start.y)
        }
        for (i in rangeY) {
            overlay[start.x, i] = selectedColor
            overlay[end.x, i] = selectedColor
        }

        val rangeX = if (start.x <= end.x) {
            start.x..end.x
        } else {
            (end.x..start.x)
        }
        for (i in rangeX) {
            overlay[i, start.y] = selectedColor
            overlay[i, end.y] = selectedColor
        }
    }

    private fun applyStartPoint(x: Int, y: Int) {
        if (overlay.fit(x, y)) {
            startPoint = Point(x, y)
        }
    }

    private fun applyEndPoint(x: Int, y: Int) {
        if (overlay.fit(x, y)) {
            endPoint = Point(x, y)
        }
    }
}