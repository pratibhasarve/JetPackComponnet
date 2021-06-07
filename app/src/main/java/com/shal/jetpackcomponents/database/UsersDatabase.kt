package com.shal.jetpackcomponents.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shal.jetpackcomponents.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun userDao() : UsersDao

    companion object{

        private var INSTANCE: UsersDatabase? = null

        fun getDatabase(context: Context): UsersDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java,
                "users_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}