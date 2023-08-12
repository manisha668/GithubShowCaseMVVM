package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubshowcaseapp.mappers.IssuesDataState
import com.example.githubshowcaseapp.mappers.RepositoryDataState
import com.example.network_module.network.NetworkCallStatus
import com.example.network_module.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SharedViewModel(
    private val dataRepository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

     private val _networkDataState = MutableStateFlow<RepositoryDataState>(RepositoryDataState.ResetState)
      var networkDataState = _networkDataState.asStateFlow()

    private val _issuesDataState = MutableStateFlow<IssuesDataState>(IssuesDataState.ResetState)
    var issuesDataState = _issuesDataState.asStateFlow()


      fun fetchRepositoryDetails(searchName: String){
        viewModelScope.launch {
            dataRepository.fetchRepositoryDetails(searchName = searchName).collect {
                when(it) {
                    is NetworkCallStatus.Loading -> {
                        _networkDataState.value = RepositoryDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _networkDataState.value = RepositoryDataState.SuccessState(itemList = it.data?.items)
                    }
                    is NetworkCallStatus.Error -> {
                        _networkDataState.value = RepositoryDataState.ErrorState(error = it.msg.toString())
                    }
                    else -> Unit
                }
            }
        }
    }

    fun fetchRepositoriesIssues(fullName: String){
        viewModelScope.launch {
            dataRepository.fetchRepositoriesIssues(fullName = fullName).collect {
                when(it) {
                    is NetworkCallStatus.Loading -> {
                        _issuesDataState.value = IssuesDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _issuesDataState.value = IssuesDataState.SuccessState(issues = it.data)
                    }
                    is NetworkCallStatus.Error -> {
                        _issuesDataState.value = IssuesDataState.ErrorState(error = it.msg.toString())
                    }
                    else -> Unit
                }
            }
        }
    }
}