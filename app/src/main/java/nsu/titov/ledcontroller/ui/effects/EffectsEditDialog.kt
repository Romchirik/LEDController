package nsu.titov.ledcontroller.ui.effects

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

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