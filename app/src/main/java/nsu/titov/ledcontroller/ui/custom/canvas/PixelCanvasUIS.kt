package nsu.titov.ledcontroller.ui.custom.canvas

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.root.utils.UIModel

data class PixelCanvasUIS(
    val canvas: PixelsSource,
    val initialOffset: Offset,
    val rectSize: Size,
    val rectSpacing: Offset,
    val cornerRadius: CornerRadius,
) {

    fun getMinSizeDp(density: Density): DpOffset = with(density) {
        val minSizePx = Offset(
            x = (rectSpacing.x + rectSize.width) * canvas.width - rectSpacing.x,
            y = (rectSpacing.y + rectSize.height) * canvas.height - rectSpacing.y
        ) - rectSpacing

        DpOffset(
            x = minSizePx.x.toDp(),
            y = minSizePx.y.toDp(),
        )
    }

    fun getMinSizePx(): Offset = Offset(
        x = (rectSpacing.x + rectSize.width) * canvas.width - rectSpacing.x,
        y = (rectSpacing.y + rectSize.height) * canvas.height - rectSpacing.y
    ) - rectSpacing

    companion object {

        val Default = PixelCanvasUIS(
            canvas = PixelsSource.Test16x8,
            initialOffset = Offset.Zero,
            rectSize = Size(Spacing.Quad.value, Spacing.Quad.value),
            rectSpacing = Offset(Spacing.Double.value, Spacing.Double.value),
            cornerRadius = CornerRadius(Spacing.Single.value),
        )
    }
}

@Immutable
data class PixelsSource(
    val width: Int,
    val height: Int,
    val pixels: List<Color>,
) : UIModel {

    operator fun get(x: Int, y: Int): Color = pixels[width * y + x]

    companion object {

        val Empty: PixelsSource = PixelsSource(
            width = 0, height = 0, pixels = emptyList()
        )

        val Test16x8: PixelsSource = PixelsSource(
            width = 16, height = 8, pixels = List(16 * 8) { Color.Transparent }
        )

        val Test16x16: PixelsSource = PixelsSource(
            width = 16, height = 16, pixels = List(16 * 16) { Color.Transparent }
        )

        val Test32x16: PixelsSource = PixelsSource(
            width = 16, height = 8, pixels = List(32 * 16) { Color.Transparent }
        )
    }
}
