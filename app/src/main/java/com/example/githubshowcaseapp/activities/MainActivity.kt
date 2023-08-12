package com.example.githubshowcaseapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import com.example.network_module.network.NetworkCallStatus
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,CustomViewModelFactory()).get(SharedViewModel::class.java)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        lifecycleScope.launchWhenStarted {
            viewModel.fetchRepositoryDetails("Android").collect {
             when(it) {
                 is NetworkCallStatus.Loading -> {
                     Log.d("apiTest loading", it.data.toString())
                 }
                 is NetworkCallStatus.Success -> {
                    Log.d("apiTest success", it.data.toString())
                 }
                 is NetworkCallStatus.Error -> {
                     Log.d("apiTest error", it.msg.toString())
                 }
             }
            }
        }
    }
}