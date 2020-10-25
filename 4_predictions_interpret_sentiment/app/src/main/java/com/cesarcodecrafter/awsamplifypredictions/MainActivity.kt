package com.cesarcodecrafter.awsamplifypredictions

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.core.Amplify
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter : TaskRecyclerAdapter
    private var tasks: MutableList<Task> = arrayListOf(
        Task("Meditate", false, Calendar.getInstance().time, Calendar.getInstance().time),
        Task("Stoic diary", false, Calendar.getInstance().time, Calendar.getInstance().time)
    )
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

    @SuppressLint("SimpleDateFormat")
    private fun initToday() {
        val current = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("EEEE, d MMMM ")
        subtitle.text = simpleDateFormat.format(current)
    }

    private fun taskItemClicked(taskPosition : Int) {
        val intent = Intent(this, TaskRetrospective::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            startActivity(intent)
        }
    }
}