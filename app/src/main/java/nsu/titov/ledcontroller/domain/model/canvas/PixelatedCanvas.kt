package nsu.titov.ledcontroller.domain.model.canvas

import androidx.compose.ui.graphics.Color

data class PixelatedCanvas(
    val width: Int,
    val height: Int,
    val initialPixel: Color,
) {

    val pixels: Array<Color> = Array(width * height) { initialPixel }

    fun resize(newWidth: Int, newHeight: Int): PixelatedCanvas {
        val newCanvas = PixelatedCanvas(newWidth, newHeight, Color.Transparent)

        repeat(height) { _y ->
            val y = _y * width
            repeat(width) { x ->
                if (newCanvas.fit(x, y)) {
                    newCanvas[x, y] = this[x, y]
                }
            }
        }
        return newCanvas
    }

    fun reset() = repeat(pixels.size) { i ->
        pixels[i] = Color.Transparent
    }


    fun fit(x: Int, y: Int): Boolean = 0 < x && x < width && 0 < y && y < height

    operator fun get(x: Int, y: Int) = pixels[width * y + x]

    operator fun set(x: Int, y: Int, item: Color) = pixels.set(width * y + x, item)

    operator fun iterator(): Iterator<Color> = pixels.iterator()

    companion object {

        private const val DEFAULT_WIDTH = 16
        private const val DEFAULT_HEIGHT = 8

        val Default = PixelatedCanvas(DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.White)
    }
}