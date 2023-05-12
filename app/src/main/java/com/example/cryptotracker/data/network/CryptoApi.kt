package com.example.cryptotracker.data.network

import com.example.cryptotracker.data.models.CoinsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("price")
    suspend fun getCoins(
        @Query("ids") coins: String,
        @Query("vs_currencies") currencies: String,
        @Query("include_market_cap") includeMarketCap: Boolean = true,
        @Query("include_24hr_vol") include24hrVol: Boolean = true,
        @Query("include_24hr_change") include24hrChangel: Boolean = true,
        @Query("include_last_updated_at") includeLastUpdatedAt: Boolean = true,
        @Query("precision") precision: Int = 2
    ): CoinsResponse

}