package nsu.titov.ledcontroller.ui.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nsu.titov.ledcontroller.R
import nsu.titov.ledcontroller.ui.Spacing

@Composable
fun EffectItem(effect: EffectVM) {
    Row(
        Modifier
            .width(300.dp)
            .padding(Spacing.Double)
    ) {
        Column(Modifier.fillMaxWidth(0.9f)) {
            Text(text = effect.name)
            Text(text = effect.additionalInfo, fontSize = 10.sp, color = Color(0xFF222222))
        }
        Icon(
            painter = painterResource(id = R.drawable.baseline_drag_handle_24),
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun EffectVmPreview() {
    EffectItem(EffectVM.Text())
}

sealed interface EffectVM {

    val additionalInfo: String
    val name: String

    class Rainbow(
        private val period: Long = 300L,
    ) : EffectVM {

        override val name: String
            get() = "Rainbow effect"

        override val additionalInfo: String
            get() = "Period: ${period}ms"
    }

    class Text(
        private val text: String = "Hello world!",
        private val shiftX: Float = 0f,
        private val shiftY: Float = 0f,
        private val initialColor: Color = Color.Red,
    ) : EffectVM {

        override val name: String
            get() = "Text effect"

        override val additionalInfo: String
            get() = "Text: $text\n${getColorString()} ${getShiftString()}"

        private fun getShiftString(): String = "x: ${shiftX}px/s, y: ${shiftY}px/s"
        private fun getColorString(): String = "Initial color: ${initialColor.asHex()}"
    }

    class Sin(
        private val period: Long = 600L,
    ) : EffectVM {

        override val name: String
            get() = "Sinus blink"

        override val additionalInfo: String
            get() = "Period: ${period}ms"
    }
}

fun Color.asHex(): String = "#${Integer.toHexString(this.toArgb())}"