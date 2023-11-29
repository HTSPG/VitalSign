package com.example.vitalsign

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//루틴 편집 화면
class RoutineEditActivity : AppCompatActivity() {

    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exercisesAdapter: ExercisesEditAdapter
    private var routine: Routine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_edit)

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

        val btnAddExercise = findViewById<Button>(R.id.btnAddExercise)
        btnAddExercise.setOnClickListener {
            // 운동 목록 화면으로 이동
            val intent = Intent(this, ExerciseListActivity::class.java)
            startActivity(intent)
        }
    }
}
