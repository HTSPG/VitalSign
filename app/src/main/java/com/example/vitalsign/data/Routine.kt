package com.example.vitalsign.data

import com.example.vitalsign.data.Exercise
import java.io.Serializable

data class Routine(
    val id: String,                    // 루틴의 고유 식별자
    val name: String,                  // 루틴 이름
    val description: String = "",            //루틴 설명
    val exercises: MutableList<Exercise>  // 운동 목록
) : Serializable
