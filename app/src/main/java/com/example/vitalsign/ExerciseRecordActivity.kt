package com.example.vitalsign

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.R

//운동 기록 화면
class ExerciseRecordActivity : AppCompatActivity() {

    private lateinit var exerciseTitle: TextView
    private lateinit var tvTimer: TextView
    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseRecordSetAdapter
    private lateinit var tvRestTime: TextView
    private var restTimeInSeconds = 60
    private var timer: CountDownTimer? = null
    private var currentExerciseIndex = 0 // 현재 운동 인덱스
    private var routineExercises: List<Exercise>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_record)

        val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine
        routineExercises = routine?.exercises

        tvTimer = findViewById(R.id.tvTimer)
        tvRestTime = findViewById(R.id.tvRestTime)
        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)
        exerciseTitle = findViewById(R.id.exerciseTitle) // TextView 연결
        // 나머지 초기화...

        // 첫 번째 운동 이름 설정
        exerciseTitle.text = routine?.exercises?.firstOrNull()?.name ?: "운동 이름"

        // 루틴에서 운동 세트 데이터를 어댑터에 설정
        setAdapter = ExerciseRecordSetAdapter(routine?.exercises?.map { exercise ->
            ExerciseSet(
                setNumber = exercise.sets,
                weight = exercise.weight,
                repetitions = exercise.repetitions,
                isCompleted = false
            )
        }?.toMutableList() ?: mutableListOf())

        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter

        findViewById<Button>(R.id.btnAddSet).setOnClickListener {
            setAdapter.addSet()
        }

        findViewById<Button>(R.id.btnRemoveSet).setOnClickListener {
            setAdapter.removeSet()
        }

        findViewById<Button>(R.id.btnIncreaseRest).setOnClickListener {
            adjustRestTime(10)
        }

        findViewById<Button>(R.id.btnDecreaseRest).setOnClickListener {
            adjustRestTime(-10)
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            resetTimer()
        }

        findViewById<Button>(R.id.btnNextExercise).setOnClickListener {
            moveToNextExercise()
        }

        startTimer()
    }

    private fun adjustRestTime(amount: Int) {
        restTimeInSeconds = (restTimeInSeconds + amount).coerceAtLeast(0)
        tvRestTime.text = "휴식 시간: ${restTimeInSeconds}초"
    }

    private fun resetTimer() {
        timer?.cancel()
        startTimer()
    }

    private fun startTimer() {
        tvTimer.text = "00:00"
        // 여기에 타이머 로직 구현
        // 예: 1분 타이머
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                tvTimer.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                tvTimer.text = "완료"
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun moveToNextExercise() {
        // 다음 인덱스로 이동
        currentExerciseIndex++

        // 다음 운동이 리스트에 존재하는지 확인하고 업데이트
        if (currentExerciseIndex < (routineExercises?.size ?: 0)) {
            updateExercise(routineExercises?.get(currentExerciseIndex))
        } else {
            // 다음 운동이 없는 경우 처리 (예: 화면 종료 또는 메시지 표시)
            Toast.makeText(this, "모든 운동을 완료했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateExercise(exercise: Exercise?) {
        exercise?.let {
            exerciseTitle.text = it.name
            // 각 세트에 대한 데이터 생성
            val setsData = List(it.sets) { index ->
                ExerciseSet(
                    setNumber = index + 1,  // 세트 번호
                    weight = it.weight,     // 중량
                    repetitions = it.repetitions, // 횟수
                    isCompleted = false     // 완료 여부
                )
            }
            setAdapter.setData(setsData.toMutableList())
        }
    }

}
