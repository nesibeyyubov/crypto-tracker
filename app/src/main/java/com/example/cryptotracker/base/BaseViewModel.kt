package com.example.cryptotracker.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<STATE : State>(initialState: STATE) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun setState(update: (old: STATE) -> STATE) {
        _state.update(update)
    }

    fun currentState() = state.value


}