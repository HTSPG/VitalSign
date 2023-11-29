package com.example.vitalsign
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.example.vitalsign.R
import com.example.vitalsign.Exercise

class ExerciseListActivity : AppCompatActivity() {

    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        exerciseRecyclerView = findViewById(R.id.rvExerciseList)
        exerciseAdapter = ExerciseAdapter(getExercises()) { exercise ->
            onExerciseClicked(exercise)
        }
        exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        exerciseRecyclerView.adapter = exerciseAdapter
    }

    private fun getExercises(): List<Exercise> {
        // TODO: 기본적으로 제공하는 운동 목록을 반환
        return listOf(
            Exercise("운동 1", 3, 10.0, 10),
            // ... 기타 운동들 ...
        )
    }

    private fun onExerciseClicked(exercise: Exercise) {
        // TODO: 운동 편집 화면으로 이동
        val intent = Intent(this, ExerciseEditActivity::class.java)
        intent.putExtra("EXERCISE_DATA", exercise)
        startActivity(intent)
    }
}
