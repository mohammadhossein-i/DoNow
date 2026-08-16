package ir.mhira.donow.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mhira.donow.Constants


data class TasksGroupModel(val id:Int, val title:String, val icon: Int, val memberTasks:Int)

@Entity(tableName = Constants.TASKS_TABLE_NAME)
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var isFinished: Boolean,
    var date:Long
)