package ir.mhira.donow.screen.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mhira.donow.data.database.DBManager
import ir.mhira.donow.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel : ViewModel(){
    private val _tasks = MutableLiveData<List<TaskModel>>()
    val tasks: LiveData<List<TaskModel>> get() = _tasks

    fun loadTaskFromDatabase() {
        viewModelScope.launch {
            val loadedTasks= withContext(Dispatchers.IO) {
                DBManager.getTasks()
            }
            _tasks.value = loadedTasks
        }
    }

    fun setTasks(tasks: List<TaskModel>){
        _tasks.value = tasks
    }


}