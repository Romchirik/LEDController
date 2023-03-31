package nsu.titov.ledcontroller.ui.effects

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import nsu.titov.ledcontroller.ui.Spacing
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun EffectsListDialog(
    onDismissRequest: () -> Unit,
    effects: List<EffectVM>,
    onEffectsReordered: (List<EffectVM>) -> Unit,
    properties: DialogProperties = DialogProperties(),
) {

    val data = remember { mutableStateOf(effects) }
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        onEffectsReordered(data.value)
    })


    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {

        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .fillMaxWidth()
                .reorderable(state)
                .detectReorderAfterLongPress(state)
        ) {
            items(data.value, { it }) { item ->
                ReorderableItem(state, key = item) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)

                    Column(
                        modifier = Modifier
                            .shadow(elevation.value)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .padding(Spacing.Double)
                                .clickable(onClick = item.onClick),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EffectsListDialogPreview() {
    EffectsListDialog(onDismissRequest = { }, effects = listOf(EffectVM("Сдвиг") {}), {})
}