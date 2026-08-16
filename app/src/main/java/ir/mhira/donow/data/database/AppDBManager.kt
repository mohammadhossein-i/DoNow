package ir.mhira.donow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.mhira.donow.model.TaskModel

@Database(entities = [TaskModel::class], version = 1)
abstract class AppDBManager: RoomDatabase(){
    abstract fun appDao() : DAO
}