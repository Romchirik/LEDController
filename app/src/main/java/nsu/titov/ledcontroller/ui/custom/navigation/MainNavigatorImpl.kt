package nsu.titov.ledcontroller.ui.custom.navigation

import androidx.navigation.NavController
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.domain.model.layer.PixelatedLayer
import nsu.titov.ledcontroller.domain.model.scene.PixelatedScene

class MainNavigatorImpl(
    private val controller: NavController
): MainNavigator {

    override fun toEditBaseCanvas(canvas: PixelatedCanvas) {
        controller.navigate(Routes.EditCanvas, )
    }

    override fun toEditEffects(layer: PixelatedLayer) {
        TODO("Not yet implemented")
    }

    override fun toEditScene(scene: PixelatedScene) {
        TODO("Not yet implemented")
    }
}