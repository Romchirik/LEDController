package ru.nsu.ledcontroller.ui.custom.canvas

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PixelCanvasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState.getInitial())
    val uiState = _uiState.asStateFlow()

    fun onGestureDetected(
        centroid: Offset,
        panShift: Offset,
        zoom: Float,
        ignored: Float,
    ) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            if (zoom != 0f || panShift.x != 0f || panShift.y != 0f) {
                val newOffset = panShift + (uiState.value.offset - centroid) * zoom + centroid
                val newZoom = zoom * uiState.value.zoom

                _uiState.value = UiState(
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