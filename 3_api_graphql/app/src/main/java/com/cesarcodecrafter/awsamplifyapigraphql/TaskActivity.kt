package com.cesarcodecrafter.awsamplifyapigraphql

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.datastore.generated.model.PostStatus
import com.amplifyframework.datastore.generated.model.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.subtitle
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    private var taskPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        taskPosition = intent.getIntExtra(EXTRA_TASK, -1)

        var task = TasksFactory.getTask(taskPosition)
        if (task  == null) {
            loadTaskHolderLayout(taskPosition)
        } else {
            loadTask(task, taskPosition)
        }
    }

    override fun onBackPressed() {
        saveTask()

        super.onBackPressed()
    }

    private fun saveTask() {
        if (taskPosition < 0) {
            return
        }

        if (task_name.text.isEmpty()) {
            TasksFactory.instance.deleteTask(position = taskPosition)
        } else {
            TasksFactory.instance.setTask(
                taskPosition,
                Todo.builder().name(task_name.text.toString()).status(PostStatus.UNCOMPLETED).build()
            )
        }
    }

    private fun loadTaskHolderLayout(position: Int) {
        when (position) {
            0 -> subtitle.text = "Think about your first most important task today"
            1 -> subtitle.text = "Think about your second most important task today"
            else -> subtitle.text = "Think about your third most important task today"
        }

        task_name.hint = "Start to write here..."
    }

    private fun loadTask(task: Todo, position: Int) {
        loadTaskHolderLayout(position)

        task_name.setText(task.name)
    }
}
