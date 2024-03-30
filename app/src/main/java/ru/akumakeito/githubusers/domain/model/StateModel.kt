package ru.akumakeito.githubusers.domain.model

data class StateModel(
    val loading : Boolean = false,
    val refreshing : Boolean = false
)