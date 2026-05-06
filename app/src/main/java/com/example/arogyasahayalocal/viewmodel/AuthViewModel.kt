package com.example.arogyasahayalocal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyasahayalocal.data.local.entities.User
import com.example.arogyasahayalocal.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginStatus = MutableStateFlow<AuthResult?>(null)
    val loginStatus: StateFlow<AuthResult?> = _loginStatus

    fun login(username: String, passwordHash: String) {
        viewModelScope.launch {
            val user = repository.login(username)
            if (user != null && user.passwordHash == passwordHash) {
                _loginStatus.value = AuthResult.Success(user)
            } else {
                _loginStatus.value = AuthResult.Error("Invalid credentials")
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            repository.registerUser(user)
            _loginStatus.value = AuthResult.Success(user)
        }
    }
}

sealed class AuthResult {
    data class Success(val user: User) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
