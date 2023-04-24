package nsu.titov.ledcontroller.ui.custom.canvas

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import nsu.titov.ledcontroller.domain.model.canvas.PixelatedCanvas
import nsu.titov.ledcontroller.ui.Spacing
import nsu.titov.ledcontroller.ui.editor.PixelCanvasMapper
import nsu.titov.ledcontroller.ui.utils.UIModel

data class PixelCanvasUIS(
    val canvas: PixelsSource,
    val rectSize: Size,
    val rectSpacing: Offset,
    val cornerRadius: CornerRadius,
    val initialOffset: Offset,
) {

    fun getMinSizeDp(density: Density): DpOffset = with(density) {
        val minSizePx = Offset(
            x = (rectSpacing.x + rectSize.width) * canvas.width,
            y = (rectSpacing.y + rectSize.height) * canvas.height,
        ) - rectSpacing

        DpOffset(
            x = minSizePx.x.toDp(),
            y = minSizePx.y.toDp(),
        )
    }

    fun getMinSizePx(): Offset = Offset(
        x = (rectSpacing.x + rectSize.width) * canvas.width,
        y = (rectSpacing.y + rectSize.height) * canvas.height,
    ) - rectSpacing

    companion object {

        fun withPattern(canvas: PixelatedCanvas) = PixelCanvasUIS(
            canvas = PixelCanvasMapper.toUi(canvas),
            initialOffset = Offset.Zero,
            rectSize = Size(Spacing.Quad.value, Spacing.Quad.value),
            rectSpacing = Offset.Zero,
            cornerRadius = CornerRadius.Zero,
        )

        val Default = PixelCanvasUIS(
            canvas = PixelsSource(0, 0, emptyList()),
            initialOffset = Offset.Zero,
            rectSize = Size(Spacing.Quad.value, Spacing.Quad.value),
            rectSpacing = Offset(Spacing.Double.value, Spacing.Double.value),
            cornerRadius = CornerRadius(Spacing.Single.value),
        )
    }
}

@Immutable
class PixelsSource(
    val width: Int,
    val height: Int,
    val pixels: List<Color>,
) : UIModel {

    operator fun get(x: Int, y: Int): Color = pixels[width * y + x]
}
