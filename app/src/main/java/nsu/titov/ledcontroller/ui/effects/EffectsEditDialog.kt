package nsu.titov.ledcontroller.ui.effects

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import nsu.titov.ledcontroller.ui.editor.EffectItem
import nsu.titov.ledcontroller.ui.editor.EffectVM

@Composable
fun EffectsEditDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Card {
            LazyColumn {
                item {
                    EffectItem(effect = EffectVM.Text())
                }
                item {
                    EffectItem(effect = EffectVM.Rainbow())
                }
                item {
                    EffectItem(effect = EffectVM.Sin())
                }
            }
        }
    }
}

@Preview
@Composable
fun EffectsEditDialogPreview() {
    EffectsEditDialog(onDismissRequest = {})
}