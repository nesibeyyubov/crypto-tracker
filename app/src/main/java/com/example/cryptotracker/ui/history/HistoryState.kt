package com.example.cryptotracker.ui.history

import com.example.cryptotracker.base.State
import com.example.cryptotracker.data.models.Coin


data class HistoryState(
    val loading: Boolean = false,
    val coins: List<Coin>? = null,
    val error: String? = null
) : State