package nsu.titov.ledcontroller.domain.edit

import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.util.Deque
import java.util.LinkedList

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


    companion object {

        const val INITIAL_SIZE = 1
    }
}