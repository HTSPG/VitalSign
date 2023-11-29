package com.example.vitalsign

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.R

//운동 기록 화면
class ExerciseRecordActivity : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseSetAdapter
    private lateinit var tvRestTime: TextView
    private var restTimeInSeconds = 60
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_record)

        tvTimer = findViewById(R.id.tvTimer)
        tvRestTime = findViewById(R.id.tvRestTime)
        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)
        setAdapter = ExerciseSetAdapter(mutableListOf(
            // 예시 데이터
            ExerciseSet(1, 100.0, 10, false),
            ExerciseSet(2, 100.0, 10, false)
            // 추가 세트...
        ))
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
}
