package com.example.vitalsign

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.Exercise

class ExercisesEditAdapter(
//    private var exercises: MutableList<Exercise>,
    private var exercises: List<Exercise>,
    private val onDeleteClicked: (Int) -> Unit,
    private val onAddButtonClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == exercises.size) VIEW_TYPE_BUTTON else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_exercise_edit, parent, false)
                ExerciseViewHolder(view)
            }
            VIEW_TYPE_BUTTON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_button, parent, false)
                AddButtonViewHolder(view, onAddButtonClicked)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ExerciseViewHolder) {
            val exercise = exercises[position]
            holder.bind(exercise, position, onDeleteClicked)
        }
    }

    override fun getItemCount(): Int = exercises.size + 1

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvExerciseName)
//        private val deleteButton: Button = itemView.findViewById(R.id.btnDeleteExercise)
        private val imageView: ImageView = itemView.findViewById(R.id.menuBtn)

        fun bind(exercise: Exercise, position: Int, onDeleteClicked: (Int) -> Unit) {
            nameTextView.text = exercise.name
//            deleteButton.setOnClickListener { onDeleteClicked(position) }
            imageView.setOnClickListener {
                onImageClicked(exercise, position, onDeleteClicked)
            }
        }

        private fun onImageClicked(exercise: Exercise, position: Int, onDeleteClicked: (Int) -> Unit) {
            val context = itemView.context
            val dialog = AlertDialog.Builder(context)
                .setTitle("운동 설정")
                .setMessage("원하는 동작을 선택하세요.")
                .setPositiveButton("운동 삭제") { _, _ ->
                    onDeleteClicked(position)
                }
                .setNegativeButton("운동 편집") { _, _ ->
                    val intent = Intent(context, ExerciseEditActivity::class.java)
                    intent.putExtra("EXERCISE_DATA", exercise)
                    context.startActivity(intent)
                }
                .setNeutralButton("취소", null)
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED)
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setTypeface(positiveButton.typeface, Typeface.BOLD)
            positiveButton.setTextColor(
                ContextCompat.getColor(context, R.color.normal)
            )
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(context, R.color.dialNormal)
            )
        }
    }

    class AddButtonViewHolder(
        itemView: View,
        private val onAddButtonClicked: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            val addButton = itemView.findViewById<Button>(R.id.addButton)
            addButton.text = "+ 운동 추가"
            addButton.setOnClickListener { onAddButtonClicked() }
        }
        // 뷰 홀더 구현...
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_BUTTON = 1
    }
}
