package com.example.githubshowcaseapp.mappers

import com.example.network_module.model.Item

sealed class NetworkDataState{
    object ResetState : NetworkDataState()
    object LoadingState : NetworkDataState()
    data class SuccessState(var itemList : List<Item>?) : NetworkDataState()
    data class ErrorState(var error : String) : NetworkDataState()
}
