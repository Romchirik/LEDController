package nsu.titov.ledcontroller.domain.model.tools

import androidx.compose.ui.graphics.Color

interface ColorChangeListener {

    fun onColorChange(newColor: Color) {}
}