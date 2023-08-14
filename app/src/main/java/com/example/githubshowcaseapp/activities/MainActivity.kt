package com.example.githubshowcaseapp.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.adapters.GithubRepoAdapter
import com.example.githubshowcaseapp.databinding.ActivityMainBinding
import com.example.githubshowcaseapp.hideLoader
import com.example.githubshowcaseapp.mappers.ContributorsDataState
import com.example.githubshowcaseapp.mappers.IssuesDataState
import com.example.githubshowcaseapp.mappers.RepositoryDataState
import com.example.githubshowcaseapp.showLoader
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import com.example.network_module.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var mViewModel: SharedViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var issuesString = ""
    private var contributorsString = ""
    private var searchObserverLD = MutableLiveData<String>("kotlin")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider(this, CustomViewModelFactory()).get(SharedViewModel::class.java)
        installSplashScreen()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSearchView()
        mViewModel.fetchRepositoryDetails("kotlin")
        collectGithubRepositoryData()
        collectRepoIssuesData()
        collectRepoContributorsData()
        searchObserverLD.observe(this, Observer {
            mViewModel.fetchRepositoryDetails(searchName = it)
        })
    }

    private fun setAdapter(itemList: List<Item>?) {
        mBinding.rvItemList.adapter = itemList?.let {
            GithubRepoAdapter(itemList = it) { item ->
                    mBinding.loader.showLoader()
                    item?.full_name?.let { it1 -> mViewModel.fetchIssuesAndContributorDetails(fullName = it1) }
                showRepoDetailsDialog()
            }
        }
    }

    private fun setSearchView() {
        mBinding.searchView.apply {
            isActivated = true
            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchObserverLD.value = newText
                    return false
                }
            })

        }
    }

    private fun showRepoDetailsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_custom_dialog)
        val issuesText = dialog.findViewById<TextView>(R.id.tv_issues)
        val contributorsText = dialog.findViewById<TextView>(R.id.tv_contributors)
        val confirmBrn = dialog.findViewById<Button>(R.id.bt_dialog)
        issuesText.text = issuesString
        contributorsText.text = contributorsString
        confirmBrn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

    private fun collectRepoIssuesData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.issuesDataState.collectLatest {
                when (it) {
                    is IssuesDataState.LoadingState -> {
                        Log.d("testIssues loading", it.toString())
                        mBinding.loader.showLoader()
                    }
                    is IssuesDataState.SuccessState -> {
                        val issuesPair =IssuesDataState.processIssuesData(it.issues)
                        Log.d("testIssues success", it.issues.toString())
                        issuesString = getString(R.string.issues,issuesPair.first,issuesPair.second,issuesPair.third)
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

    private fun collectRepoContributorsData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.contributors.collectLatest {
                when (it) {
                    is ContributorsDataState.LoadingState -> {
                        Log.d("testIssues contr loading", it.toString())
                        mBinding.loader.showLoader()
                    }
                    is ContributorsDataState.SuccessState -> {
                        Log.d("testIssues success", it.contributions.toString())
                        val contributionPair = ContributorsDataState.processContributorsData(it.contributions)
                        contributorsString = getString(R.string.contributors,contributionPair.first,contributionPair.second,contributionPair.third)
                        mBinding.loader.hideLoader()
                    }
                    is ContributorsDataState.ErrorState -> {
                        Log.d("testIssues contr error", it.error)
                        mBinding.loader.hideLoader()
                    }
                    else -> Unit
                }
            }
        }
    }
}