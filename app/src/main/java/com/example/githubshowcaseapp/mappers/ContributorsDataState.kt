package com.example.githubshowcaseapp.mappers

import com.example.network_module.model.ContributorsDataItem

sealed class ContributorsDataState {
    object ResetState : ContributorsDataState()
    object LoadingState : ContributorsDataState()
    data class SuccessState(var contributions: List<ContributorsDataItem>?) : ContributorsDataState()
    data class ErrorState(var error: String) : ContributorsDataState()

    companion object {
        fun processContributorsData(list: List<ContributorsDataItem>?): Triple<String, String, String> {
            return if (list.isNullOrEmpty().not().and(list?.size!! >= 3))
                Triple(
                    list[0].login ?: "",
                    list[1].login ?: "",
                    list[2].login ?: ""
                )
            else Triple("", "", "")
        }
    }
}
