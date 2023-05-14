package com.example.cryptotracker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins_table")
data class CoinEntity(
    @PrimaryKey(true) val id: Int = 0,
    val type: Int,
    val shortName: String,
    val drawable: Int,
    val name: String,
    val usd: Float,
    val usd24hChange: Float,
    val lastUpdatedAt: String,
    val usdMarketCap: String,
    val usd24hVol: String,
)

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        type = this.type.ordinal,
        shortName = this.type.shortName,
        name = this.name,
        usd = this.usd,
        usdMarketCap = this.usdMarketCap,
        usd24hVol = this.usd24hVol,
        usd24hChange = this.usd24hChange,
        lastUpdatedAt = this.lastUpdatedAt,
        drawable = this.type.drawable
    )
}

fun CoinEntity.toCoinUi(): Coin {
    return Coin(
        name = this.name,
        usd = this.usd,
        usd24hChange = this.usd24hChange,
        usdMarketCap = this.usdMarketCap,
        usd24hVol = this.usd24hVol,
        type = CoinType.values()[this.type],
        lastUpdatedAt = this.lastUpdatedAt
    )
}