package com.example.vitalsign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.data.ExerciseSet

class ExerciseSetAdapter(private var dataSet: MutableList<ExerciseSet>) :
    RecyclerView.Adapter<ExerciseSetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val setNumberText: TextView = view.findViewById(R.id.textSetNumber)
        val weightEdit: EditText = view.findViewById(R.id.editWeight)
        val repetitionsEdit: EditText = view.findViewById(R.id.editRepetitions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = dataSet[position]
        holder.setNumberText.text = set.setNumber.toString()
        holder.weightEdit.setText(set.weight.toString())
        holder.repetitionsEdit.setText(set.repetitions.toString())
    }

    override fun getItemCount() = dataSet.size

    fun addSet() {
        val nextSetNumber = if (dataSet.isEmpty()) 1 else dataSet.last().setNumber + 1
        dataSet.add(ExerciseSet(nextSetNumber, 0.0, 0))
        notifyItemInserted(dataSet.size - 1)
    }

    fun removeSet() {
        if (dataSet.isNotEmpty()) {
            val lastIndex = dataSet.size - 1
            dataSet.removeAt(lastIndex)
            notifyItemRemoved(lastIndex)
        }
    }
}
