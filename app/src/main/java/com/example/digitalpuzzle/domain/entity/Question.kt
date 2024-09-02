package com.example.digitalpuzzle.domain.entity

data class Question(
     val sumNumber: Int,
     val visibleNumber: Int,
     val answers: List<Int>,
) {
     val rightAnswer: Int
          get() = sumNumber - visibleNumber
}