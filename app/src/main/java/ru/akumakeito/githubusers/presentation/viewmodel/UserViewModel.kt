package ru.akumakeito.githubusers.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.akumakeito.githubusers.domain.model.StateModel
import ru.akumakeito.githubusers.domain.model.User
import ru.akumakeito.githubusers.domain.repository.UserRepository
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val userList = repository.data
    private val _state = MutableStateFlow(StateModel())
    val state = _state.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        _state.update {
            it.copy(
                loading = true
            )
        }
        _state.update {
            StateModel()
        }

    }

    fun getUserList() = viewModelScope.launch {
        repository.getUserListSince(46)
    }

    fun getUserByUsername(username: String) = viewModelScope.launch {
        try {

            _currentUser.update {
                repository.getUserByUsername(username)
            }
        } catch (e: Exception) {

        }

    }
}