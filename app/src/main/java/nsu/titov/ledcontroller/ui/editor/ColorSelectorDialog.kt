package nsu.titov.ledcontroller.ui.editor

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker

@Composable
fun ColorSelectorDialog(
    onDismissRequest: () -> Unit,
    onColorChange: (Color) -> Unit,
    initialColor: Color? = null,
    properties: DialogProperties = DialogProperties(),
) {
    val controller = remember(Unit) {
        ColorPickerController()
    }

    LaunchedEffect(initialColor) {
        if (initialColor == null) return@LaunchedEffect
    }


    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Card {
            Column(
                Modifier
                    .width(WHEEL_SIZE)
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .height(WHEEL_SIZE)
                        .width(WHEEL_SIZE)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = { onColorChange(it.color) }
                )

                AlphaSlider(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    controller = controller
                )

                BrightnessSlider(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(35.dp)
                        .align(Alignment.CenterHorizontally),
                    controller = controller
                )
                Log.i("Point", controller.selectedPoint.value.toString())
            }
        }
    }
}

private val WHEEL_SIZE = 300.dp

@Preview
@Composable
fun ColorSelectorDialogPreview() {
    ColorSelectorDialog({}, {})
}