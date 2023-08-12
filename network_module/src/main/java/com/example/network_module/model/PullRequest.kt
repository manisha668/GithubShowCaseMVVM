package com.example.network_module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PullRequest(
    @SerializedName("diff_url")
    val diff_url: String?,
    @SerializedName("html_url")
    val html_url: String?,
    @SerializedName("merged_at")
    val merged_at: String?,
    @SerializedName("patch_url")
    val patch_url: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable