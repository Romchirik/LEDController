package nsu.titov.ledcontroller.domain.edit.tools

import android.util.Log
import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import kotlin.math.abs

class LineTool(
    source: PixelatedCanvas,
    selectedColor: Color,
) : AbstractTool(source, selectedColor) {

    override val type: ToolType = ToolType.DrawLine
    private var startPoint: Point? = null
    private var endPoint: Point? = null

    override fun isOverlayReady(): Boolean = (startPoint != null && endPoint != null)

    override fun onColorChange(newColor: Color) {
        selectedColor = newColor
    }

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

    private fun configureOverlay() = startPoint?.let { start ->
        val end = endPoint ?: start
        resetOverlay()

        val dx = end.x - start.x
        val dy = end.y - start.y

        Log.i("Diff", "dx: $dx, dy: $dy")
        if (abs(dx) >= abs(dy)) {
            if (dx == 0) {
                overlay[start.x, start.y] = selectedColor
                return@let
            }

            val rangeX = if (start.x <= end.x) {
                start.x..end.x
            } else {
                (end.x..start.x)
            }

            for (x in rangeX) {
                val y = start.y + dy * (x - start.x) / dx
                overlay[x, y] = selectedColor
            }
        } else {
            if (dy == 0) {
                overlay[start.x, start.y] = selectedColor
                return@let
            }

            val rangeY = if (start.y <= end.y) {
                start.y..end.y
            } else {
                (end.y..start.y)
            }

            for (y in rangeY) {
                val x = start.x + dx * (y - start.y) / dy
                overlay[x, y] = selectedColor
            }
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