package com.example.vitalsign

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Rational
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.ExerciseSet
import com.example.vitalsign.data.Routine


import androidx.lifecycle.ViewModel
import androidx.activity.viewModels

class ExerciseRecordViewModel : ViewModel() {
    // LiveData로 선언
    val exerciseName = MutableLiveData<String>()
    val restTimeInSeconds = MutableLiveData<Int>()
    val secondsElapsed = MutableLiveData<Int>()

    val currentExerciseIndex = MutableLiveData<Int>()

    var isTimerRunning = false
    var isRestTimerRunning = false

    private var timerHandler = Handler()
    private lateinit var timerRunnable: Runnable

    private val restTimerHandler = Handler(Looper.getMainLooper())
    private lateinit var restTimerRunnable: Runnable

    init {
        exerciseName.value = "운동 이름"
        restTimeInSeconds.value = 60
        secondsElapsed.value = 0

        currentExerciseIndex.value = 0

        startTimer() // ViewModel 초기화 시 타이머 시작
        setupRestTimer()
    }

    fun setCurrentExercise(exercise: Exercise) {
        exerciseName.value = exercise.name
    }

    fun setCurrentExerciseIndex(index: Int) {
        currentExerciseIndex.value = index
        // 운동 정보 업데이트 로직도 여기에 추가할 수 있습니다.
        //exerciseName.value = // 운동 이름 업데이트
        // 다른 필요한 업데이트 로직
    }

    fun startTimer() {
        if (!isTimerRunning) {
            timerRunnable = object : Runnable {
                override fun run() {
                    val newTime = (secondsElapsed.value ?: 0) + 1
                    secondsElapsed.postValue(newTime)
                    timerHandler.postDelayed(this, 1000)
                }
            }
            timerHandler.post(timerRunnable)
            isTimerRunning = true
        }
    }


    fun stopTimer() {
        timerHandler.removeCallbacks(timerRunnable)
    }

    private fun setupRestTimer() {
        restTimerRunnable = object : Runnable {
            override fun run() {
                val currentTime = restTimeInSeconds.value ?: 0
                if (currentTime > 0) {
                    restTimeInSeconds.postValue(currentTime - 1)
                    restTimerHandler.postDelayed(this, 1000)
                } else {
                    isRestTimerRunning = false
                }
            }
        }
    }

    fun startRestTimer() {
        if (!isRestTimerRunning) {
            isRestTimerRunning = true
            restTimerHandler.postDelayed(restTimerRunnable, 1000)
        }
    }

    fun stopRestTimer() {
        if (isRestTimerRunning) {
            restTimerHandler.removeCallbacks(restTimerRunnable)
            isRestTimerRunning = false
        }
    }

    // 필요한 다른 메서드 추가
}



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

    private val viewModel: ExerciseRecordViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_record)

        // ViewModel의 LiveData 관찰하여 UI 업데이트
        viewModel.exerciseName.observe(this, Observer { name ->
            exerciseTitle.text = name
        })

        viewModel.restTimeInSeconds.observe(this, Observer { time ->
            tvRestTime.text = "$time 초"
        })

        viewModel.secondsElapsed.observe(this, Observer { time ->
            val minutes = time / 60
            val seconds = time % 60
            tvTimer.text = String.format("%02d:%02d", minutes, seconds)
        })

        viewModel.currentExerciseIndex.observe(this, Observer { index ->
            currentExerciseIndex = index
            val exercise = routineExercises?.getOrNull(index)
            exercise?.let {
                updateExercise(it)
            }
        })

        // 타이머 시작
        viewModel.startTimer()

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

