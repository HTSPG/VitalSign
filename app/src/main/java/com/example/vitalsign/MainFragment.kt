package com.example.vitalsign

import RecentRoutineAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.vitalsign.Exercise
import com.example.vitalsign.R
import com.example.vitalsign.Routine
import com.example.vitalsign.RoutineDetailActivity

class MainFragment : Fragment() {
    private lateinit var recentRoutineRecyclerView: RecyclerView
    private lateinit var recentRoutineAdapter: RecentRoutineAdapter
    private var recentRoutines: List<Routine> = listOf() // 최근 수행한 루틴 데이터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        return inflater.inflate(R.layout.main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 운동 기록 그래프 설정 (mainGraphData 사용)
        setupExerciseGraph()

        // 최근 수행한 루틴 리사이클러 뷰 설정
        recentRoutineRecyclerView = view.findViewById(R.id.recentRoutineRecyclerView)
        recentRoutineAdapter = RecentRoutineAdapter(recentRoutines) { routine ->
            onRoutineClicked(routine)
        }
        recentRoutineRecyclerView.layoutManager = LinearLayoutManager(context)
        recentRoutineRecyclerView.adapter = recentRoutineAdapter

        //아이템 간격
        val spaceInPixels = 30 // 픽셀 단위
        recentRoutineRecyclerView.addItemDecoration(SpacesItemDecoration(spaceInPixels))

        // '내 루틴' 버튼 클릭 이벤트 설정
        val myRoutineButton = view.findViewById<Button>(R.id.btnMyRoutine)
        myRoutineButton.setOnClickListener {
            val intent = Intent(activity, RoutineListActivity::class.java)
            startActivity(intent)
        }

        // 데이터 로딩
        loadRecentRoutines()
    }

    private fun setupExerciseGraph() {
        // TODO: 그래프 설정을 여기에 구현합니다.
    }

    private fun loadRecentRoutines() {
        // TODO: 최근 루틴 데이터 로딩 로직 (최대 3개)
        // 예시 데이터 로딩
        recentRoutines = listOf(
            Routine("1", "Routine 1", "", mutableListOf(Exercise("Exercise 1", 3, 10.0, 10))),
            Routine("2", "Routine 2", "", mutableListOf(Exercise("Exercise 2", 4, 12.5, 8)))
        )
        recentRoutineAdapter.updateData(recentRoutines)
    }

    private fun onRoutineClicked(routine: Routine) {
        // TODO: 루틴 아이템 클릭 시 루틴 시작 화면으로 이동
        val intent = Intent(activity, RoutineDetailActivity::class.java)
        intent.putExtra("ROUTINE_ID", routine.id)
        startActivity(intent)
    }
}