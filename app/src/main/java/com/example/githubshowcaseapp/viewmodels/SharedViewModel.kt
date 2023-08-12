package com.example.githubshowcaseapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network_module.network.NetworkCallStatus
import com.example.network_module.repository.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SharedViewModel(
    private val dataRepository: DataRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

     fun fetchRepositoryDetails(searchName: String) {
        viewModelScope.launch {
            dataRepository.fetchRepositoryDetails(searchName).collect {
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