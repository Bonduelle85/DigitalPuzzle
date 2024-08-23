package com.example.digitalpuzzle.domain.entity

data class Question(
    private val sumNumber: Int,
    private val visibleNumber: Int,
    private val answers: List<Int>,
)