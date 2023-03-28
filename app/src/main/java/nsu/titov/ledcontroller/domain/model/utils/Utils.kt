package nsu.titov.ledcontroller.domain.model.utils

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

//inline val Color.hue: Float
//    get() {
//        return 1f
//    }
//
//inline val Color.hsvValue: Float
//    get() {
//        return 1f
//    }
//inline val Color.saturation: Float
//    get() {
//        return 1f
//    }

inline fun PixelatedCanvas.forEachPixel(action: (x: Int, y: Int, color: Color) -> Unit): Unit {
    repeat(this.height) { y ->
        repeat(this.width) { x ->
            action(x, y, this[x, y])
        }
    }
}

