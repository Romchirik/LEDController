package nsu.titov.ledcontroller.domain.edit.tools

interface DrawEventConsumer {

    fun consumePoint(x: Int, y: Int)
    fun onPress() {}
    fun onCancelPress() {}
    fun onDepress() {}
}