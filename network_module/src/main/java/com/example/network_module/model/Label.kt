package com.example.network_module.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Label(
    @SerializedName("color")
    val color: String?,
    @SerializedName("default")
    val default: Boolean?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("node_id")
    val node_id: String?,
    @SerializedName("url")
    val url: String?
) : Parcelable