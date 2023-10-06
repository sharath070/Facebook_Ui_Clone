package com.sharath070.facebookuiclone.repository

import com.sharath070.facebookuiclone.db.UserDatabase
import com.sharath070.facebookuiclone.model.User

class Repository(private val db: UserDatabase) {

    fun upsert(postData: User) = db.getArticleDao().upsert(postData)

    suspend fun getUsers() = db.getArticleDao().getAllUsers()


}