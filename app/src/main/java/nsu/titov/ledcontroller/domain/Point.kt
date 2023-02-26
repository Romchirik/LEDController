package nsu.titov.ledcontroller.domain

data class Point(
    val x: Int = 0,
    val y: Int = 0,
) {
    companion object {
        val Default = Point(0, 0)
    }
}