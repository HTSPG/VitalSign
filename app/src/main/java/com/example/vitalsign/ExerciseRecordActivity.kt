package com.example.vitalsign

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.ExerciseSet
import com.example.vitalsign.data.Routine

//운동 기록 화면
class ExerciseRecordActivity : AppCompatActivity() {

    private lateinit var exerciseTitle: TextView
    private lateinit var tvTimer: TextView
    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseRecordSetAdapter
    private lateinit var tvRestTime: TextView
    private lateinit var restTimerHandler: Handler
    private lateinit var restTimerRunnable: Runnable
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

/*        // 첫 번째 운동 이름 설정
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
*/
        val firstExercise = routine?.exercises?.firstOrNull()
        exerciseTitle.text = firstExercise?.name ?: "운동 이름"
        val initialSetsData = List(firstExercise?.sets ?: 0) { index ->
            ExerciseSet(
                setNumber = index + 1,  // 세트 번호 초기화
                weight = firstExercise?.weight ?: 0.0,     // 초기 중량
                repetitions = firstExercise?.repetitions ?: 0, // 초기 횟수
                isCompleted = false     // 초기 완료 상태
            )
        }
        setAdapter = ExerciseRecordSetAdapter(initialSetsData.toMutableList())

        // 리사이클러뷰 설정
        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter

        /*
        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter
*/
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


        findViewById<Button>(R.id.btnNextExercise).setOnClickListener {
            moveToNextExercise()
        }
        setupRestTimer()
        findViewById<Button>(R.id.btnReset).setOnClickListener {
            // restTimeInSeconds 값을 재설정하지 않고,
            // 휴식 타이머를 현재 설정된 시간부터 다시 시작합니다.
            restTimerHandler.removeCallbacks(restTimerRunnable)
            restTimerHandler.postDelayed(restTimerRunnable, 1000)
        }

        startTimer()
    }

    private fun setupRestTimer() {
        restTimerHandler = Handler()
        restTimerRunnable = object : Runnable {
            override fun run() {
                if (restTimeInSeconds > 0) {
                    restTimeInSeconds--
                    tvRestTime.text = "${restTimeInSeconds}초"
                    restTimerHandler.postDelayed(this, 1000)
                }
            }
        }
    }

    private fun adjustRestTime(amount: Int) {
        restTimeInSeconds += amount
        restTimeInSeconds = restTimeInSeconds.coerceAtLeast(0) // 음수가 되지 않도록 보장
        tvRestTime.text = "${restTimeInSeconds}초"
    }

    private fun resetTimer() {
        timer?.cancel()
        startTimer()
    }

    private var secondsElapsed = 0
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable

/*    private fun startTimer() {
        timerHandler = Handler()
        timerRunnable = object : Runnable {
            override fun run() {
                secondsElapsed++
                val minutes = secondsElapsed / 60
                val seconds = secondsElapsed % 60
                tvTimer.text = String.format("%02d:%02d", minutes, seconds)
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.post(timerRunnable)
    }
*/
    private fun startTimer() {
        timerRunnable = object : Runnable {
            override fun run() {
                secondsElapsed++
                val minutes = secondsElapsed / 60
                val seconds = secondsElapsed % 60
                tvTimer.text = String.format("%02d:%02d", minutes, seconds)
                timerHandler.postDelayed(this, 1000)
            }
        }

        // Check if the timerHandler is initialized before removing callbacks
        if (this::timerHandler.isInitialized) {
            timerHandler.removeCallbacks(timerRunnable)
        }

        timerHandler = Handler()
        timerHandler.post(timerRunnable)
    }


    override fun onPause() {
        super.onPause()
        timerHandler.removeCallbacks(timerRunnable)
    }

    override fun onResume() {
        super.onResume()
        // 기존 타이머를 중지하고 다시 시작
        if (this::timerHandler.isInitialized) {
            timerHandler.removeCallbacks(timerRunnable)
        }
        startTimer()
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
            stopTimer()
            finish()
        }
    }

    private fun stopTimer() {
        timerHandler.removeCallbacks(timerRunnable)
        // 타이머 중지 후 관련 변수 초기화
        secondsElapsed = 0
        //tvTimer.text = "00:00"
    }

    private fun updateExercise(exercise: Exercise?) {
        exercise?.let {
            exerciseTitle.text = it.name
            // 각 세트에 대한 데이터 생성
            val setsData = List(it.sets) { index ->
                ExerciseSet(
                    setNumber = index + 1,  // 세트 번호는 index + 1로 설정하여 고유하게 만듦
                    weight = it.weight,     // 중량
                    repetitions = it.repetitions, // 횟수
                    isCompleted = false     // 완료 여부
                )
            }
            setAdapter.setData(setsData.toMutableList())
        }
    }
}
