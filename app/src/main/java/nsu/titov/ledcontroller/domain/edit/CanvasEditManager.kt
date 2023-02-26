package nsu.titov.ledcontroller.domain.edit

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.util.*

class CanvasEditManager(
    initialCanvas: PixelatedCanvas
) {

    private val editHistory: Deque<PixelatedCanvas> = LinkedList()

    init {
        editHistory.push(initialCanvas)
    }

    fun applyTool(applier: ToolApplier) {
        val newCanvas = applier.applyTool(editHistory.last)
        editHistory.push(newCanvas)
    }

    fun getLast(): PixelatedCanvas = editHistory.last

    fun undo() {

    }

    companion object {

        const val INITIAL_SIZE = 1
    }
}