package com.example.githubshowcaseapp.mappers

import com.example.githubshowcaseapp.constants.AppConstants.NO_DATA_AVAILABLE
import com.example.network_module.model.RepoIssuesItem

sealed class IssuesDataState {
    object ResetState : IssuesDataState()
    object LoadingState : IssuesDataState()
    data class SuccessState(var issues: List<RepoIssuesItem>?) : IssuesDataState()
    data class ErrorState(var error: String) : IssuesDataState()

    companion object {
        /**
         * processing the issues data to send the final string to ui level
         */
        fun processIssuesData(list: List<RepoIssuesItem>?): Triple<String, String, String> {
            return if (list.isNullOrEmpty().not().and(list?.size!! >= 3))
                Triple(
                    list[0].title ?: NO_DATA_AVAILABLE,
                    list[1].title ?: NO_DATA_AVAILABLE,
                    list[2].title ?: NO_DATA_AVAILABLE
                )
            else Triple(NO_DATA_AVAILABLE, NO_DATA_AVAILABLE, NO_DATA_AVAILABLE)
        }
    }
}
