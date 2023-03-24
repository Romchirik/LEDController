package nsu.titov.ledcontroller.domain.edit.effects

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.coroutineContext

class PeriodTicker(
    period: Long = Long.MAX_VALUE,
) : Ticker {

    private val started: AtomicBoolean = AtomicBoolean(false)
    private val timestamp: AtomicLong = AtomicLong(0L)
    private val _period: AtomicLong = AtomicLong(period)
    override var period: Long
        get() = _period.get()
        set(value) = _period.set(value)
    override val value: Flow<Long>
        get() = flow {
            while (coroutineContext.isActive) {
                if (started.get()) {
                    emit(timestamp.getAndAdd(period))
                }
                delay(period)
            }
        }

    override fun pause() = started.set(false)

    override fun resume() = started.set(true)

    override fun reset() {
        timestamp.set(0L)
    }
}