package com.sharath070.facebookuiclone.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sharath070.facebookuiclone.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(post: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

}