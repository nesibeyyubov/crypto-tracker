package com.example.cryptotracker.ui.min_max

import com.example.cryptotracker.base.State
import com.example.cryptotracker.data.local.preferences.MinMaxRate


data class MinMaxState(
    val loading: Boolean = false,
    val minMax: MinMaxRate? = null
) : State