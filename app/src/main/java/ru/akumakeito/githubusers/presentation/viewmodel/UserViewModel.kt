package ru.akumakeito.githubusers.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
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
        viewModelScope.launch {
            Log.d("room" , "userList = ${userList.last()}")
        }
        _state.update {
            it.copy(
                loading = true
            )
        }
        _state.update {
            StateModel()
        }

    }


    fun getUserByUsername(username: String) = viewModelScope.launch {
        try {
            Log.d("FragmentUserDetails", "before update username = ${_currentUser.value}")


            _currentUser.update {
                repository.getUserByUsername(username)
            }

            Log.d("FragmentUserDetails", "after update username = ${_currentUser.value}")
        } catch (e: Exception) {

        }

    }
}