package com.sharath070.facebookuiclone.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.sharath070.facebookuiclone.R
import com.sharath070.facebookuiclone.db.UserDatabase
import com.sharath070.facebookuiclone.repository.Repository
import com.sharath070.facebookuiclone.ui.fragments.HomeFragment
import com.sharath070.facebookuiclone.viewModels.UserViewModel
import com.sharath070.facebookuiclone.viewModels.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postsRepository = Repository(UserDatabase.getDatabase(this))
        val viewModelProviderFactory = UserViewModelFactory(postsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[UserViewModel::class.java]


        sharedPreferences = getSharedPreferences("isLoggedIn", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("login", false)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        if (isLoggedIn){
            navController.navigate(R.id.homeFragment)
        }
        else{
            navController.navigate(R.id.loginFragment)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()

        // Check if the current fragment is the homeFragment
        if (currentFragment is HomeFragment) {
            // Exit the app
            finish()
        } else {
            super.onBackPressed()
        }
    }
}