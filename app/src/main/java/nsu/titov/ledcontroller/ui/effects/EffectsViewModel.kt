package nsu.titov.ledcontroller.ui.effects

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nsu.titov.ledcontroller.domain.edit.effects.EffectsManager
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.custom.canvas.PixelCanvasUIS
import nsu.titov.ledcontroller.ui.editor.PixelCanvasMapper

class EffectsViewModel : ViewModel() {

    private val effectsManager: EffectsManager = EffectsManager(viewModelScope)

    val canvasUiState = effectsManager.canvas.map { PixelCanvasUIS.withPattern(it) }

    init {
        effectsManager.resumePreview()
    }
}