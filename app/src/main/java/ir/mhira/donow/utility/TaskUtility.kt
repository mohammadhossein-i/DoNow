package ir.mhira.donow.utility

import ir.mhira.donow.model.TaskModel

fun getFinishedTask(tasks: List<TaskModel>): List<TaskModel> {
    return tasks.filter { it.isFinished }
}

fun getOpenTask(tasks: List<TaskModel>): List<TaskModel> {
    return tasks.filter { !it.isFinished }
}

fun getToDayTask(tasks: List<TaskModel>): List<TaskModel> {
    return tasks.filter { longToDateLongForm(getTodayLong()) == longToDateLongForm(it.date) }
}