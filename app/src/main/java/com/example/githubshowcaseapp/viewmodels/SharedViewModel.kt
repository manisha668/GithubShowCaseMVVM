package com.example.githubshowcaseapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.network_module.repository.DataRepository

class SharedViewModel(private val dataRepository: DataRepository) : ViewModel() {
}