package com.example.digitalpuzzle.data

import com.example.digitalpuzzle.domain.entity.GameSettings
import com.example.digitalpuzzle.domain.entity.Level
import com.example.digitalpuzzle.domain.entity.Question
import com.example.digitalpuzzle.domain.repository.GameRepository
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private val MIN_SUM_VALUE = 1
    private val MAX_ANSWER_VALUE = 9
    private val MIN_ANSWER_VALUE = 0

    override fun generateQuestion(maxSumValue: Int, countOfAnswers: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAnswer = sum - visibleNumber
        val answers = mutableSetOf<Int>()
        answers.add(rightAnswer)
        while (answers.size < countOfAnswers) {
            answers.add(Random.nextInt(MIN_ANSWER_VALUE, MAX_ANSWER_VALUE + 1))
        }
        return Question(
            sumNumber = sum,
            visibleNumber = visibleNumber,
            answers = answers.shuffled()
        )
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> GameSettings(10, 3, 50, 8)
            Level.EASY -> GameSettings(10, 10, 70, 60)
            Level.NORMAL -> GameSettings(20, 20, 80, 40)
            Level.HARD -> GameSettings(30, 30, 90, 40)
        }
    }
}