package ru.nsu.ledcontroller.ui.utils

import androidx.compose.ui.graphics.Color
import ru.nsu.ledcontroller.domain.model.canvas.Pixel

fun Color.toDomain() = Pixel(this.value)