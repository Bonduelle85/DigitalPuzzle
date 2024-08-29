package com.example.digitalpuzzle.domain.entity

import java.io.Serializable

data class GameResult(
    val isWinnner: Boolean,
    val countRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings,
) : Serializable