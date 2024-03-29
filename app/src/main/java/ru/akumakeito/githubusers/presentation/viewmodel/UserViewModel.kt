package ru.akumakeito.githubusers.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.akumakeito.githubusers.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun getUserList() = viewModelScope.launch {
        repository.getUserListSince(46)
    }

    fun getUserByUsername(username: String) = viewModelScope.launch {
        repository.getUserByUsername(username)
    }
}