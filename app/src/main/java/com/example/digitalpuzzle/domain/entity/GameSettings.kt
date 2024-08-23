package com.example.digitalpuzzle.domain.entity

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPersentOfRightAnswers: Int,
    val gameTimeInSeconds: Int,
)