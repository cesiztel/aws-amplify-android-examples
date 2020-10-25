package com.cesarcodecrafter.awsamplifypredictions

class TasksFactory {

    companion object {
        val instance =  TasksFactory
        private val activities : MutableList<Task> = arrayListOf()

        fun getTodayActivities() : MutableList<Task> = activities

        fun deleteTask(position: Int) {
            activities.removeAt(position)
        }

        fun setTask(position: Int, task: Task) {
            if (position < 0 || position > 2) {
                return
            }

            if (position > activities.size - 1) {
                activities.add(task)
            } else {
                activities[position] = task
            }
        }

        fun getTask(position: Int): Task? {
            return if (position > activities.size - 1 || position < 0) {
                null
            } else {
                activities[position]
            }
        }
    }
}