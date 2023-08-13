package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubshowcaseapp.mappers.ContributorsDataState
import com.example.githubshowcaseapp.mappers.IssuesDataState
import com.example.githubshowcaseapp.mappers.RepositoryDataState
import com.example.network_module.network.NetworkCallStatus
import com.example.network_module.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SharedViewModel(
    private val dataRepository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _networkDataState =
        MutableStateFlow<RepositoryDataState>(RepositoryDataState.ResetState)
    var networkDataState = _networkDataState.asStateFlow()

    private val _issuesDataState = MutableStateFlow<IssuesDataState>(IssuesDataState.ResetState)
    var issuesDataState = _issuesDataState.asStateFlow()

    private val _contributorsDataState =
        MutableStateFlow<ContributorsDataState>(ContributorsDataState.ResetState)
    var contributors = _contributorsDataState.asStateFlow()


    fun fetchRepositoryDetails(searchName: String) {
        viewModelScope.launch {
            dataRepository.fetchRepositoryDetails(searchName = searchName).collect {
                when (it) {
                    is NetworkCallStatus.Loading -> {
                        _networkDataState.value = RepositoryDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _networkDataState.value =
                            RepositoryDataState.SuccessState(itemList = it.data?.items)
                    }
                    is NetworkCallStatus.Error -> {
                        _networkDataState.value =
                            RepositoryDataState.ErrorState(error = it.msg.toString())
                    }
                    else -> Unit
                }
            }
        }
    }

    fun fetchIssuesAndContributorDetails(fullName: String) {
        viewModelScope.launch {
            dataRepository.fetchRepositoriesIssues(fullName = fullName).combine(
                dataRepository.fetchRepositoriesContributors(fullName = fullName)
            ) { issues, contributors ->
                Pair(issues, contributors)
            }.collect {
                when (it.first) {
                    is NetworkCallStatus.Loading -> {
                        _issuesDataState.value = IssuesDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _issuesDataState.value =
                            IssuesDataState.SuccessState(issues = it.first.data)
                    }
                    is NetworkCallStatus.Error -> {
                        _issuesDataState.value =
                            IssuesDataState.ErrorState(error = it.first.msg.toString())
                    }
                    else -> Unit
                }
                when (it.second) {
                    is NetworkCallStatus.Loading -> {
                        _contributorsDataState.value = ContributorsDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _contributorsDataState.value =
                            ContributorsDataState.SuccessState(contributions = it.second.data)
                    }
                    is NetworkCallStatus.Error -> {
                        _contributorsDataState.value =
                            ContributorsDataState.ErrorState(error = it.first.msg.toString())
                    }
                    else -> Unit
                }
            }
        }
    }
}