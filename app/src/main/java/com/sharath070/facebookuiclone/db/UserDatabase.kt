package com.sharath070.facebookuiclone.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sharath070.facebookuiclone.model.User


@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun getArticleDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}