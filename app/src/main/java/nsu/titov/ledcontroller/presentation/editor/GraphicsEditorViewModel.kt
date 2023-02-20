package nsu.titov.ledcontroller.presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GraphicsEditorViewModel : ViewModel() {

    private val _displayingCanvas = MutableStateFlow(1)
    val displayingCanvas: StateFlow<Int> = _displayingCanvas


    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _displayingCanvas.value = _displayingCanvas.value + 1
            }
        }
    }
}