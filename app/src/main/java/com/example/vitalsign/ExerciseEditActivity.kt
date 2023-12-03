package com.example.vitalsign

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.vitalsign.R
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.ExerciseSet
import com.example.vitalsign.data.Routine

class ExerciseEditActivity : AppCompatActivity() {

    private lateinit var setRecyclerView: RecyclerView
    private lateinit var setAdapter: ExerciseSetAdapter
    private lateinit var tvRestTime: TextView
    private lateinit var exeImgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_edit)

        setRecyclerView = findViewById(R.id.recyclerViewExerciseSets)

        val backBtnView = findViewById<ImageView>(R.id.backBtn)
        backBtnView.setOnClickListener {
            onBackPressed()
        }

        val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine

        // 인텐트에서 운동 데이터 가져오기
        val exercise = intent.getSerializableExtra("EXERCISE_DATA") as? Exercise
        val exerciseNameView = findViewById<EditText>(R.id.tvExerciseName)
        val exeName = exercise?.name ?: "벤치 프레스"
        exerciseNameView.setText(exeName)


        val exSetMutableList = mutableListOf<ExerciseSet>()
        val setsNum = exercise?.sets ?: 4
        val exeWeight = exercise?.weight ?:75.0
        val exeRep = exercise?.repetitions ?: 10

        val exeInst = Exercise(
            exeName,
            setsNum,
            exeWeight,
            exeRep
        )

        for (i in 1..setsNum) {
            exSetMutableList.add(
                ExerciseSet(
                    setNumber = i,
                    weight = exeWeight,
                    repetitions = exeRep,
                    isCompleted = false
                )
            )
        }

        setAdapter = ExerciseSetAdapter(exSetMutableList)
        setRecyclerView.layoutManager = LinearLayoutManager(this)
        setRecyclerView.adapter = setAdapter
        exeImgView = findViewById(R.id.imgExercise)

        when(exeName){
            "벤치 프레스"-> exeImgView.setImageResource(R.drawable.bench_press)
            "덤벨 플라이"-> exeImgView.setImageResource(R.drawable.dumbbell_fly)
            "랫 풀 다운"-> exeImgView.setImageResource(R.drawable.lat_pull_down)
            "숄더 프레스"-> exeImgView.setImageResource(R.drawable.shoulder_press)
            "바이셉스 컬"-> exeImgView.setImageResource(R.drawable.biceps_curl)
            else->exeImgView.setImageResource(R.drawable.bench_press)
        }

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
            val intent = Intent(this, RoutineEditActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = FLAG_ACTIVITY_CLEAR_TOP
            if (routine != null){
                routine.exercises.add(
                    exeInst
                )
                intent.putExtra("ROUTINE_DATA", routine)
            }
            else{
                val newRoutine = Routine(
                    "6",
                    "",
                    "",
                    mutableListOf(
                        exeInst
                    )
                )
                intent.putExtra("ROUTINE_DATA", newRoutine)
            }
            startActivity(intent)
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
