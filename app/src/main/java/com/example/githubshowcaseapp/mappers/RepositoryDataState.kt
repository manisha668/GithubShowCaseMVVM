package com.example.githubshowcaseapp.mappers

import com.example.network_module.model.Item

sealed class RepositoryDataState{
    object ResetState : RepositoryDataState()
    object LoadingState : RepositoryDataState()
    data class SuccessState(var itemList : List<Item>?) : RepositoryDataState()
    data class ErrorState(var error : String) : RepositoryDataState()
}
