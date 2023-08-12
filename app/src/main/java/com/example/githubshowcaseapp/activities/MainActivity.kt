package com.example.githubshowcaseapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.mappers.NetworkDataState
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

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
        collectGithubRepositoryData()

    }

    fun collectGithubRepositoryData() {
        lifecycleScope.launchWhenStarted {
            viewModel.networkDataState.collectLatest {
              when(it){
                  is NetworkDataState.LoadingState -> {
                      Log.d("apiTest loading", "loading state")
                  }
                  is NetworkDataState.SuccessState -> {
                      Log.d("apiTest success", it.itemList.toString())
                  }
                  is NetworkDataState.ErrorState -> {
                      Log.d("apiTest error", it.error)
                  }
                  else -> Unit
              }
            }
        }
    }
}