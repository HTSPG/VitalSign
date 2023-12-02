package com.example.vitalsign

import RoutineAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Exercise
import com.example.vitalsign.data.Routine


class RoutineListFragment : Fragment() {
    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var routineAdapter: RoutineAdapter

    val routines = listOf(
        Routine("1", "Routine 1", "", mutableListOf(Exercise("Exercise 1", 3, 10.0, 10))),
        Routine("2", "Routine 2", "", mutableListOf(Exercise("Exercise 2", 4, 12.5, 8)))
        // 추가 루틴...
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프래그먼트의 레이아웃을 인플레이트합니다.
        return inflater.inflate(R.layout.activity_routine_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        routineRecyclerView = view.findViewById(R.id.rvRoutineList)

        //아이템 간격
        val spaceInPixels = 30 // 픽셀 단위
        routineRecyclerView.addItemDecoration(SpacesItemDecoration(spaceInPixels))

        routineAdapter = RoutineAdapter(
            routines,
            onItemClicked = { routine ->
                // 루틴 아이템을 클릭하면 루틴 상세 화면으로 이동합니다.
                val intent = Intent(activity, RoutineDetailActivity::class.java)
                intent.putExtra("ROUTINE_DATA", routine)
                startActivity(intent)
            },
            onAddButtonClicked = {
                // '+ 루틴 추가' 버튼을 클릭할 때의 로직
                val intent = Intent(activity, RoutineEditActivity::class.java)
                startActivity(intent)
            }
        )
        routineRecyclerView.layoutManager = LinearLayoutManager(context)
        routineRecyclerView.adapter = routineAdapter

        // 데이터 로드 및 어댑터 업데이트
        loadRoutines()

//        // 루틴 추가 버튼 클릭 리스너 설정
//        val btnAddRoutine = view.findViewById<Button>(R.id.btnAddRoutine)
//        btnAddRoutine.setOnClickListener {
//            // TODO: 루틴 추가 화면으로 이동하거나 루틴 추가 로직 구현
//        }
    }

    private fun loadRoutines() {
        // 예시 데이터 로딩
//        val routines = listOf(
//            Routine("1", "Routine 1", "", mutableListOf(Exercise("Exercise 1", 3, 10.0, 10), Exercise("Exercise 3", 3, 10.0, 10))),
//            Routine("2", "Routine 2", "", mutableListOf(Exercise("Exercise 2", 4, 12.5, 8)))
//        )

        val routines = listOf(
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
            ),
            Routine(
                "4",
                "코어 강화 루틴",
                "",
                mutableListOf(
                    Exercise("크런치", 3, 0.0, 15),
                    Exercise("러시안 트위스트", 3, 0.0, 20),
                    Exercise("플랭크", 3, 0.0, 60), // 시간은 초 단위
                    Exercise("마운틴 클라이머", 3, 0.0, 20),
                    Exercise("레그 레이즈", 3, 0.0, 12)
                )
            ),
            Routine(
                "5",
                "카디오 및 지구력 루틴",
                "",
                mutableListOf(
                    Exercise("조깅", 1, 0.0, 900), // 시간은 초 단위, 15분
                    Exercise("자전거 타기", 1, 0.0, 600), // 시간은 초 단위, 10분
                    Exercise("스킵 로프", 3, 0.0, 120), // 시간은 초 단위, 2분
                    Exercise("스텝업", 3, 0.0, 20),
                    Exercise("버피", 3, 0.0, 15)
                )
            )
        )


         routineAdapter.updateData(routines)
        // TODO: 루틴 데이터 로딩 로직 구현
    }

    private fun onRoutineClicked(routine: Routine) {
        // TODO: 루틴 아이템 클릭 시 루틴 시작 화면으로 이동
        val intent = Intent(activity, RoutineDetailActivity::class.java)
        intent.putExtra("ROUTINE_ID", routine.id)
        startActivity(intent)
    }
}