package com.example.vitalsign

import RoutineAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.Exercise
import com.example.vitalsign.R
import com.example.vitalsign.Routine
import com.example.vitalsign.RoutineDetailActivity
import com.example.vitalsign.RoutineEditActivity

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
                // '추가' 버튼을 클릭할 때의 로직
            }
        )
        routineRecyclerView.layoutManager = LinearLayoutManager(context)
        routineRecyclerView.adapter = routineAdapter

        // 데이터 로드 및 어댑터 업데이트
        loadRoutines()

        // 루틴 추가 버튼 클릭 리스너 설정
        val btnAddRoutine = view.findViewById<Button>(R.id.btnAddRoutine)
        btnAddRoutine.setOnClickListener {
            // TODO: 루틴 추가 화면으로 이동하거나 루틴 추가 로직 구현
        }
    }

    private fun loadRoutines() {
        // 예시 데이터 로딩
        val routines = listOf(
            Routine("1", "Routine 1", "", mutableListOf(Exercise("Exercise 1", 3, 10.0, 10))),
            Routine("2", "Routine 2", "", mutableListOf(Exercise("Exercise 2", 4, 12.5, 8)))
            // 추가 루틴...
        )
        // routineAdapter.updateData(routines)
        // TODO: 루틴 데이터 로딩 로직 구현
    }

}