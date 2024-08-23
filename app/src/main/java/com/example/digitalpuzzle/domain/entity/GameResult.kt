package com.example.digitalpuzzle.domain.entity

data class GameResult(
    val isWinnner: Boolean,
    val countRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings,
)