//            아래 코드인데 주석처리해 놓겠습니다.
//            viewModel.restTimeInSeconds.value = 60
//            viewModel.stopRestTimer()
//            viewModel.startRestTimer()
        }

        startTimer()
    }

    private fun setupRestTimer() {
        restTimerHandler = Handler()
        restTimerRunnable = object : Runnable {
            override fun run() {
                if (restTimeInSeconds > 0) {
                    restTimeInSeconds--
                    //여기서 viewModel도 같이 감소 시켜볼까?
                    tvRestTime.text = "${restTimeInSeconds}초"
                    restTimerHandler.postDelayed(this, 1000)
                } else if(restTimeInSeconds == 0) {
                    restTimeInSeconds = 60
                }
                val currentTime = viewModel.restTimeInSeconds.value ?: 0
                if (currentTime > 0) {
                    viewModel.restTimeInSeconds.postValue(currentTime - 1)
                    //restTimerHandler.postDelayed(this, 1000)
                } else if (currentTime == 0) {
                    viewModel.restTimeInSeconds.value = 60
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


    private fun updateTimerDisplay(time: Int) {
        val minutes = time / 60
        val seconds = time % 60
        tvTimer.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onPause() {
        super.onPause()
        if (!isInPictureInPictureMode) {
            viewModel.stopTimer()
            viewModel.stopRestTimer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isInPictureInPictureMode) {
            viewModel.startTimer()
            if (viewModel.isRestTimerRunning) {
                viewModel.startRestTimer()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    /*private fun moveToNextExercise() {
        // 다음 인덱스로 이동
       /* currentExerciseIndex++

        val nextExercise = routineExercises?.getOrNull(currentExerciseIndex)
        nextExercise?.let {
            viewModel.setCurrentExercise(it)
        }

        // 다음 운동이 리스트에 존재하는지 확인하고 업데이트
        if (currentExerciseIndex < (routineExercises?.size ?: 0)) {
            updateExercise(routineExercises?.get(currentExerciseIndex))
        } else {
            // 다음 운동이 없는 경우 처리 (예: 화면 종료 또는 메시지 표시)
            Toast.makeText(this, "모든 운동을 완료했습니다.", Toast.LENGTH_SHORT).show()
            stopTimer()
            finish()
        }*/
        val nextIndex = (viewModel.currentExerciseIndex.value ?: 0) + 1
        if (nextIndex < (routineExercises?.size ?: 0)) {
            viewModel.setCurrentExerciseIndex(nextIndex)
            val nextExercise = routineExercises?.getOrNull(nextIndex)
            nextExercise?.let {
                viewModel.setCurrentExercise(it)
            }
        } else {
            Toast.makeText(this, "모든 운동을 완료했습니다.", Toast.LENGTH_SHORT).show()
            viewModel.stopTimer()
            finish()
        }
    }*/
    private fun moveToNextExercise() {
        val currentIndex = viewModel.currentExerciseIndex.value ?: 0
        val nextIndex = currentIndex + 1

        if (nextIndex < (routineExercises?.size ?: 0)) {
            // 다음 운동으로 업데이트
            viewModel.setCurrentExerciseIndex(nextIndex)
            val nextExercise = routineExercises?.getOrNull(nextIndex)
            nextExercise?.let {
                viewModel.setCurrentExercise(it)
                updateExercise(it)
            }
        } else {
            Toast.makeText(this, "모든 운동을 완료했습니다.", Toast.LENGTH_SHORT).show()
            viewModel.stopTimer()
            finish()
        }
    }


    private fun stopTimer() {
        timerHandler.removeCallbacks(timerRunnable)
        // 타이머 중지 후 관련 변수 초기화
        secondsElapsed = 0
        //tvTimer.text = "00:00"
    }

    /*private fun updateExercise(exercise: Exercise?) {
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
    }*/

    private fun updateExercise(exercise: Exercise) {
        exerciseTitle.text = exercise.name
        val setsData = List(exercise.sets) { index ->
            ExerciseSet(
                setNumber = index + 1,
                weight = exercise.weight,
                repetitions = exercise.repetitions,
                isCompleted = false
            )
        }
        setAdapter.setData(setsData.toMutableList())
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        val params = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(16, 9)) // 비율 설정
            .build()
        enterPictureInPictureMode(params)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {
            // PiP 모드로 전환 시 PiP 전용 레이아웃 사용
            setContentView(R.layout.activity_exercise_record_pip)
            // PiP 레이아웃의 뷰 바인딩
            bindPipViews()
            //viewModel.startTimer() // PiP 모드에서 타이머 재개
        } else {
            // 일반 모드로 전환 시 원래의 레이아웃 사용
            setContentView(R.layout.activity_exercise_record)
            // 기존 레이아웃의 뷰 바인딩
            bindRegularViews()
            //viewModel.startTimer() // 일반 모드에서 타이머 재개
            refreshRecyclerView()
        }
    }

    private fun bindPipViews() {
        // PiP 모드 뷰 바인딩
        val tvPipTimer: TextView = findViewById(R.id.tvPipTimer)
        val tvPipRestTime: TextView = findViewById(R.id.tvPipRestTime)
        val tvPipExerciseName: TextView = findViewById(R.id.tvPipExerciseName)

        // ViewModel의 LiveData 관찰
        viewModel.secondsElapsed.observe(this, Observer { time ->
            val minutes = time / 60
            val seconds = time % 60
            tvPipTimer.text = String.format("%02d:%02d", minutes, seconds)
        })

        viewModel.restTimeInSeconds.observe(this, Observer { time ->
            tvPipRestTime.text = "휴식시간 : $time 초"
        })

        viewModel.exerciseName.observe(this, Observer { name ->
            tvPipExerciseName.text = name
        })

        tvPipExerciseName.text = viewModel.exerciseName.value

    }

    private fun bindRegularViews() {
        // 일반 레이아웃의 뷰 바인딩 및 초기화
        tvTimer = findViewById(R.id.tvTimer)
        tvRestTime = findViewById(R.id.tvRestTime)
        exerciseTitle = findViewById(R.id.exerciseTitle)
        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)

        // RecyclerView 및 어댑터 설정
        setAdapter = ExerciseRecordSetAdapter(mutableListOf())
        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter

        // '다음 운동' 버튼
        findViewById<Button>(R.id.btnNextExercise).setOnClickListener {
            moveToNextExercise()
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            // restTimeInSeconds 값을 재설정하지 않고,
            // 휴식 타이머를 현재 설정된 시간부터 다시 시작합니다.
            restTimerHandler.removeCallbacks(restTimerRunnable)
            restTimerHandler.postDelayed(restTimerRunnable, 1000)
        }
        findViewById<Button>(R.id.btnIncreaseRest).setOnClickListener {
            adjustRestTime(10)
        }

        findViewById<Button>(R.id.btnDecreaseRest).setOnClickListener {
            adjustRestTime(-10)
        }

        // ViewModel의 LiveData 관찰하여 UI 업데이트
        viewModel.exerciseName.observe(this, Observer { name ->
            exerciseTitle.text = name
        })

        viewModel.restTimeInSeconds.observe(this, Observer { time ->
            tvRestTime.text = "$time 초"
        })

        viewModel.secondsElapsed.observe(this, Observer { time ->
            val minutes = time / 60
            val seconds = time % 60
            tvTimer.text = String.format("%02d:%02d", minutes, seconds)
        })

        // 리사이클러뷰 및 기타 뷰 초기화
        // ... 여기에 리사이클러뷰 및 기타 필요한 뷰의 초기화 로직을 추가합니다.

        refreshRecyclerView()
    }
    private fun refreshRecyclerView() {
        // 현재 선택된 운동을 가져옵니다.
        val currentExercise = routineExercises?.getOrNull(currentExerciseIndex)
        currentExercise?.let { exercise ->
            // 현재 운동의 세트 데이터를 생성합니다.
            val setsData = List(exercise.sets) { index ->
                ExerciseSet(
                    setNumber = index + 1,
                    weight = exercise.weight,
                    repetitions = exercise.repetitions,
                    isCompleted = false
                )
            }
            // RecyclerView 어댑터에 데이터를 설정합니다.
            setAdapter.setData(setsData.toMutableList())
        }
    }
}
