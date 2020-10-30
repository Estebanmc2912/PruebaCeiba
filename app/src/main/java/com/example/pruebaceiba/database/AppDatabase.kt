package com.example.pruebaceiba.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pruebaceiba.model.User
import com.example.pruebaceiba.model.UserPost

@Database(entities = [User::class, UserPost::class], version = 2,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun users():UsersDao
    abstract fun userPosts():UserPostsDao

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context) : AppDatabase{

            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}