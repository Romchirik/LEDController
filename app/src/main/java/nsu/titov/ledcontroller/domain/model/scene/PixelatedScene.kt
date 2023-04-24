package nsu.titov.ledcontroller.domain.model.scene

import nsu.titov.ledcontroller.domain.model.layer.PixelatedLayer

data class PixelatedScene(
    val layers: List<PixelatedLayer>,
    val startOffsetForLayer: Map<PixelatedLayer, Long>,
    val endOffsetForLayer: Map<PixelatedLayer, Long>,
    val isLayerCycled: Map<PixelatedLayer, Long>,
)