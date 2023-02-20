package nsu.titov.ledcontroller.ui.custom.canvas

import nsu.titov.ledcontroller.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.root.utils.UIMapper

object PixelCanvasMapper : UIMapper<PixelatedCanvas, PixelCanvasUIState> {

    override fun toUi(source: PixelatedCanvas): PixelCanvasUIState = PixelCanvasUIState(
        width = source.width,
        height = source.height,
        pixels = source.pixels.copyOf().asList()
    )
}