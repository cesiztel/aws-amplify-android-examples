package com.cesarcodecrafter.awsamplifyapigraphql

import com.amplifyframework.datastore.generated.model.PostStatus
import com.amplifyframework.datastore.generated.model.Todo

class TasksFactory {

    companion object {
        val instance =  TasksFactory
        private val activities : MutableList<Todo> = arrayListOf(
            Todo.builder().name("Write article of AWS Amplify").status(PostStatus.UNCOMPLETED).build(),
            Todo.builder().name( "Do the grocery").status(PostStatus.UNCOMPLETED).build()
        )

        fun getTodayActivities() : List<Todo> = activities

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