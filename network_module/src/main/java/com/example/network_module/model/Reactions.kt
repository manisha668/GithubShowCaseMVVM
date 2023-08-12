package com.example.network_module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reactions(
    @SerializedName("+1")
    val plus_one: Int?,
    @SerializedName("-1")
    val minus_one: Int?,
    @SerializedName("confused")
    val confused: Int?,
    @SerializedName("eyes")
    val eyes: Int?,
    @SerializedName("heart")
    val heart: Int?,
    @SerializedName("hooray")
    val hooray: Int?,
    @SerializedName("laugh")
    val laugh: Int?,
    @SerializedName("rocket")
    val rocket: Int?,
    @SerializedName("total_count")
    val total_count: Int?,
    @SerializedName("url")
    val url: String?
) : Parcelable