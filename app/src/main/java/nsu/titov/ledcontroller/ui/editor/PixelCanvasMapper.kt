package nsu.titov.ledcontroller.ui.editor

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.custom.PixelsSource
import nsu.titov.ledcontroller.ui.root.utils.UIMapper

object PixelCanvasMapper : UIMapper<PixelatedCanvas, PixelsSource> {

    override fun toUi(source: PixelatedCanvas): PixelsSource = PixelsSource(
        width = source.width,
        height = source.height,
        pixels = source.pixels.copyOf().asList()
    )
}