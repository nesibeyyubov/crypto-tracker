package com.example.cryptotracker.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptotracker.data.models.CoinEntity

@Dao
interface CryptoDao {

    @Insert
    suspend fun insertCoin(coinEntity: CoinEntity)


    @Query("SELECT * FROM coins_table WHERE type = :type")
    suspend fun getCoins(type: Int): List<CoinEntity>

}