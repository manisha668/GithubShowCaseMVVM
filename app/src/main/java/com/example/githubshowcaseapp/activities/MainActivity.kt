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
import com.example.githubshowcaseapp.hideLoader
import com.example.githubshowcaseapp.mappers.IssuesDataState
import com.example.githubshowcaseapp.mappers.RepositoryDataState
import com.example.githubshowcaseapp.showLoader
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
        collectRepoIssuesData()

    }

    private fun collectGithubRepositoryData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.networkDataState.collectLatest {
                when (it) {
                    is RepositoryDataState.LoadingState -> {
                        mBinding.loader.showLoader()
                    }
                    is RepositoryDataState.SuccessState -> {
                        setAdapter(it.itemList)
                        mBinding.loader.hideLoader()
                    }
                    is RepositoryDataState.ErrorState -> {
                        mBinding.loader.hideLoader()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setAdapter(itemList: List<Item>?) {
        mBinding.rvItemList.adapter = itemList?.let { GithubRepoAdapter(itemList = it){ item ->
            item?.full_name?.let { it1 -> mViewModel.fetchRepositoriesIssues(fullName = it1) }
        } }
    }

    private fun collectRepoIssuesData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.issuesDataState.collectLatest {
                when (it) {
                    is IssuesDataState.LoadingState -> {
                        Log.d("testIssues loading", it.toString())
                        mBinding.loader.showLoader()
                    }
                    is IssuesDataState.SuccessState -> {
                        Log.d("testIssues", it.issues.toString())
                        mBinding.loader.hideLoader()
                    }
                    is IssuesDataState.ErrorState -> {
                        Log.d("testIssues error", it.error)
                        mBinding.loader.hideLoader()
                    }
                    else -> Unit
                }
            }
        }
    }
}