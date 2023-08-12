package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network_module.network.NetworkCallStatus
import com.example.network_module.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SharedViewModel(
    private val dataRepository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun fetchRepositoryDetails(searchName: String) = flow {
        viewModelScope.launch {
            dataRepository.fetchRepositoryDetails(searchName).collect {
                when (it) {
                    is NetworkCallStatus.Loading -> {
                        emit(NetworkCallStatus.Loading(data = null))
                    }
                    is NetworkCallStatus.Success -> {
                        emit(NetworkCallStatus.Success(it.data))
                    }
                    is NetworkCallStatus.Error -> {
                        emit(NetworkCallStatus.Error(data = null, msg = it.msg))
                    }
                }
            }
        }
    }.flowOn(coroutineDispatcher)
}