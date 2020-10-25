package com.cesarcodecrafter.upto3

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Todo
import kotlinx.android.synthetic.main.task_item_row.view.*

class TaskRecyclerAdapter(private val tasks: List<Todo>, private val taskHolderListener: (Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_TASK) {
            TaskItemHolder(parent.inflate(R.layout.task_item_row, false))
        } else {
            TaskHolder(parent.inflate(R.layout.task_holder_item_row, false))
        }
    }

    override fun getItemCount() = 3

    override fun getItemViewType(position: Int): Int {
        val task = tasks.elementAtOrNull(position)
        return if (task == null) {
            TYPE_HOLDER
        } else {
            TYPE_TASK
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_HOLDER) {
            (holder as TaskHolder).bind(position, taskHolderListener)
        } else {
            val itemTask = tasks[position]
            (holder as TaskItemHolder).bind(position, itemTask, taskHolderListener)
        }
    }

    class TaskHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view : View = v

        fun bind(position: Int, clickListener: (Int) -> Unit) {
            when (position) {
                0 -> view.task_name.text = view.context.getText(R.string.first_most_important_task_holder)
                1 -> view.task_name.text = view.context.getText(R.string.second_most_important_task_holder)
                else -> view.task_name.text = view.context.getText(R.string.third_most_important_task_holder)
            }

            view.setOnClickListener { clickListener(position) }
        }
    }

    class TaskItemHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var view : View = v
        private var task : Todo? = null

        fun bind(position: Int, task: Todo, clickListener: (Int) -> Unit) {
            this.task = task

            setTaskUIState(task.status, position, clickListener)

            view.task_name.text = task.name
            view.task_state_checkbox.isChecked = task.status
            view.task_state_checkbox.setOnClickListener {
                if (it is CheckBox) {
                    val checked: Boolean = it.isChecked
                    setTaskUIState(checked, position, clickListener)
                }
            }
        }

        private fun setTaskUIState(taskState: Boolean, position: Int, clickListener: (Int) -> Unit) {
            if (taskState) {
                if (view is CardView) {
                    (view as CardView).setCardBackgroundColor(Color.parseColor("#e9ecef"))
                }
                view.setOnClickListener(null)
            } else {
                if (view is CardView) {
                    (view as CardView).setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                }
                view.setOnClickListener { clickListener(position) }
            }
        }
    }

    companion object {
        private const val TYPE_TASK = 1
        private const val TYPE_HOLDER = 2
    }
}

