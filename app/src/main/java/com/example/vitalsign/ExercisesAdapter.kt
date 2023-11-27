package com.example.vitalsign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExercisesAdapter(
    private val exercises: List<Exercise>
) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvExerciseName)
        private val setsTextView: TextView = itemView.findViewById(R.id.tvExerciseSets)
        private val weightTextView: TextView = itemView.findViewById(R.id.tvExerciseWeight)
        private val repetitionsTextView: TextView = itemView.findViewById(R.id.tvExerciseRepetitions)

        fun bind(exercise: Exercise) {
            nameTextView.text = exercise.name
            setsTextView.text = "세트: ${exercise.sets}"
            weightTextView.text = "중량: ${exercise.weight}kg"
            repetitionsTextView.text = "횟수: ${exercise.repetitions}"
        }
    }
}
