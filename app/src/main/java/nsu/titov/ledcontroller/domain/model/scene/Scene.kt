package nsu.titov.ledcontroller.domain.model.scene

import nsu.titov.ledcontroller.domain.edit.effects.StatelessEffect
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas

data class Scene(
    val canvas: PixelatedCanvas,
    val effects: List<StatelessEffect> = emptyList(),
)