package ru.nsu.ledcontroller.ui.custom.canvas

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.nsu.ledcontroller.domain.model.canvas.PixelCanvas
import kotlin.math.roundToInt

@Composable
fun PixelCanvasView(
    source: PixelCanvas,
    rectSpacing: Offset = Offset.Zero,
    rectSize: Size = Size.Zero,
    cornerRadius: CornerRadius = CornerRadius.Zero,
    viewMode: ViewMode = ViewMode.LOCKED,
    onPixelClicked: ((Int, Int) -> Unit)? = null,
) {
    val viewModel: PixelCanvasViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.7f)
        .pointerInput(viewMode) {
            when (viewMode) {
                ViewMode.LOCKED -> onPixelClicked?.let { clickedCallback ->
                    detectTapGestures(onTap = {
                        val tap = it - uiState.offset
                        val targetingAreaOffsetX =
                            (tap.x / (uiState.zoom * (rectSpacing.x + rectSize.width))).roundToInt()
                        val targetingAreaOffsetY =
                            (tap.y / (uiState.zoom * (rectSpacing.y + rectSize.height))).roundToInt()

                        Log.d(
                            "PixelCanvasView",
                            "Tap detected x: $targetingAreaOffsetX, y: $targetingAreaOffsetY\n" + "Original tap x: ${it.x} y: ${it.y}"
                        )
                        clickedCallback(targetingAreaOffsetX, targetingAreaOffsetY)
                    })
                }
                ViewMode.FREE_MOVE -> detectTransformGestures(
                    panZoomLock = true,
                    onGesture = viewModel::onGestureDetected,
                )
            }
        }) {

        repeat(source.width) { x ->
            repeat(source.height) { y ->
                drawRoundRect(
                    color = Color(source[x, y].value),
                    topLeft = Offset(
                        x = uiState.offset.x + x * uiState.zoom * (rectSize.width + rectSpacing.x),
                        y = uiState.offset.y + y * uiState.zoom * (rectSize.height + rectSpacing.y)
                    ),
                    size = rectSize * uiState.zoom,
                    cornerRadius = cornerRadius * uiState.zoom,
                )
            }
        }
    }
}

//@Composable
//@Preview
//fun PixelCanvasPreview() {
//    PixelCanvasView(
//        source = PixelCanvas(16, 8, Color.Black.toDomain()),
//        rectSpacing = Offset(20f, 20f),
//        rectSize = Size(40f, 40f),
//        cornerRadius = CornerRadius(8f),
//        viewMode = ViewMode.FREE_MOVE,
//    )
//}