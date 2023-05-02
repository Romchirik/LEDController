package nsu.titov.ledcontroller.domain.model.layer

import nsu.titov.ledcontroller.domain.edit.effects.StatelessEffect
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.time.Duration

class PixelatedLayer(
    val canvas: PixelatedCanvas,
    val effects: List<StatelessEffect> = emptyList(),
)