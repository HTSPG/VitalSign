package com.example.vitalsign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.databinding.ActivityMainBinding
import com.example.vitalsign.databinding.ActivityRoutineEditBinding

//루틴 편집 화면
class RoutineEditActivity : AppCompatActivity() {

    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exercisesAdapter: ExercisesEditAdapter
    private var routine: Routine? = null

    private lateinit var binding: ActivityRoutineEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelERBtn.setOnClickListener {
            onBackPressed()
            this.finish()
        }

        binding.submitERBtn.setOnClickListener {
            //루틴 변경하는 기능 구현 필
        }

        routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine

        val tvRoutineName = findViewById<TextView>(R.id.tvRoutineName)
        tvRoutineName.text = routine?.name

        exercisesRecyclerView = findViewById(R.id.rvExercises)
        exercisesAdapter = ExercisesEditAdapter(routine?.exercises?.toMutableList() ?: mutableListOf()) { position ->
            // 운동 삭제 로직
            routine?.exercises?.removeAt(position)
            exercisesAdapter.notifyItemRemoved(position)
        }
        exercisesRecyclerView.layoutManager = LinearLayoutManager(this)
        exercisesRecyclerView.adapter = exercisesAdapter


        //운동 목록 화면으로 이동하는 버튼 다른 것으로 대체
//        val btnAddExercise = findViewById<Button>(R.id.btnAddExercise)
//        btnAddExercise.setOnClickListener {
//            // 운동 목록 화면으로 이동
//            val intent = Intent(this, ExerciseListActivity::class.java)
//            startActivity(intent)
//        }
    }
}
