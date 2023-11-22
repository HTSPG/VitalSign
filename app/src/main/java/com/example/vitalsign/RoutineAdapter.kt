import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.R
import com.example.vitalsign.Routine

class RoutineAdapter(
    private var routines: List<Routine>,
    private val onItemClicked: (Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_routine, parent, false)
        return RoutineViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.bind(routine)
    }

    override fun getItemCount(): Int = routines.size

    fun updateData(newRoutines: List<Routine>) {
        routines = newRoutines
        notifyDataSetChanged()
    }

    class RoutineViewHolder(
        itemView: View,
        private val onItemClicked: (Routine) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.tvRoutineName)

        fun bind(routine: Routine) {
            nameTextView.text = routine.name
            itemView.setOnClickListener { onItemClicked(routine) }
        }
    }
}
