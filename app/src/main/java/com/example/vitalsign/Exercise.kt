package com.example.vitalsign

import java.io.Serializable

data class Exercise(
    val name: String,     // 운동 이름
    val sets: Int,        // 세트 수
    val weight: Double,   // 중량
    val repetitions: Int  // 횟수
) : Serializable
