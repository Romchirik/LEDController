package nsu.titov.ledcontroller.domain.edit.effects

import kotlinx.coroutines.flow.Flow

interface Ticker {

    val value: Flow<Long>
    var period: Long

    fun pause()
    fun resume()
    fun reset()
}