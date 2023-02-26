package nsu.titov.ledcontroller.ui.editor

import androidx.annotation.DrawableRes

data class ToolsUiState(
    val undoAvailable: Boolean, val tools: List<ToolUIModel>
) {
    companion object {
        val Default = ToolsUiState(undoAvailable = false, tools = emptyList())
    }
}

data class ToolUIModel(
    @field:DrawableRes val icon: Int,
    val selected: Boolean = false,
    val onSelect: () -> Unit,
)