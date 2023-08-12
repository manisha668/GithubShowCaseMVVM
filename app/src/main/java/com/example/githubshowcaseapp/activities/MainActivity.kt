package com.example.githubshowcaseapp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.adapters.GithubRepoAdapter
import com.example.githubshowcaseapp.databinding.ActivityMainBinding
import com.example.githubshowcaseapp.mappers.NetworkDataState
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import com.example.network_module.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var mViewModel: SharedViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider(this, CustomViewModelFactory()).get(SharedViewModel::class.java)
        installSplashScreen()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel.fetchRepositoryDetails("kotlin")
        collectGithubRepositoryData()

    }

    private fun collectGithubRepositoryData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.networkDataState.collectLatest {
                when (it) {
                    is NetworkDataState.LoadingState -> {
                        Log.d("apiTest loading", "loading state")
                    }
                    is NetworkDataState.SuccessState -> {
                        setAdapter(it.itemList)
                    }
                    is NetworkDataState.ErrorState -> {
                        Log.d("apiTest error", it.error)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setAdapter(itemList: List<Item>?) {
        mBinding.rvItemList.adapter = itemList?.let { GithubRepoAdapter(itemList = it) }
    }
}