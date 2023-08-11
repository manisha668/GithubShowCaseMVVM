package com.example.network_module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class License(
    @SerializedName("key")
    val key: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("node_id")
    val node_id: String?,
    @SerializedName("spdx_id")
    val spdx_id: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable