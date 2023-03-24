package nsu.titov.ledcontroller.ui.utils

import kotlinx.coroutines.flow.MutableStateFlow

inline fun <T> MutableStateFlow<T>.update(valueUpdater: (T) -> T) {
    this.value = valueUpdater.invoke(this.value)
}