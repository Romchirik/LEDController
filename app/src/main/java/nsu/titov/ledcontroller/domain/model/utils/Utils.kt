package nsu.titov.ledcontroller.domain.model.utils

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import kotlin.math.abs

inline fun PixelatedCanvas.forEachPixel(action: (x: Int, y: Int, color: Color) -> Unit) {
    repeat(this.height) { y ->
        repeat(this.width) { x ->
            action(x, y, this[x, y])
        }
    }
}

fun PixelatedCanvas.subCanvas(
    startX: Int,
    startY: Int,
    endX: Int = this.width,
    endY: Int = this.height,
): PixelatedCanvas {
    val result = PixelatedCanvas.withSize(abs(endX - startX), abs(endY - startY))

    result.forEachPixel { x, y, _ ->
        result[x, y] = this[this.width.fitCycled(x + startX), this.height.fitCycled(y + startY)]
    }

    return result
}

@Suppress("NOTHING_TO_INLINE")
inline fun Int.fitCycled(value: Int): Int {
    val x = value % this

    return if (x < 0) {
        x + this
    } else {
        x
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Int.fitCycled(value: Long): Int {
    val x = value % this

    return if (x < 0) {
        (x + this).toInt()
    } else {
        x.toInt()
    }
}