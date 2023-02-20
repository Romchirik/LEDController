package nsu.titov.ledcontroller.model.canvas

import androidx.compose.ui.graphics.Color

data class PixelatedCanvas(
    val width: Int,
    val height: Int,
    val initialPixel: Color,
) {

    val pixels: Array<Color> = Array(width * height) { initialPixel }

    operator fun get(x: Int, y: Int) = pixels[width * y + x]

    operator fun set(x: Int, y: Int, item: Color) = pixels.set(width * y + x, item)

    operator fun iterator(): Iterator<Color> = pixels.iterator()

    companion object {

        private const val DEFAULT_WIDTH = 16
        private const val DEFAULT_HEIGHT = 8

        fun getDefault() = PixelatedCanvas(DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.White)
    }
}