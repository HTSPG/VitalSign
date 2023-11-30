package com.example.vitalsign
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Routine

//루틴 시작화면
class RoutineDetailActivity : AppCompatActivity() {

    private lateinit var exercisesRecyclerView: RecyclerView
    private lateinit var exercisesAdapter: ExercisesAdapter // 운동 목록을 위한 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_detail)

        val tvRoutineName = findViewById<TextView>(R.id.tvRoutineName)
        val btnStartRoutine = findViewById<Button>(R.id.btnStartRoutine)
        val btnEditRoutine = findViewById<Button>(R.id.btnEditRoutine)

        // 인텐트에서 루틴 데이터 가져오기
        val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine
        tvRoutineName.text = routine?.name

        // 운동 목록 RecyclerView 설정
        exercisesRecyclerView = findViewById(R.id.rvExercises)
        exercisesAdapter = ExercisesAdapter(routine?.exercises ?: emptyList())

        //아이템 간격
        val spaceInPixels = 30 // 픽셀 단위
        exercisesRecyclerView.addItemDecoration(SpacesItemDecoration(spaceInPixels))


        exercisesRecyclerView.layoutManager = LinearLayoutManager(this)
        exercisesRecyclerView.adapter = exercisesAdapter

        // 루틴 시작 버튼 클릭 리스너
        btnStartRoutine.setOnClickListener {
            // 인텐트에서 현재 루틴 데이터 가져오기
            val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine
            routine?.let {
                val recordIntent = Intent(this, ExerciseRecordActivity::class.java)
                recordIntent.putExtra("ROUTINE_DATA", it)
                startActivity(recordIntent)
            }
        }

        // 루틴 편집 버튼 클릭 리스너
        btnEditRoutine.setOnClickListener {
            // 인텐트에서 현재 루틴 데이터 가져오기
            val routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine
            routine?.let {
                val editIntent = Intent(this, RoutineEditActivity::class.java)
                editIntent.putExtra("ROUTINE_DATA", it)
                startActivity(editIntent)
            }
        }
    }
}
