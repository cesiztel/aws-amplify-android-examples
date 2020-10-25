package com.cesarcodecrafter.upto3.data

import com.amplifyframework.datastore.generated.model.Todo
import com.cesarcodecrafter.upto3.models.Task
import java.util.*

class TasksFactory {

    companion object {
        val instance =  TasksFactory
        private val activities : MutableList<Todo> = arrayListOf()

        fun getTodayActivities() : MutableList<Todo> = activities

        fun deleteTask(position: Int) {
            activities.removeAt(position)
        }

        fun setTask(position: Int, task: Todo) {
            if (position < 0 || position > 2) {
                return
            }

            if (position > activities.size - 1) {
                activities.add(task)
            } else {
                activities[position] = task
            }
        }

        fun getTask(position: Int): Todo? {
            return if (position > activities.size - 1 || position < 0) {
                null
            } else {
                activities[position]
            }
        }
    }
}