package com.example.digitalpuzzle.domain.usecases

import com.example.digitalpuzzle.domain.entity.Question
import com.example.digitalpuzzle.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue: Int) : Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_ANSWERS)
    }
    companion object {
        private const val COUNT_OF_ANSWERS = 6
    }
}