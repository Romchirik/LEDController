package nsu.titov.ledcontroller.ui.effects

@JvmInline
value class PreviewToolsUIS(
    val paused: Boolean,
) {

    companion object {

        val Default = PreviewToolsUIS(true)
    }
}