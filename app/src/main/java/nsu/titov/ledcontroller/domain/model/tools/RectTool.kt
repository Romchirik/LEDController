package nsu.titov.ledcontroller.domain.model.tools

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class RectTool(override val overlay: PixelatedCanvas) : Tool {

    private var color: Color = Color.Transparent
    private var startPoint: Point? = null
    private var endPoint: Point? = null

    override fun onColorChange(newColor: Color) {
        color = newColor


    }

    override fun consumePoint(x: Int, y: Int) {
        if (startPoint == null) {
            applyStartPoint(x, y)
            applyEndPoint(x, y)
        } else {
            applyEndPoint(x, y)
        }

        configureOverlay()
    }

    private fun configureOverlay() = startPoint?.let { start ->
        val end = endPoint ?: start
        overlay.reset()

        //vertical lines
        for (i in start.y until end.y step (end.y - start.y).sign()) {
            overlay[start.x, i] = color
            overlay[end.x, i] = color
        }

        //horizontal lines
        for (i in start.x until end.x step (end.x - start.x).sign()) {
            overlay[i, start.y] = color
            overlay[i, end.y] = color
        }
    }

    private fun Int.sign(): Int = if (this < 0) -1 else 1

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

    override fun applyReady(): Boolean = startPoint != null && endPoint != null
}