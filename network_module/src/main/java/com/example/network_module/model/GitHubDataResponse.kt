package com.example.network_module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubDataResponse(
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean? = false,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("total_count")
    val total_count: Int? = null
) : Parcelable