package nsu.titov.ledcontroller.ui.editor

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.edit.tools.ToolType

data class ToolsUIS(
    val selectedColor: Color,
    val undoAvailable: Boolean,
    val selectedTool: ToolType? = null,
) {

    companion object {

        val Default =
            ToolsUIS(selectedColor = Color.Unspecified, undoAvailable = false)
    }
}