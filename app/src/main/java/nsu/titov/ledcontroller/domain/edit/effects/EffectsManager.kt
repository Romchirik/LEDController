package nsu.titov.ledcontroller.domain.edit.effects

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import kotlin.system.measureTimeMillis

class EffectsManager(
    private val managerScope: CoroutineScope,
) {

    private val canvasChannel: Channel<PixelatedCanvas> = Channel()
    val canvas: Flow<PixelatedCanvas> = canvasChannel.receiveAsFlow()

    private val effects: List<StatelessEffect> = listOf(MoveEffect(1, 0, 30), RainbowEffect(1000))
    private var baseCanvas: PixelatedCanvas = PixelatedCanvas.Default

    private var ticked = 0
    private var ticker: Ticker = PeriodTicker(TickDelay)

    init {
        managerScope.launch {
            canvasChannel.send(baseCanvas)
        }
        managerScope.launch {
            ticker.value.collect(::onNextTick)
        }
    }

    fun pausePreview() = ticker.pause()

    fun resumePreview() = ticker.resume()

    private suspend fun onNextTick(timestamp: Long) = withContext(Dispatchers.Unconfined) {
        ticked++
        emitNextFrame(ticked, timestamp)
    }

    private suspend fun emitNextFrame(idx: Int, timestamp: Long) {
        var canvas: PixelatedCanvas = baseCanvas
        measureTimeMillis {
            for (effect in effects) {
                canvas = effect.apply(canvas, idx, timestamp)
            }
        }.let {
            Log.d("Update", "Calculations took $it ms")
        }
        if (canvas !== baseCanvas) {
            canvasChannel.send(canvas)
        }
    }

    fun stopPreview() = ticker.run {
        pause()
        reset()
        ticked = 0
        managerScope.launch {
            canvasChannel.send(baseCanvas)
        }
    }

    companion object {

        const val TickDelay = 16L
    }
}