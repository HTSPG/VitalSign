package com.example.vitalsign

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Routine
import com.example.vitalsign.databinding.ActivityRoutineEditBinding

//루틴 편집 화면
class RoutineEditActivity : AppCompatActivity() {

    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exercisesAdapter: ExercisesEditAdapter

    private lateinit var binding: ActivityRoutineEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트에서 루틴 데이터 가져오기
        val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine
        binding.tvRoutineName.setText(routine?.name)

//        exercisesAdapter = ExercisesEditAdapter(routine?.exercises ?: emptyList(),
//            onDeleteClicked = )


        binding.cancelERBtn.setOnClickListener {
            onBackPressed()
            this.finish()
        }

        binding.submitERBtn.setOnClickListener {
            //루틴 변경하는 기능 구현 필
        }

        exercisesRecyclerView = findViewById(R.id.rvExercises)

        //아이템 간격
        val spaceInPixels = 30 // 픽셀 단위
        exercisesRecyclerView.addItemDecoration(SpacesItemDecoration(spaceInPixels))

        exercisesAdapter = ExercisesEditAdapter(routine?.exercises?.toMutableList() ?: mutableListOf(),
            onDeleteClicked = { position ->
                // 운동 삭제 로직
                Log.i("REA", "onDeleteClicked 함수 내부")
                Log.i("REA_position", position.toString())
                routine?.exercises?.let {
                    it.removeAt(position)
                    exercisesAdapter.notifyItemRemoved(position)
                    exercisesAdapter.notifyItemRangeChanged(position, it.size)
                }
            },
            onAddButtonClicked = {
                // '+ 운동 추가' 버튼을 클릭할 때의 로직
                val intent = Intent(this, ExerciseListActivity::class.java)
                startActivity(intent)
            }
        )
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
