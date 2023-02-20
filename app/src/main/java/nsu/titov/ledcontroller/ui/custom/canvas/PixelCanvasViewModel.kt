package nsu.titov.ledcontroller.ui.custom.canvas

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nsu.titov.ledcontroller.domain.Point

class PixelCanvasViewModel : ViewModel() {

    private val _viewModifiers = MutableStateFlow(UiState.getInitial())
    val viewModifiers = _viewModifiers.asStateFlow()

    private val _touchedPixel = MutableStateFlow(Point())
    val touchedPixel = _touchedPixel.asStateFlow()

    var currentViewMode: (Point) -> Unit = {}

    fun onGestureDetected(
        centroid: Offset,
        panShift: Offset,
        zoom: Float,
        ignored: Float,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (zoom != 0f || panShift != Offset.Zero) {
                val newOffset = panShift + (viewModifiers.value.offset - centroid) * zoom + centroid
                val newZoom = zoom * viewModifiers.value.zoom

                _viewModifiers.value = UiState(
                    offset = newOffset, zoom = newZoom
                )
            }
        }
    }

    data class UiState(
        val offset: Offset = Offset.Zero,
        val zoom: Float = 1f,
    ) {

        companion object {

            fun getInitial() = UiState()
        }
    }
}