package com.example.vitalsign

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

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

    // 새로운 세트 데이터를 설정하는 메서드
    fun setData(newData: MutableList<ExerciseSet>) {
        dataSet = newData
        notifyDataSetChanged() // 데이터가 변경되었음을 알리고 UI를 갱신
    }

    override fun getItemCount() = dataSet.size
}
