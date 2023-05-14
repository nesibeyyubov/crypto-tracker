package com.example.cryptotracker.utils

object Constants {

    // Api Url
    const val API_BASE_URL = "https://api.coingecko.com/api/v3/simple/"

    // Coin types
    const val BITCOIN = "bitcoin"
    const val RIPPLE = "ripple"
    const val ETHEREUM = "ethereum"

    // Extra query parameters
    const val USD = "usd"
    const val USD_MARKET_CAP = "usd_market_cap"
    const val USD_24H_VOL = "usd_24h_vol"
    const val USD_24H_CHANGE = "usd_24h_change"
    const val LAST_UPDATED_AT = "last_updated_at"

    // Preferences keys
    const val KEY_COIN = "coin"
    const val KEY_MIN = "min"
    const val KEY_MAX = "max"

    // Work Manager stuff
    const val CRYPTO_WORKER_NAME = "crypto_worker"

}