import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import com.example.vitalsign.R
import com.example.vitalsign.Routine
import com.example.vitalsign.RoutineDetailActivity

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
        recentRoutines = listOf(
            Routine("1","루틴 1"),
            Routine("2", "루틴 2"),
            Routine("3","루틴 3")
        )
        recentRoutineAdapter.updateData(recentRoutines)
    }

    private fun onRoutineClicked(routine: Routine) {
        // TODO: 루틴 아이템 클릭 시 루틴 시작 화면으로 이동
        val intent = Intent(this, RoutineDetailActivity::class.java)
        intent.putExtra("ROUTINE_ID", routine.id)
        startActivity(intent)
    }
}
