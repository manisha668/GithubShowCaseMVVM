package com.example.githubshowcaseapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, CustomViewModelFactory()).get(SharedViewModel::class.java)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        viewModel.fetchRepositoryDetails("Android")
    }
}