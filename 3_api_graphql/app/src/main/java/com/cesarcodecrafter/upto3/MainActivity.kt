package com.cesarcodecrafter.upto3

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Todo
import com.cesarcodecrafter.upto3.data.TasksFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_TASK = "com.cesarcodecrafter.upto3.EXTRA_TASK"

class MainActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter : TaskRecyclerAdapter
    private var tasks: MutableList<Todo> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskRecyclerAdapter(tasks) {
                holderClicked: Int -> taskItemClicked(holderClicked)
        }

        recyclerView = task_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        initToday()
    }

    override fun onResume() {
        super.onResume()

        executeGetTodo()
    }

    private fun executeGetTodo() {
        Amplify.API.query(
            ModelQuery.list(Todo::class.java),
            { response ->
                processTodo(response.data.items.iterator().asSequence().toList())
            },
            { error -> showMessage("Query failure: ${error.message}") }
        )
    }

    private fun showMessage(message: String?) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun processTodo(taskList: List<Todo>) {
        Handler(Looper.getMainLooper()).post {
            tasks.clear()
            for (task in taskList) {
                TasksFactory.instance.setTask(task.order, task)
                tasks.add(task)
            }

            viewAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initToday() {
        val current = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("EEEE, d MMMM ")
        subtitle.text = simpleDateFormat.format(current)
    }

    private fun taskItemClicked(taskPosition : Int) {
        val intent = Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskPosition)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent)
        }
    }
}
