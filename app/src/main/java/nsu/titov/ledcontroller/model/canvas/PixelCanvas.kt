package ru.nsu.ledcontroller.domain.model.canvas

data class PixelCanvas(
    val width: Int,
    val height: Int,
    val initialPixel: Pixel = Pixel(),
) {
    val source: Array<Pixel> = Array(width * height) { initialPixel }

    operator fun get(x: Int, y: Int) = source[width * y + x]

    operator fun set(x: Int, y: Int, item: Pixel) = source.set(width * y + x, item)

    operator fun iterator(): Iterator<Pixel> = source.iterator()


    companion object {
        private const val DEFAULT_WIDTH = 16
        private const val DEFAULT_HEIGHT = 8

        fun getDefault() = PixelCanvas(DEFAULT_WIDTH, DEFAULT_HEIGHT)
    }
}