package nsu.titov.ledcontroller.domain.edit.merge_policy

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface MergePolicy {

    fun merge(canvas1: PixelatedCanvas, canvas2: PixelatedCanvas): PixelatedCanvas
}