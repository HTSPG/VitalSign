import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vitalsign.R
import com.example.vitalsign.RoutineDetailActivity
import com.example.vitalsign.RoutineEditActivity
import com.example.vitalsign.data.Routine

class RoutineAdapter(
    private var routines: List<Routine>,
    private val onItemClicked: (Routine) -> Unit,
    private val onAddButtonClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (position == routines.size) VIEW_TYPE_BUTTON else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_routine, parent, false)
                RoutineViewHolder(view, onItemClicked)
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
        if (holder is RoutineViewHolder) {
            val routine = routines[position]
            holder.bind(routine)
        }
        // AddButtonViewHolder에 대한 별도의 바인딩 로직은 필요 없습니다.
    }

    override fun getItemCount(): Int = routines.size + 1

    fun updateData(newRoutines: List<Routine>) {
        routines = newRoutines
        notifyDataSetChanged()
    }

    class RoutineViewHolder(
        itemView: View,
        private val onItemClicked: (Routine) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.tvRoutineName)
        private val imageView: ImageView = itemView.findViewById(R.id.menuBtn)

        fun bind(routine: Routine) {
            nameTextView.text = routine.name
            itemView.setOnClickListener { onItemClicked(routine) }
            imageView.setOnClickListener {
                onImageClicked(routine)
            }
        }

        private fun onImageClicked(routine: Routine) {
            val context = itemView.context
            val dialog = AlertDialog.Builder(context)
                .setTitle("루틴 설정")
                .setMessage("원하는 동작을 선택하세요.")
                .setPositiveButton("루틴 선택") { _, _ ->
                    val intent = Intent(context, RoutineDetailActivity::class.java)
                    intent.putExtra("ROUTINE_DATA", routine)
                    context.startActivity(intent)
                }
                .setNegativeButton("루틴 편집") { _, _ ->
                    val intent = Intent(context, RoutineEditActivity::class.java)
                    intent.putExtra("ROUTINE_DATA", routine)
                    context.startActivity(intent)
                }
                .setNeutralButton("취소", null)
                .create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(context, R.color.accent)
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
            itemView.findViewById<View>(R.id.addButton).setOnClickListener { onAddButtonClicked() }
        }
        // 뷰 홀더 구현...
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_BUTTON = 1
    }

}