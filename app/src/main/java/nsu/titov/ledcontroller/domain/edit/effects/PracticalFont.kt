package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class PracticalFont : PixelatedFont {

    private val mapper: PracticalFontTextMapper = PracticalFontTextMapper

    override fun getSymbol(char: Char): PixelatedCanvas = mapper.map(char)
}