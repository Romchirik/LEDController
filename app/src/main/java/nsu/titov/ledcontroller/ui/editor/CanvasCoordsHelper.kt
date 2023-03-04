package nsu.titov.ledcontroller.ui.editor

import androidx.compose.ui.geometry.Offset
import nsu.titov.ledcontroller.domain.Point
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIS
import kotlin.math.floor
import kotlin.math.roundToInt

object CanvasCoordsHelper {

    fun toDomainCoords(uiState: PixelCanvasUIS, screenTap: Offset): Point? =
        uiState.run {
            val tap = screenTap - initialOffset
            if (outOfBounds(uiState, tap)) return null

            val x = calculateX(this, tap.x)
            val y = calculateY(this, tap.y)

            if (x != null && y != null) {
                return Point(x, y)
            }
            return null
        }

    private fun outOfBounds(uiState: PixelCanvasUIS, screenTap: Offset): Boolean =
        uiState.run {
            val boundsX = (rectSize.width + rectSpacing.x) * (canvas.width - 1) + rectSize.width
            val boundsY = (rectSize.height + rectSpacing.y) * (canvas.height - 1) + rectSize.height

            return !(0f < screenTap.x && screenTap.x < boundsX && 0f < screenTap.y && screenTap.y < boundsY)
        }

    private fun calculateX(uiState: PixelCanvasUIS, tapX: Float): Int? = uiState.run {
        val sizeX = rectSize.width + rectSpacing.x
        val xPrediction = floor(tapX / sizeX)
        val areaStart = xPrediction * sizeX
        val areaEnd = areaStart + rectSize.width

        if (areaStart < tapX && tapX < areaEnd) {
            return xPrediction.roundToInt()
        }

        return null
    }

    private fun calculateY(uiState: PixelCanvasUIS, tapY: Float): Int? = uiState.run {
        val sizeY = rectSize.height + rectSpacing.y
        val yPrediction = floor(tapY / sizeY)
        val areaStart = yPrediction * sizeY
        val areaEnd = areaStart + rectSize.height

        if (areaStart < tapY && tapY < areaEnd) {
            return yPrediction.roundToInt()
        }

        return null
    }
}