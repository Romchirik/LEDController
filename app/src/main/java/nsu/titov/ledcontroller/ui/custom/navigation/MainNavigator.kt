package nsu.titov.ledcontroller.ui.custom.navigation

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.layer.PixelatedLayer
import nsu.titov.ledcontroller.domain.model.scene.PixelatedScene

interface MainNavigator {

    fun toEditBaseCanvas(canvas: PixelatedCanvas)
    fun toEditEffects(layer: PixelatedLayer)
    fun toEditScene(scene: PixelatedScene)
}