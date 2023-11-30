package com.example.vitalsign
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.R
import com.example.vitalsign.data.Exercise

class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onItemClicked: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_list, parent, false)
        return ExerciseViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class ExerciseViewHolder(itemView: View, private val onItemClicked: (Exercise) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tvExerciseName)

        fun bind(exercise: Exercise) {
            nameTextView.text = exercise.name
            itemView.setOnClickListener { onItemClicked(exercise) }
        }
    }
}
