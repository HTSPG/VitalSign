package com.example.vitalsign

data class Routine(
    val id: String,                    // 루틴의 고유 식별자
    val name: String,                  // 루틴 이름
    val exercises: List<Exercise>      // 운동 목록
)
