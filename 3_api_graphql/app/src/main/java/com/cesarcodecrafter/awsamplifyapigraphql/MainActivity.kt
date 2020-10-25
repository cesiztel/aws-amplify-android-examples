package com.cesarcodecrafter.awsamplifyapigraphql

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Todo
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_TASK = "com.cesarcodecrafter.awsamplifyapigraphql.EXTRA_TASK"

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter : TaskRecyclerAdapter
    private var tasks: List<Todo> = TasksFactory.instance.getTodayActivities()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        linearLayoutManager = LinearLayoutManager(this)
        task_recycler_view.layoutManager = linearLayoutManager

        adapter = TaskRecyclerAdapter(tasks) {
                holderClicked: Int -> taskItemClicked(holderClicked)
        }
        task_recycler_view.adapter = adapter

        initToday()
    }

    override fun onResume() {
        super.onResume()

        tasks = TasksFactory.instance.getTodayActivities()
        adapter.notifyDataSetChanged()
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