package com.sharath070.facebookuiclone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val userName: String,
    val password: String
)