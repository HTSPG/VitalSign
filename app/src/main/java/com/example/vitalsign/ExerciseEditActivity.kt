package com.example.vitalsign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.vitalsign.R
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.ExerciseSet
import com.example.vitalsign.data.Routine

class ExerciseEditActivity : AppCompatActivity() {

    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseSetAdapter
    private lateinit var tvRestTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_edit)

        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)


        // 인텐트에서 운동 데이터 가져오기
        val exercise = intent.getSerializableExtra("EXERCISE_DATA") as? Exercise
        val exerciseNameView = findViewById<EditText>(R.id.tvExerciseName)
        exerciseNameView.setText(exercise?.name ?: "벤치 프레스")

        val exSetMutableList = mutableListOf<ExerciseSet>()
        val setsNum = exercise?.sets ?: 4

        for (i in 1..setsNum) {
            exSetMutableList.add(
                ExerciseSet(
                    setNumber = i,
                    weight = exercise?.weight ?:75.0,
                    repetitions = exercise?.repetitions ?: 10,
                    isCompleted = false
                )
            )
        }

        setAdapter = ExerciseSetAdapter(exSetMutableList)
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
        tvRestTime.text = "$newRest 초"
    }
}
