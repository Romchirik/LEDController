package nsu.titov.ledcontroller.ui.editor

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.model.tools.ToolType

data class ToolsUIS(
    val selectedColor: Color,
    val undoAvailable: Boolean,
    val selectedTool: ToolType? = null,
) {

    companion object {

        val Default =
            ToolsUIS(selectedColor = Color.Transparent, undoAvailable = false)
    }
}

sealed class ToolUIS(
    @field:DrawableRes val icon: Int,
    val selected: Boolean = false,
    val onClick: () -> Unit,
) {

    class ColorPicked(
        onSelect: () -> Unit,
        private val selectedColor: Color,
    ) : ToolUIS(0, false, onSelect)

    class Draw(
        icon: Int,
        selected: Boolean = false,
        onSelect: () -> Unit,
    ) : ToolUIS(icon, selected, onSelect)

    class Rect(
        icon: Int,
        selected: Boolean = false,
        onSelect: () -> Unit,
    ) : ToolUIS(icon, selected, onSelect)

    class Circle(
        icon: Int,
        selected: Boolean = false,
        onSelect: () -> Unit,
    ) : ToolUIS(icon, selected, onSelect)

    class Line(
        icon: Int,
        selected: Boolean = false,
        onSelect: () -> Unit,
    ) : ToolUIS(icon, selected, onSelect)
}