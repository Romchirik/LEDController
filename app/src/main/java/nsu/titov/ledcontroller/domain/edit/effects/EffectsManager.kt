package nsu.titov.ledcontroller.domain.edit.effects

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import kotlin.system.measureTimeMillis

class EffectsManager(
    private val managerScope: CoroutineScope,
) {

    private val canvasChannel: Channel<PixelatedCanvas> = Channel()
    val canvas: Flow<PixelatedCanvas> = canvasChannel.receiveAsFlow()

    private val effects: List<Effect> =
        listOf(MoveEffect(1, 1), MoveEffect(1, 1), MoveEffect(-1, -1))
    private var baseCanvas: PixelatedCanvas = PixelatedCanvas.Default

    private var ticker: Ticker = PeriodTicker(16L)

    init {
        managerScope.launch {
            ticker.value.collect(::onNextTick)
        }
    }

    fun pausePreview() = ticker.pause()

    fun resumePreview() = ticker.resume()

    private fun onNextTick(timestamp: Long) {
        emitNextFrame(timestamp)
    }

    private fun emitNextFrame(timestamp: Long) {
        var canvas: PixelatedCanvas = baseCanvas
        measureTimeMillis {
            for (effect in effects) {
                canvas = effect.apply(canvas, timestamp)
            }
        }.let {
            Log.d("Update", "Calculations took $it ms")
        }
        managerScope.launch {
            canvasChannel.send(canvas)
        }
    }
}