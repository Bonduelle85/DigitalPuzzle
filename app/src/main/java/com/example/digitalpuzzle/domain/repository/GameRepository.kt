package com.example.digitalpuzzle.domain.repository

import com.example.digitalpuzzle.domain.entity.GameSettings
import com.example.digitalpuzzle.domain.entity.Level
import com.example.digitalpuzzle.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfAnswers: Int) : Question

    fun getGameSettings(level: Level) : GameSettings
}