package com.tuongvi.movieexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuongvi.movieexplorer.data.local.datastore.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private  val userPreferencesRepository: UserPreferencesRepository
): ViewModel(){
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        observeTheme()
    }

    fun observeTheme(){
        viewModelScope.launch {
            userPreferencesRepository.isDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
    }

    fun toggleDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setDarkMode(isDark)
        }
    }
}