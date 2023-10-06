package com.sharath070.facebookuiclone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}