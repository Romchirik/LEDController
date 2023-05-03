package nsu.titov.ledcontroller.domain.model.layer

import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportCallback.Default
import nsu.titov.ledcontroller.domain.edit.effects.StatelessEffect
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.time.Duration

class PixelatedLayer(
    var canvas: PixelatedCanvas,
    val effects: List<StatelessEffect> = emptyList(),
) {

    companion object {

        val Default = PixelatedLayer(
            canvas = PixelatedCanvas.Default,
        )
    }
}