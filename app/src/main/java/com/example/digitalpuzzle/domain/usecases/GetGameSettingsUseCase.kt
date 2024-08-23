package com.example.digitalpuzzle.domain.usecases

import com.example.digitalpuzzle.domain.entity.GameSettings
import com.example.digitalpuzzle.domain.entity.Level
import com.example.digitalpuzzle.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level) : GameSettings {
        return repository.getGameSettings(level)
    }
}