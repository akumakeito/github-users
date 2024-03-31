package ru.akumakeito.githubusers.data.dto

import androidx.room.ColumnInfo

data class UserResponse(
    val login: String,
    val id: Int,
    @ColumnInfo(name = "node_id")
    val nodeId: String?,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "gravatar_id")
    val gravatarId: String?,
    val url : String?,
    @ColumnInfo(name = "html_url")
    val htmlUrl : String?,
    @ColumnInfo(name = "followers_url")
    val followersUrl : String?,
    @ColumnInfo(name = "following_url")
    val followingUrl : String?,
    @ColumnInfo(name = "gists_url")
    val gistsUrl : String?,
    @ColumnInfo(name = "starred_url")
    val starredUrl : String?,
    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl : String?,
    @ColumnInfo(name = "organizations_url")
    val organizationsUrl : String?,
    @ColumnInfo(name = "repos_url")
    val reposUrl : String?,
    @ColumnInfo(name = "events_url")
    val eventsUrl : String?,
    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl : String?,
    val type : String?,
    @ColumnInfo(name = "site_admin")
    val siteAdmin : Boolean?,
    val name : String?,
    val company : String?,
    val blog : String?,
    val location : String?,
    val email : String?,
    val hireable : String?,
    val bio : String?,
    @ColumnInfo(name = "twitter_username")
    val twitterUsername : String?,
    @ColumnInfo(name = "public_repos")
    val publicRepos : Int,
    @ColumnInfo(name = "public_gists")
    val publicGists : Int,
    val followers : Int,
    val following : Int,
    @ColumnInfo(name = "created_at")
    val createdAt : String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt : String?
)