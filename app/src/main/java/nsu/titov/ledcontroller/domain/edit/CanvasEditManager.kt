package nsu.titov.ledcontroller.domain.edit

import androidx.compose.ui.graphics.Color
import nsu.titov.ledcontroller.domain.edit.history.EditHistory
import nsu.titov.ledcontroller.domain.edit.history.LimitedEditHistory
import nsu.titov.ledcontroller.domain.edit.tools.ColorChangeListener
import nsu.titov.ledcontroller.domain.edit.tools.DrawEventConsumer
import nsu.titov.ledcontroller.domain.edit.tools.EllipseTool
import nsu.titov.ledcontroller.domain.edit.tools.EraseTool
import nsu.titov.ledcontroller.domain.edit.tools.FreeDrawTool
import nsu.titov.ledcontroller.domain.edit.tools.LineTool
import nsu.titov.ledcontroller.domain.edit.tools.RectTool
import nsu.titov.ledcontroller.domain.edit.tools.Tool
import nsu.titov.ledcontroller.domain.edit.tools.ToolType
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.util.concurrent.atomic.AtomicBoolean

class CanvasEditManager(
    initialCanvas: PixelatedCanvas,
) : ColorChangeListener, DrawEventConsumer {

    private val editHistory: EditHistory = LimitedEditHistory(initialCanvas, HISTORY_SIZE)
    private var currentTool: Tool? = null
    private var currentColor: Color = Color.Unspecified
    private var pressed = AtomicBoolean()

    fun getLast() = if (pressed.get()) {
        currentTool?.overlay ?: editHistory.currentCanvas
    } else {
        editHistory.currentCanvas
    }

    fun isUndoAvailable() = editHistory.size > 1

    fun undo() {
        editHistory.undo()
        resetTool()
    }

    fun selectTool(tool: ToolType) {
        currentTool = when (tool) {
            ToolType.FreeDraw -> FreeDrawTool(
                selectedColor = currentColor,
                source = editHistory.currentCanvas,
            )
            ToolType.Ellipse -> EllipseTool(
                selectedColor = currentColor,
                source = editHistory.currentCanvas,
            )
            ToolType.DrawRect -> RectTool(
                selectedColor = currentColor,
                source = editHistory.currentCanvas,
            )
            ToolType.DrawLine -> LineTool(
                selectedColor = currentColor,
                source = editHistory.currentCanvas,
            )
            ToolType.Erase -> EraseTool(
                selectedColor = currentColor,
                source = editHistory.currentCanvas,
            )
            ToolType.ColorSelector -> {
                return
            }
        }
    }

    override fun onColorChange(newColor: Color) {
        currentColor = newColor
        currentTool?.onColorChange(newColor)
    }

    override fun consumePoint(x: Int, y: Int) {
        currentTool?.consumePoint(x, y)
    }

    override fun onCancelPress() {
        pressed.set(false)
        resetTool()
    }

    override fun onPress() = pressed.set(true)

    override fun onDepress() {
        pressed.set(false)
        currentTool?.let { tool ->
            if (tool.isOverlayReady()) {
                editHistory.currentCanvas = tool.overlay
            }
        }
        resetTool()
    }

    private fun resetTool() {
        if (currentTool != null) {
            selectTool(currentTool!!.type)
        }
    }

    fun selectedTool(): ToolType? = currentTool?.type

    companion object {

        const val HISTORY_SIZE = 20
    }
}