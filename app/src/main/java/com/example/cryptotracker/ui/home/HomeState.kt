package com.example.cryptotracker.ui.home

import com.example.cryptotracker.base.State
import com.example.cryptotracker.data.models.Coin


data class HomeState(
    val loading: Boolean = false,
    val coins: List<Coin>? = null
) : State