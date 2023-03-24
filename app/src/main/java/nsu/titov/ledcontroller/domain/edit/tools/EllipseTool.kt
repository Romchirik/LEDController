package nsu.titov.ledcontroller.domain.edit.tools

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import kotlin.math.abs
import kotlin.math.roundToInt

class EllipseTool(
    source: PixelatedCanvas,
    selectedColor: Color,
) : AbstractTool(source, selectedColor) {

    override val type: ToolType = ToolType.Ellipse
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

    fun ellipse(
        rx: Float, ry: Float,
        xc: Float, yc: Float,
    ) {
        var dx: Float
        var dy: Float
        var d1: Float
        var d2: Float

        var x: Float = 0f
        var y: Float = ry

        // Initial decision parameter of region 1
        d1 = ry * ry - rx * rx * ry + 0.25f * rx * rx
        dx = 2 * ry * ry * x
        dy = 2 * rx * rx * y

        // For region 1
        while (dx < dy) {
            overlay[(x + xc).roundToInt(), (y + yc).roundToInt()] = selectedColor
            overlay[(-x + xc).roundToInt(), (y + yc).roundToInt()] = selectedColor
            overlay[(x + xc).roundToInt(), (-y + yc).roundToInt()] = selectedColor
            overlay[(-x + xc).roundToInt(), (-y + yc).roundToInt()] = selectedColor

            // Checking and updating value of
            // decision parameter based on algorithm
            if (d1 < 0) {
                x++
                dx += (2 * ry * ry)
                d1 += dx + (ry * ry)
            } else {
                x++
                y--
                dx += (2 * ry * ry)
                dy -= (2 * rx * rx)
                d1 = d1 + dx - dy + (ry * ry)
            }
        }

        // Decision parameter of region 2
        d2 =
            (((ry * ry) * ((x + 0.5f) * (x + 0.5f))) + ((rx * rx) * ((y - 1) * (y - 1))) - (rx * rx * ry * ry))

        // Plotting points of region 2
        while (y >= 0) {
            overlay[(x + xc).roundToInt(), (y + yc).roundToInt()] = selectedColor
            overlay[(-x + xc).roundToInt(), (y + yc).roundToInt()] = selectedColor
            overlay[(x + xc).roundToInt(), (-y + yc).roundToInt()] = selectedColor
            overlay[(-x + xc).roundToInt(), (-y + yc).roundToInt()] = selectedColor

            if (d2 > 0) {
                y--
                dy -= (2 * rx * rx)
                d2 = d2 + (rx * rx) - dy
            } else {
                y--
                x++
                dx += (2 * ry * ry)
                dy -= (2 * rx * rx)
                d2 = d2 + dx - dy + (rx * rx)
            }
        }
    }

    private fun configureOverlay() = startPoint?.let { start ->
        val end = endPoint ?: start
        resetOverlay()

        val center = Offset(
            ((start.x + end.x).toFloat() / 2f),
            ((start.y + end.y).toFloat() / 2f),
        )
        val radiusX = abs(start.x.toFloat() - end.x.toFloat()) / 2f
        val radiusY = abs(start.y.toFloat() - end.y.toFloat()) / 2f
        ellipse(radiusX, radiusY, center.x, center.y)
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

    override fun isOverlayReady(): Boolean = (startPoint != null && endPoint != null)
}