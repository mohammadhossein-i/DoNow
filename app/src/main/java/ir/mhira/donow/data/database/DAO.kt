package ir.mhira.donow.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.mhira.donow.Constants
import ir.mhira.donow.model.TaskModel

@Dao
interface DAO {
    @Insert
    fun insertTask(task: TaskModel)
    @Update
    fun updateTask(task: TaskModel)
    @Delete
    fun deleteTask(take: TaskModel)
    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME}")
    fun getAllTasks(): List<TaskModel>
    @Query("SELECT * FROM ${Constants.TASKS_TABLE_NAME} WHERE id=:id")
    fun getTaskById(id:Int): TaskModel

}