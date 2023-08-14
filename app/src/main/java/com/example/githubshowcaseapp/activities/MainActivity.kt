package com.example.githubshowcaseapp.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubshowcaseapp.R
import com.example.githubshowcaseapp.adapters.GithubRepoAdapter
import com.example.githubshowcaseapp.constants.AppConstants.NO_CONTRIBUTORS_AVAILABLE
import com.example.githubshowcaseapp.constants.AppConstants.NO_ISSUES_AVAILABLE
import com.example.githubshowcaseapp.databinding.ActivityMainBinding
import com.example.githubshowcaseapp.dialogs.DetailsViewDialog
import com.example.githubshowcaseapp.mappers.ContributorsDataState
import com.example.githubshowcaseapp.mappers.IssuesDataState
import com.example.githubshowcaseapp.mappers.RepositoryDataState
import com.example.githubshowcaseapp.utilities.hideView
import com.example.githubshowcaseapp.utilities.showToast
import com.example.githubshowcaseapp.utilities.showView
import com.example.githubshowcaseapp.viewmodels.CustomViewModelFactory
import com.example.githubshowcaseapp.viewmodels.SharedViewModel
import com.example.network_module.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var mViewModel: SharedViewModel
    private lateinit var mBinding: ActivityMainBinding
    private var issuesString = NO_ISSUES_AVAILABLE
    private var contributorsString = NO_CONTRIBUTORS_AVAILABLE
    private var mDefaultLang = "kotlin"
    private var searchObserverLD = MutableLiveData(mDefaultLang)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider(this, CustomViewModelFactory()).get(SharedViewModel::class.java)
        installSplashScreen()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSearchView()
        mViewModel.fetchRepositoryDetails(mDefaultLang)
        collectGithubRepositoryData()
        collectRepoIssuesData()
        collectRepoContributorsData()
        //observing the livedata to check if any keyword was searched to fetch the api data
        searchObserverLD.observe(this, {
            mViewModel.fetchRepositoryDetails(searchName = it)
        })
    }

    private fun setAdapter(itemList: List<Item>?) {
        mBinding.rvItemList.adapter = itemList?.let {
            GithubRepoAdapter(itemList = it) { item ->
                mBinding.loader.showView()
                item?.full_name?.let { it1 -> mViewModel.fetchIssuesAndContributorDetails(fullName = it1) }
                DetailsViewDialog(
                    this,
                    mBinding.root as ViewGroup,
                    issuesString,
                    contributorsString
                )
            }
        }
    }

    /**
     * fetch repository data from server when a keyword is searched and enter is pressed
     */
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

    /**
     * Collect the repository data from api
     * and set the adapter to show the list
     */
    private fun collectGithubRepositoryData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.networkDataState.collectLatest {
                when (it) {
                    is RepositoryDataState.LoadingState -> {
                        mBinding.loader.showView()
                    }
                    is RepositoryDataState.SuccessState -> {
                        setAdapter(it.itemList)
                        mBinding.loader.hideView()
                    }
                    is RepositoryDataState.ErrorState -> {
                        this@MainActivity.showToast(it.error)
                        mBinding.loader.hideView()
                    }
                    else -> Unit
                }
            }
        }
    }

    /**
     * Collect the issues data from api
     */
    private fun collectRepoIssuesData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.issuesDataState.collectLatest {
                when (it) {
                    is IssuesDataState.LoadingState -> {
                        mBinding.loader.showView()
                    }
                    is IssuesDataState.SuccessState -> {
                        val issuesPair = IssuesDataState.processIssuesData(it.issues)
                        issuesString = getString(
                            R.string.issues,
                            issuesPair.first,
                            issuesPair.second,
                            issuesPair.third
                        )
                        mBinding.loader.hideView()
                    }
                    is IssuesDataState.ErrorState -> {
                        this@MainActivity.showToast(it.error)
                        mBinding.loader.hideView()
                    }
                    else -> Unit
                }
            }
        }
    }

    /**
     * Collect the contributors data from api
     */
    private fun collectRepoContributorsData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.contributors.collectLatest {
                when (it) {
                    is ContributorsDataState.LoadingState -> {
                        mBinding.loader.showView()
                    }
                    is ContributorsDataState.SuccessState -> {
                        val contributionPair =
                            ContributorsDataState.processContributorsData(it.contributions)
                        contributorsString = getString(
                            R.string.contributors,
                            contributionPair.first,
                            contributionPair.second,
                            contributionPair.third
                        )
                        mBinding.loader.hideView()
                    }
                    is ContributorsDataState.ErrorState -> {
                        this@MainActivity.showToast(it.error)
                        mBinding.loader.hideView()
                    }
                    else -> Unit
                }
            }
        }
    }
}