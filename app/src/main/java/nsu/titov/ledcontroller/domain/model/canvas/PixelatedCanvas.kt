package nsu.titov.ledcontroller.domain.model.canvas

import androidx.compose.ui.graphics.Color

class PixelatedCanvas(
    val width: Int,
    val height: Int,
    val pixels: Array<Color> = Array(width * height) { Color.Unspecified },
) {

    fun resize(newWidth: Int, newHeight: Int): PixelatedCanvas {
        val newCanvas = PixelatedCanvas(newWidth, newHeight)

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
        pixels[i] = Color.Unspecified
    }

    fun fit(x: Int, y: Int): Boolean = 0 <= x && x < width && 0 <= y && y < height

    operator fun get(x: Int, y: Int) = pixels[width * y + x]

    operator fun set(x: Int, y: Int, item: Color) = pixels.set(width * y + x, item)

    operator fun iterator(): Iterator<Color> = pixels.iterator()

    override fun equals(other: Any?): Boolean {
        return super.equals(other) && (other as PixelatedCanvas).pixels.contentEquals(this.pixels)
    }

    fun copy(
        width: Int = this.width,
        height: Int = this.height,
        pixels: Array<Color> = this.pixels.copyOf(),
    ) = PixelatedCanvas(width, height, pixels)

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + pixels.contentHashCode()
        return result
    }

    companion object {

        val Default = PixelatedCanvas(6, 2, Array(8 * 8) { Color.Unspecified }).apply {
            this[0, 0] = Color(255, 0, 0)
            this[0, 1] = Color(255, 0, 0)
        }

        fun withPattern(source: PixelatedCanvas) = PixelatedCanvas(width = source.width,
            height = source.height,
            pixels = Array(source.height * source.width) { Color.Unspecified })
    }
}