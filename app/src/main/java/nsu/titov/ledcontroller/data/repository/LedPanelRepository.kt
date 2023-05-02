package nsu.titov.ledcontroller.data.repository

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4
import androidx.core.graphics.green
import androidx.core.graphics.red
import animatedLayer
import com.google.protobuf.ByteString
import moveEffect
import nsu.titov.ledcontroller.domain.edit.effects.MoveEffect
import nsu.titov.ledcontroller.domain.edit.effects.RainbowEffect
import nsu.titov.ledcontroller.domain.edit.effects.TextEffect
import nsu.titov.ledcontroller.domain.model.layer.PixelatedLayer
import nsu.titov.ledcontroller.domain.model.scene.PixelatedScene
import nsu.titov.ledcontroller.domain.repository.LedPanelRepository
import pixelatedCanvas
import rainbowEffect
import textEffect

import java.nio.ByteBuffer
import kotlin.experimental.or

class LedPanelRepositoryImpl(
    private val context: Context,
) : LedPanelRepository {

    override suspend fun saveAnimation(source: PixelatedLayer): LedPanelRepository.Result<Unit> {
        val result = animatedLayer {
            duration = Long.MAX_VALUE

            baseFrame = pixelatedCanvas {
                width = source.canvas.width
                height = source.canvas.height

                image = source.canvas.pixels.asByteString()
            }

            source.effects.forEachIndexed { idx, effect ->
                when (effect) {
                    is MoveEffect -> {
                        moveEffect = moveEffect {
                            shiftPerTickX = effect.shiftX.toFloat()
                            shiftPerTickY = effect.shiftY.toFloat()
                            ordinal = idx
                        }
                    }
                    is RainbowEffect -> {
                        rainbowEffect = rainbowEffect {
                            duration = effect.period.toInt()
                            ordinal = idx
                        }
                    }
                    is TextEffect -> {
                        textEffect = textEffect {
                            text = effect.text
                            ordinal = idx
                        }
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveFileUsingMediaStore(context, "test_animation.anm", result.toByteArray())
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Too old android version", Toast.LENGTH_SHORT).show()
        }

        return Success
    }

    override suspend fun getSavedAnimation(): LedPanelRepository.Result<PixelatedScene> {
        TODO("Not yet implemented")
    }

    companion object {

        private val Success = LedPanelRepository.Result.Success(Unit)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileUsingMediaStore(context: Context, fileName: String, bytes: ByteArray) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        resolver.openOutputStream(uri!!).use { output ->
            output?.write(bytes)
            output?.flush()
        }
    }

    private fun Array<Color>.asByteString(): ByteString {
        val expectedLength = this.size * Byte.SIZE_BYTES
        val buffer = ByteBuffer.allocate(expectedLength)
        buffer.put(this.map(Color::toArgb).map(::to8bitColor).toByteArray())

        return ByteString.copyFrom(buffer.array())
    }

    private fun to8bitColor(@ColorInt color: Int): Byte {
        val (alpha, red, green, blue) = color

        return ((alpha / 64) shl 6 or
                (red / 64) shl 4 or
                (green / 64) shl 2 or
                (blue / 64)).toByte()
    }
}