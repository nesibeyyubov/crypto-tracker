package com.example.cryptotracker.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.models.toUiModel
import com.example.cryptotracker.data.network.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CryptoRepository
) : BaseViewModel<HomeState>(HomeState()) {


    fun getCoins() {
        repository.getCoins()
            .onStart { setState { it.copy(loading = true) } }
            .onEach { response ->
                setState { it.copy(loading = false, coins = response.coins) }
            }
            .catch {
                Log.d("mytag", "error: $it")
            }
            .launchIn(viewModelScope)
    }

}