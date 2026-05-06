package com.example.arogyasahayalocal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.arogyasahayalocal.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val sessionManager = SessionManager(application)
    
    private val _isLoggedIn = MutableStateFlow(sessionManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _language = MutableStateFlow(sessionManager.getLanguage())
    val language: StateFlow<String> = _language

    fun setLoggedIn(loggedIn: Boolean, userId: Int = -1) {
        if (loggedIn) {
            sessionManager.saveAuthToken(userId)
        } else {
            sessionManager.logout()
        }
        _isLoggedIn.value = loggedIn
    }

    fun setLanguage(lang: String) {
        sessionManager.setLanguage(lang)
        _language.value = lang
    }
}
