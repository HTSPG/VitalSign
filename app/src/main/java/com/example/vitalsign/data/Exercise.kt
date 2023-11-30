package com.example.vitalsign.data

import java.io.Serializable
import java.lang.Math.round
import kotlin.math.roundToInt

data class Exercise(
    val name: String,     // 운동 이름
    val sets: Int,        // 세트 수
    val weight: Double,   // 중량
    val repetitions: Int  // 횟수
) : Serializable


//data class Exercise(
//    val name: String,     // 운동 이름
//    val sets: MutableList<ExerciseSet> // 세트
//) : Serializable{
//
//    //세트 수
//    fun getSetsNum(): Int{
//        return sets.size
//    }
//
//    //평균 중량
//    fun getAverageWeight(): Double{
//        var weightSum = 0.0
//        for (set in sets){
//            weightSum += set.weight
//        }
//        return round(weightSum/getSetsNum()*100)/100.toDouble()
//    }
//
//    //평균 횟수
//    fun getAverageRepetitions(): Int{
//        var repSum = 0
//        for (set in sets){
//            repSum += set.repetitions
//        }
//        return (repSum.toDouble() / getSetsNum()).roundToInt()
//    }
//
//}


