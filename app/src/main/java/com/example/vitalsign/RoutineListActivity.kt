import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.Exercise
import com.example.vitalsign.R
import com.example.vitalsign.Routine

class RoutineListActivity : AppCompatActivity() {

    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_list)

        routineRecyclerView = findViewById(R.id.rvRoutineList)
        routineAdapter = RoutineAdapter(emptyList()) { routine ->
            // TODO: 루틴 아이템 클릭 시 처리 ( 루틴 상세 화면으로 이동)
        }
        routineRecyclerView.layoutManager = LinearLayoutManager(this)
        routineRecyclerView.adapter = routineAdapter

        // 데이터 로드 및 어댑터 업데이트
        loadRoutines()

        // 루틴 추가 버튼 클릭 리스너 설정
        val btnAddRoutine = findViewById<Button>(R.id.btnAddRoutine)
        btnAddRoutine.setOnClickListener {
            // TODO: 루틴 추가 화면으로 이동 또는 루틴 추가 로직 구현
        }
    }

    private fun loadRoutines() {
        // 예시 데이터 로딩
        val routines = listOf(
            Routine("1", "루틴 1", listOf(Exercise("운동 1", 3, 10.0, 10))),
            Routine("2", "루틴 2", listOf(Exercise("운동 2", 4, 12.5, 8))),
            // 추가 루틴...
        )
        routineAdapter.updateData(routines)
        // TODO: 루틴 데이터 로드 로직 구현
    }
}