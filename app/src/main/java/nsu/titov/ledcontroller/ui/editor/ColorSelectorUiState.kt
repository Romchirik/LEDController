package nsu.titov.ledcontroller.ui.editor

import androidx.compose.ui.graphics.Color

sealed interface ColorSelectorUiState {

    object Hidden : ColorSelectorUiState

    class Shown(val initialColor: Color) : ColorSelectorUiState

    companion object {

        val Default = Hidden
    }
}