package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubshowcaseapp.utilities.NetworkResolver
import com.example.network_module.network.NetworkService
import com.example.network_module.repository.DataRepository
import kotlinx.coroutines.Dispatchers

class CustomViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(
            dataRepository = DataRepository(
                service = NetworkService(NetworkResolver.retrofitInstance),
                coroutineDisPatcher = Dispatchers.IO
            )
        ) as T
    }
}