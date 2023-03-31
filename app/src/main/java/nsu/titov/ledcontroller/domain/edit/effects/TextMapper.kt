package nsu.titov.ledcontroller.domain.edit.effects

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

object TextMapper {

    private val symbolsMap: Map<Char, PixelatedCanvas> by lazy { generateMap() }

    private fun generateMap(): Map<Char, PixelatedCanvas> {

        return HashMap()
    }

    init {
    }

    suspend fun map(symbol: Char): PixelatedCanvas = symbolsMap[symbol] ?: PixelatedCanvas.Zero
}