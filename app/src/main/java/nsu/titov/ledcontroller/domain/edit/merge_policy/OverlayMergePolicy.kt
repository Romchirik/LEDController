package nsu.titov.ledcontroller.domain.edit.merge_policy

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class OverlayMergePolicy : MergePolicy {

    override fun merge(canvas1: PixelatedCanvas, canvas2: PixelatedCanvas): PixelatedCanvas {
        assert(canvas1.width == canvas2.width)
        assert(canvas1.height == canvas2.height)

        val result = canvas1.copy()
        repeat(result.height) { y ->
            repeat(result.width) { x ->
                result[x, y] = if (canvas2[x, y] == Color.Unspecified) {
                    canvas1[x, y]
                } else {
                    canvas2[x, y]
                }
            }

        }
        return result
    }
}