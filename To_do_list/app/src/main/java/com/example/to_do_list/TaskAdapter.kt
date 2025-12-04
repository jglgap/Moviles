import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.Task
import com.example.to_do_list.TasksModelView
import com.example.to_do_list.databinding.ItemTaskBinding
class TaskAdapter(
    private val viewModel: TasksModelView
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var tasks: List<Task> = emptyList()

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Show task name
        holder.binding.taskName.text = task.taskName

        // Show priority chip text
        holder.binding.taskPriorityChip.text = task.priority

        // Set color based on priority
        val color = when (task.priority.lowercase()) {
            "hard" -> Color.RED
            "medium" -> Color.YELLOW
            "easy" -> Color.GREEN
            else -> Color.GRAY
        }
        holder.binding.taskPriorityChip.chipBackgroundColor = ColorStateList.valueOf(color)

        // Set completed chip
        holder.binding.taskCompletedChip.setOnCheckedChangeListener(null) // avoid recycling issues
        holder.binding.taskCompletedChip.isChecked = task.completed
        holder.binding.taskCompletedChip.setOnCheckedChangeListener { _, _ ->
            viewModel.toggleTaskCompleted(task.idtask)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun submitList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}
