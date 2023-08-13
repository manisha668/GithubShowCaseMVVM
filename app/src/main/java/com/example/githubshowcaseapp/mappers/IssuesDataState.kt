package com.example.githubshowcaseapp.mappers

import com.example.network_module.model.ContributorsDataItem
import com.example.network_module.model.RepoIssuesItem

sealed class IssuesDataState {
    object ResetState : IssuesDataState()
    object LoadingState : IssuesDataState()
    data class SuccessState(var issues: List<RepoIssuesItem>?) : IssuesDataState()
    data class ErrorState(var error: String) : IssuesDataState()

    companion object {
        fun processIssuesData(list: List<RepoIssuesItem>?): Triple<String, String, String> {
            return if (list.isNullOrEmpty().not().and(list?.size!! >= 3))
                Triple(
                    list[0].title ?: "",
                    list[1].title ?: "",
                    list[2].title ?: ""
                )
            else Triple("", "", "")
        }
    }
}
