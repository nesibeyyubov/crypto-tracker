package com.example.cryptotracker.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptotracker.data.models.CoinEntity

@Database(entities = [CoinEntity::class], version = 1)
abstract class CoinsDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}