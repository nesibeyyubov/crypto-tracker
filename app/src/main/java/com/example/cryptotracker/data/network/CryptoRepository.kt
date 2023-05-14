package com.example.cryptotracker.data.network

import com.example.cryptotracker.data.local.room.CryptoDao
import com.example.cryptotracker.data.models.CoinsResponse
import com.example.cryptotracker.data.models.CoinsUi
import com.example.cryptotracker.data.models.toCoinUi
import com.example.cryptotracker.data.models.toUiModel
import com.example.cryptotracker.utils.Constants.BITCOIN
import com.example.cryptotracker.utils.Constants.ETHEREUM
import com.example.cryptotracker.utils.Constants.RIPPLE
import com.example.cryptotracker.utils.Constants.USD
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val api: CryptoApi,
    private val dao: CryptoDao
) {

    fun getCoins(): Flow<CoinsUi> = flow {
        val coins = api.getCoins(coins = listOf(BITCOIN, RIPPLE, ETHEREUM).joinToString(","), currencies = USD)
        emit(coins.toUiModel())
    }

    fun getCoinsFromDb(type: Int): Flow<CoinsUi> = flow {
        val coins = dao.getCoins(type)
        emit(CoinsUi(coins.map { it.toCoinUi() }))
    }

}