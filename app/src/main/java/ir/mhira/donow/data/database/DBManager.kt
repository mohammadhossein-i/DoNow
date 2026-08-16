package ir.mhira.donow.data.database

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import ir.mhira.donow.Constants
import ir.mhira.donow.model.TaskModel
import ir.mhira.donow.screen.task.TasksViewModel
import kotlinx.coroutines.launch

object DBManager {
    private lateinit var appDBManager: AppDBManager
    private lateinit var viewModel: TasksViewModel

    fun startDB(context: Context, viewModel: TasksViewModel) {
        this.viewModel = viewModel
        this.appDBManager = Room.databaseBuilder(
            context,
            AppDBManager::class.java,
            Constants.TASKS_TABLE_NAME
        ).allowMainThreadQueries().build()
    }

    fun addTask(task: TaskModel) {
        try {
            viewModel.viewModelScope.launch {
                appDBManager.appDao().insertTask(task)
                viewModel.setTasks(getTasks())
            }
        } catch (e: Exception) {
        }
    }

    fun updateTask(task: TaskModel) {
        try {
            viewModel.viewModelScope.launch {
                appDBManager.appDao().updateTask(task)
                viewModel.setTasks(getTasks())
            }
        } catch (e: Exception) {
        }
    }

    fun deleteTask(task: TaskModel) {
        try {
            viewModel.viewModelScope.launch {
                appDBManager.appDao().deleteTask(task)
                viewModel.setTasks(getTasks())
            }
        } catch (e: Exception) {
        }
    }

    fun doTask(task: TaskModel) {
        updateTask(task.copy(isFinished = !task.isFinished))
    }

    fun getTasks(): List<TaskModel> = appDBManager.appDao().getAllTasks()

    fun getTaskById(id: Int): TaskModel = appDBManager.appDao().getTaskById(id)
}