package nsu.titov.ledcontroller.domain.edit.effects

import android.graphics.text.PositionedGlyphs
import androidx.compose.material.Text
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

class TextEffect(
    override val fireOnEvery: Int,
) : StatelessEffect {


    override fun apply(canvas: PixelatedCanvas, ticked: Int, timestamp: Long): PixelatedCanvas {


        return canvas
    }
}