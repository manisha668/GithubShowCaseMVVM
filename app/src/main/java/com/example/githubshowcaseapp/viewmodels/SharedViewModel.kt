package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubshowcaseapp.mappers.NetworkDataState
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
     private val _networkDataState = MutableStateFlow<NetworkDataState>(NetworkDataState.ResetState)
      var networkDataState = _networkDataState.asStateFlow()
      fun fetchRepositoryDetails(searchName: String){
        viewModelScope.launch {
            dataRepository.fetchRepositoryDetails(searchName).collect {
                when(it) {
                    is NetworkCallStatus.Loading -> {
                        _networkDataState.value = NetworkDataState.LoadingState
                    }
                    is NetworkCallStatus.Success -> {
                        _networkDataState.value = NetworkDataState.SuccessState(itemList = it.data?.items)
                    }
                    is NetworkCallStatus.Error -> {
                        _networkDataState.value = NetworkDataState.ErrorState(error = it.msg.toString())
                    }
                }
            }
        }
    }
}