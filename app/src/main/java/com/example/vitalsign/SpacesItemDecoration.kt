package com.example.vitalsign

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // 첫 번째 아이템에만 상단 여백을 추가
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}