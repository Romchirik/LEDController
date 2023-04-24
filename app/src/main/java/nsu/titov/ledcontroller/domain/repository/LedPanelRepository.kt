package nsu.titov.ledcontroller.domain.repository

import nsu.titov.ledcontroller.domain.model.scene.PixelatedScene

interface LedPanelRepository {

    suspend fun saveAnimation(source: PixelatedScene): Result<Unit>

    suspend fun getSavedAnimation(): Result<PixelatedScene>

    sealed interface Result<T> {

        class Success<T>(
            val result: T
        ): Result<T>


        class Error(
            val reason: String,
        ): Result<Unit>
    }
}