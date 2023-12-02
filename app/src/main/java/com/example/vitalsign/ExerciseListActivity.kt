package com.example.vitalsign
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.ImageView
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.Routine

//운동 목록 화면
class ExerciseListActivity : AppCompatActivity() {

    private lateinit var exerciseRecyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var backBtnView: ImageView
    private var routine:Routine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        routine = intent.getSerializableExtra("ROUTINE_DATA") as? Routine

        exerciseRecyclerView = findViewById(R.id.rvExerciseList)
        exerciseAdapter = ExerciseAdapter(getExercises()) { exercise ->
            onExerciseClicked(exercise, routine)
        }
        backBtnView = findViewById(R.id.backBtn)
        backBtnView.setOnClickListener {
            onBackPressed()
        }



        //아이템 간격
        val spaceInPixels = 20 // 픽셀 단위
        exerciseRecyclerView.addItemDecoration(SpacesItemDecoration(spaceInPixels))

        exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        exerciseRecyclerView.adapter = exerciseAdapter
    }

    private fun getExercises(): List<Exercise> {
        return listOf(
            Exercise("벤치 프레스", 4, 75.0, 10),
            Exercise("덤벨 플라이", 3, 20.0, 12),
            Exercise("랫 풀 다운", 4, 60.0, 10),
            Exercise("숄더 프레스", 4, 50.0, 10),
            Exercise("바이셉스 컬", 3, 15.0, 12),
            Exercise("스쿼트", 4, 100.0, 10),
            Exercise("데드리프트", 3, 120.0, 8),
            Exercise("레그 프레스", 4, 150.0, 10),
            Exercise("레그 컬", 3, 40.0, 12),
            Exercise("캐프 레이즈", 4, 30.0, 15),
            Exercise("풀업", 3, 0.0, 10),
            Exercise("푸시업", 4, 0.0, 15),
            Exercise("런지", 3, 20.0, 12),
            Exercise("플랭크", 3, 0.0, 60), // 시간은 초 단위
            Exercise("버피 테스트", 2, 0.0, 15),
            Exercise("크런치", 3, 0.0, 15),
            Exercise("러시안 트위스트", 3, 0.0, 20),
            Exercise("플랭크", 3, 0.0, 60), // 시간은 초 단위
            Exercise("마운틴 클라이머", 3, 0.0, 20),
            Exercise("레그 레이즈", 3, 0.0, 12),
            Exercise("조깅", 1, 0.0, 900), // 시간은 초 단위, 15분
            Exercise("자전거 타기", 1, 0.0, 600), // 시간은 초 단위, 10분
            Exercise("스킵 로프", 3, 0.0, 120), // 시간은 초 단위, 2분
            Exercise("스텝업", 3, 0.0, 20),
            Exercise("버피", 3, 0.0, 15)

        )
    }

    private fun onExerciseClicked(exercise: Exercise, routine: Routine?) {
        val intent = Intent(this, ExerciseEditActivity::class.java)
        intent.putExtra("EXERCISE_DATA", exercise)
        if (routine != null){
            intent.putExtra("ROUTINE_DATA", routine)
        }

        startActivity(intent)
    }
}
