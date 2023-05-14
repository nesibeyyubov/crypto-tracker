package com.example.cryptotracker.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : BaseViewModel<StartState>(StartState()) {

    fun setSplashScreenSeen() {
        viewModelScope.launch {
            preferencesDataStore.setSplashScreenSeen()
        }

    }


}