package com.example.githubshowcaseapp.mappers

import com.example.network_module.model.RepoIssuesItem

sealed class IssuesDataState {
    object ResetState : IssuesDataState()
    object LoadingState : IssuesDataState()
    data class SuccessState(var issues: List<RepoIssuesItem>?) : IssuesDataState()
    data class ErrorState(var error: String) : IssuesDataState()
}
