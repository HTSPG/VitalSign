package com.example.vitalsign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExercisesEditAdapter(
    private var exercises: MutableList<Exercise>,
    private val onDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<ExercisesEditAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_edit, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise, position, onDeleteClicked)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvExerciseName)
        private val deleteButton: Button = itemView.findViewById(R.id.btnDeleteExercise)

        fun bind(exercise: Exercise, position: Int, onDeleteClicked: (Int) -> Unit) {
            nameTextView.text = exercise.name
            deleteButton.setOnClickListener { onDeleteClicked(position) }
        }
    }
}
