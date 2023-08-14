package com.example.githubshowcaseapp.mappers

import com.example.githubshowcaseapp.constants.AppConstants.NO_DATA_AVAILABLE
import com.example.network_module.model.ContributorsDataItem

sealed class ContributorsDataState {
    object ResetState : ContributorsDataState()
    object LoadingState : ContributorsDataState()
    data class SuccessState(var contributions: List<ContributorsDataItem>?) :
        ContributorsDataState()

    data class ErrorState(var error: String) : ContributorsDataState()

    companion object {
        /**
         * processing the contributors data to send the final string to ui level
         */
        fun processContributorsData(list: List<ContributorsDataItem>?): Triple<String, String, String> {
            return if (list.isNullOrEmpty().not().and(list?.size!! >= 3))
                Triple(
                    list[0].login ?: NO_DATA_AVAILABLE,
                    list[1].login ?: NO_DATA_AVAILABLE,
                    list[2].login ?: NO_DATA_AVAILABLE
                )
            else Triple(NO_DATA_AVAILABLE, NO_DATA_AVAILABLE, NO_DATA_AVAILABLE)
        }
    }
}
