package com.sharath070.facebookuiclone.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.facebookuiclone.model.User
import com.sharath070.facebookuiclone.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: Repository) : ViewModel() {

    fun saveUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.upsert(user)
    }

    fun getUsers() = userRepository.getUsers()

}