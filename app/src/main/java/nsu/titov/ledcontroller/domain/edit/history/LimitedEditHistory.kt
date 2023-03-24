package nsu.titov.ledcontroller.domain.edit.history

import kotlinx.coroutines.sync.Mutex
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import java.util.Deque
import java.util.LinkedList

class LimitedEditHistory(
    initial: PixelatedCanvas,
    override val capacity: Int,
) : EditHistory {

    private val canvasLock = Mutex()
    private val queue: Deque<PixelatedCanvas> = LinkedList<PixelatedCanvas>().apply { add(initial) }
    override val size: Int
        get() = queue.size

    override var currentCanvas: PixelatedCanvas
        get() = queue.peek()!!
        set(value) {
            if (queue.size + 1 >= capacity) {
                queue.removeLast()
            }
            queue.push(value)
        }

    override fun undo() = if (queue.size > 1) {
        queue.pop()
    } else {
        null
    }
}