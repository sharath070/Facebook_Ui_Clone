package com.sharath070.facebookuiclone.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sharath070.facebookuiclone.repository.Repository

class UserViewModelFactory(private val userRepository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository) as T
    }
}