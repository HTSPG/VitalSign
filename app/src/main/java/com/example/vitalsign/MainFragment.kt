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
import androidx.viewpager2.widget.ViewPager2
import com.example.vitalsign.R
import com.example.vitalsign.RoutineDetailActivity
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.Routine

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

        //뷰페이저
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        // '내 루틴' 버튼 클릭 이벤트 설정
        val myRoutineButton = view.findViewById<Button>(R.id.btnMyRoutine)
        myRoutineButton.setOnClickListener {
//            val intent = Intent(activity, RoutineListActivity::class.java)
//            startActivity(intent)
            viewPager?.setCurrentItem(1, true)
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
            Routine(
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
        val intent = Intent(activity, RoutineDetailActivity::class.java)
        intent.putExtra("ROUTINE_DATA", routine)
        startActivity(intent)
    }
}