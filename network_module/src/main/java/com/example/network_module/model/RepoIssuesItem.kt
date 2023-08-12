package com.example.network_module.model

import com.google.gson.annotations.SerializedName

data class RepoIssuesItem(
    @SerializedName("active_lock_reason")
    val active_lock_reason: String?,
    @SerializedName("assignee")
    val assignee: Assignee?,
    @SerializedName("assignees")
    val assignees: List<Assignee?>?,
    @SerializedName("author_association")
    val author_association: String?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("closed_at")
    val closed_at: String?,
    @SerializedName("comments")
    val comments: Int?,
    @SerializedName("comments_url")
    val comments_url: String?,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("draft")
    val draft: Boolean?,
    @SerializedName("events_url")
    val events_url: String?,
    @SerializedName("html_url")
    val html_url: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("labels")
    val labels: List<Label?>?,
    @SerializedName("labels_url")
    val labels_url: String?,
    @SerializedName("locked")
    val locked: Boolean?,
    @SerializedName("milestone")
    val milestone: String?,
    @SerializedName("node_id")
    val node_id: String?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("performed_via_github_app")
    val performed_via_github_app: String?,
    @SerializedName("pull_request")
    val pull_request: PullRequest?,
    @SerializedName("reactions")
    val reactions: Reactions?,
    @SerializedName("repository_url")
    val repository_url: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("state_reason")
    val state_reason: String?,
    @SerializedName("timeline_url")
    val timeline_url: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updated_at")
    val updated_at: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("user")
    val user: User?
)