package com.example.vitalsign.data

import java.io.Serializable

data class ExerciseSet(
    var setNumber: Int,    // 세트 번호
    var weight: Double,    // 중량
    var repetitions: Int,  // 횟수
    var isCompleted: Boolean = false  // 완료 여부
) : Serializable
