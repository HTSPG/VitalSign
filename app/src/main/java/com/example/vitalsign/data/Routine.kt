package com.example.vitalsign.data

import com.example.vitalsign.data.Exercise
import java.io.Serializable

data class Routine(
    val id: String,                    // 루틴의 고유 식별자
    var name: String,                  // 루틴 이름
    var description: String = "",            //루틴 설명
    val exercises: MutableList<Exercise>  // 운동 목록
) : Serializable
