package com.example.cryptotracker.ui.min_max

import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import com.example.cryptotracker.data.models.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MinMaxViewmodel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : BaseViewModel<MinMaxState>(MinMaxState()) {


    fun saveMinMax(coin: Coin, min: Float, max: Float) {
        viewModelScope.launch {
            preferencesDataStore.setMinMax(coin, min, max)
        }
    }

    fun getMinMax(coin: Coin) {
        preferencesDataStore.getMinMaxValues(coin)
            .onStart { setState { it.copy(loading = true) } }
            .onEach { rate -> setState { it.copy(loading = false, minMax = rate) } }
            .catch { }
            .launchIn(viewModelScope)
    }

}