package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

interface PixelatedFont {

    fun getSymbol(char: Char): PixelatedCanvas
}