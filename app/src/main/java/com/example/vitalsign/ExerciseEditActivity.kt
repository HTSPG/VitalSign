package com.example.vitalsign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.example.vitalsign.R

class ExerciseEditActivity : AppCompatActivity() {

    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseSetAdapter
    private lateinit var tvRestTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_edit)

        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)
        setAdapter = ExerciseSetAdapter(mutableListOf())
        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter

        val btnAddSet: Button = findViewById(R.id.btnAddSet)
        btnAddSet.setOnClickListener {
            setAdapter.addSet()
        }

        val btnRemoveSet: Button = findViewById(R.id.btnRemoveSet)
        btnRemoveSet.setOnClickListener {
            setAdapter.removeSet()
        }

        // 휴식 시간 텍스트뷰 초기화
        tvRestTime = findViewById(R.id.tvRestTime)

        // 휴식 시간 증가 버튼
        val btnIncreaseRest: Button = findViewById(R.id.btnIncreaseRest)
        btnIncreaseRest.setOnClickListener {
            adjustRestTime(10)
        }

        // 휴식 시간 감소 버튼
        val btnDecreaseRest: Button = findViewById(R.id.btnDecreaseRest)
        btnDecreaseRest.setOnClickListener {
            adjustRestTime(-10)
        }

        // 완료 버튼
        val btnComplete: Button = findViewById(R.id.btnComplete)
        btnComplete.setOnClickListener {
            // TODO: 운동 변경 사항 저장
            finish()
        }
    }

    // 휴식 시간을 조정하는 함수
    private fun adjustRestTime(amount: Int) {
        val currentRest = tvRestTime.text.toString().substringAfter(": ").toIntOrNull() ?: 60
        val newRest = (currentRest + amount).coerceAtLeast(0)
        tvRestTime.text = "휴식 시간: $newRest 초"
    }
}
