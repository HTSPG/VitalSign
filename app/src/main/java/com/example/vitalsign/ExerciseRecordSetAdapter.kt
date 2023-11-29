package com.example.vitalsign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseRecordSetAdapter(private var dataSet: MutableList<ExerciseSet>) :
    RecyclerView.Adapter<ExerciseRecordSetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val setNumberText: TextView = view.findViewById(R.id.textSetNumber)
        val weightEdit: EditText = view.findViewById(R.id.editWeight)
        val repetitionsEdit: EditText = view.findViewById(R.id.editRepetitions)
        val completedCheckBox: CheckBox = view.findViewById(R.id.checkBoxCompleted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise_record_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val set = dataSet[position]
        holder.setNumberText.text = set.setNumber.toString()
        holder.weightEdit.setText(set.weight.toString())
        holder.repetitionsEdit.setText(set.repetitions.toString())
        holder.completedCheckBox.isChecked = set.isCompleted

        holder.completedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            set.isCompleted = isChecked
        }
    }

    override fun getItemCount() = dataSet.size
}
