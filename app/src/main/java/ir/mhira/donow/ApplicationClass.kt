package ir.mhira.donow

import android.app.Application
import ir.mhira.donow.data.database.DBManager
import ir.mhira.donow.screen.task.TasksViewModel

class ApplicationClass : Application (){
    val sharedViewModel: TasksViewModel by lazy{
        TasksViewModel()
    }

    override fun onCreate() {
        super.onCreate()
        DBManager.startDB(this, sharedViewModel)
    }
}