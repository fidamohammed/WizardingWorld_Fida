package com.example.wizardingworld_fida.util

sealed class UiState{
    object Loading: UiState()
    data class Success<T>(val characterResponse: T): UiState()
    data class Error(val error: String): UiState()
}
