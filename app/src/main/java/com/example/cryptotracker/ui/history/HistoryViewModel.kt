package com.example.cryptotracker.ui.history

import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.network.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: CryptoRepository
) : BaseViewModel<HistoryState>(HistoryState()) {


    fun getCoins(type: Int) {
        repository.getCoinsFromDb(type)
            .onStart { setState { it.copy(loading = true) } }
            .onEach { response ->
                setState { it.copy(loading = false, coins = response.coins.reversed()) }
            }
            .catch {

            }
            .launchIn(viewModelScope)
    }


}