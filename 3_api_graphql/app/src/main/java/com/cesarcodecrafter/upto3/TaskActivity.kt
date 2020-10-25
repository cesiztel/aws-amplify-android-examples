package com.cesarcodecrafter.upto3

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Todo
import com.cesarcodecrafter.upto3.data.TasksFactory
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {

    private var taskPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        supportActionBar?.hide()

        taskPosition = intent.getIntExtra(EXTRA_TASK, -1)

        var task = TasksFactory.getTask(taskPosition)
        if (task  == null) {
            loadTaskHolderLayout(taskPosition)
        } else {
            loadTask(task, taskPosition)
        }

        setListeners()
    }

    private fun setListeners() {
        add_task_submit_button.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        if (taskPosition < 0) {
            return
        }

        if (task_name.text.isEmpty()) {
            deleteTask()
        } else {
            updateTask()
        }
    }

    private fun updateTask() {
        val task = TasksFactory.instance.getTask(position = taskPosition)
        if (task == null) {
            createTask()

            return
        }

        if (task_name.text.isEmpty()) {
            deleteTask()

            return
        }

        if (task_name.text.toString() != task.name) {
            updateNameTask()
        }
    }

    private fun deleteTask() {
        val task = TasksFactory.instance.getTask(taskPosition)
        task?.let {
            Amplify.API.mutate(
                ModelMutation.delete(task),
                { Handler(Looper.getMainLooper()).post { returnToMainScreenAfterDeleted() } },
                { error -> showMessage(error.message) }
            )
        }
    }

    private fun updateNameTask() {
        val task = TasksFactory.instance.getTask(taskPosition)
        task?.let {
            task.copyOfBuilder().name(task_name.text.toString()).build()
            Amplify.API.mutate(
                ModelMutation.update(task),
                { Handler(Looper.getMainLooper()).post { returnToMainScreenAfterCreated() } },
                { error -> showMessage(error.message) }
            )
        }
    }

    private fun createTask() {
        Amplify.API.mutate(
            ModelMutation.create(
                Todo.builder()
                    .name(task_name.text.toString())
                    .order(taskPosition)
                    .status(false)
                    .createdAt(Calendar.getInstance().time.toString())
                    .updatedAt(Calendar.getInstance().time.toString())
                    .build()),
            { Handler(Looper.getMainLooper()).post { returnToMainScreenAfterCreated() } },
            { error -> showMessage(error.message) }
        )
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun returnToMainScreenAfterDeleted() {
        TasksFactory.instance.deleteTask(position = taskPosition)

        onBackPressed()
    }

    private fun returnToMainScreenAfterCreated() {
        val task = TasksFactory.instance.getTask(position = taskPosition)
        task?.let {
            task.copyOfBuilder().name(task_name.text.toString()).build()
            TasksFactory.instance.setTask(taskPosition, task)
        }


        onBackPressed()
    }

    private fun loadTaskHolderLayout(position: Int) {
        when (position) {
            0 -> subtitle.text = getString(R.string.think_first_most_important_task_holder)
            1 -> subtitle.text = getString(R.string.think_second_most_important_task_holder)
            else -> subtitle.text = getString(R.string.think_third_most_important_task_holder)
        }

        task_name.hint = getString(R.string.start_here)
    }

    private fun loadTask(task: Todo, position: Int) {
        loadTaskHolderLayout(position)

        task_name.setText(task.name)
    }
}
