package com.example.vitalsign
import RecentRoutineAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.R
import com.example.vitalsign.data.Routine
import com.example.vitalsign.RoutineDetailActivity

//메인 화면
class MainScreen : AppCompatActivity() {

    private lateinit var recentRoutineRecyclerView: RecyclerView
    private lateinit var recentRoutineAdapter: RecentRoutineAdapter
    private var recentRoutines: List<Routine> = listOf() // 최근 루틴 데이터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // 운동 기록 그래프 설정 (mainGraphData 활용)
        setupExerciseGraph()

        // 최근 수행 루틴 리사이클러뷰 설정
        recentRoutineRecyclerView = findViewById(R.id.recentRoutineRecyclerView)
        recentRoutineAdapter = RecentRoutineAdapter(recentRoutines) { routine ->
            onRoutineClicked(routine)
        }
        recentRoutineRecyclerView.layoutManager = LinearLayoutManager(this)
        recentRoutineRecyclerView.adapter = recentRoutineAdapter

        // '나의 루틴' 버튼 클릭 이벤트 설정
        val myRoutineButton = findViewById<Button>(R.id.btnMyRoutine)
        myRoutineButton.setOnClickListener {
            val intent = Intent(this, RoutineListActivity::class.java)
            startActivity(intent)
        }

        // 데이터 로딩
        loadRecentRoutines()
    }

    private fun setupExerciseGraph() {
        // TODO: mainGraphData를 활용하여 그래프 설정
    }

    private fun loadRecentRoutines() {
        // TODO: 최근 루틴 데이터 로딩 로직 (최대 3개)
        // 예시 데이터 로딩
        recentRoutines = listOf(Routine(
            "1",
            "상체 루틴",
            "",
            mutableListOf(
                Exercise("벤치 프레스", 4, 75.0, 10),
                Exercise("덤벨 플라이", 3, 20.0, 12),
                Exercise("랫 풀 다운", 4, 60.0, 10),
                Exercise("숄더 프레스", 4, 50.0, 10),
                Exercise("바이셉스 컬", 3, 15.0, 12)
            )
        ),
            Routine(
                "2",
                "하체 루틴",
                "",
                mutableListOf(
                    Exercise("스쿼트", 4, 100.0, 10),
                    Exercise("데드리프트", 3, 120.0, 8),
                    Exercise("레그 프레스", 4, 150.0, 10),
                    Exercise("레그 컬", 3, 40.0, 12),
                    Exercise("캐프 레이즈", 4, 30.0, 15)
                )
            ),
            Routine(
                "3",
                "전신 루틴",
                "",
                mutableListOf(
                    Exercise("풀업", 3, 0.0, 10),
                    Exercise("푸시업", 4, 0.0, 15),
                    Exercise("런지", 3, 20.0, 12),
                    Exercise("플랭크", 3, 0.0, 60), // 시간은 초 단위
                    Exercise("버피 테스트", 2, 0.0, 15)
                )
            ))
        recentRoutineAdapter.updateData(recentRoutines)
    }

    private fun onRoutineClicked(routine: Routine) {
        // TODO: 루틴 아이템 클릭 시 루틴 시작 화면으로 이동
        val intent = Intent(this, RoutineDetailActivity::class.java)
        intent.putExtra("ROUTINE_ID", routine)
        startActivity(intent)
    }
}
