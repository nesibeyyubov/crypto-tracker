package com.example.cryptotracker.utils

object Constants {
    const val API_BASE_URL = "https://api.coingecko.com/api/v3/simple/"

//        "ids=bitcoin%2Cripple&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true"

    const val BITCOIN = "bitcoin"
    const val RIPPLE = "ripple"
    const val ETHEREUM = "ethereum"

    const val USD = "usd"
    const val USD_MARKET_CAP = "usd_market_cap"
    const val USD_24H_VOL = "usd_24h_vol"
    const val USD_24H_CHANGE = "usd_24h_change"
    const val LAST_UPDATED_AT = "last_updated_at"

    const val KEY_COIN = "coin"

    const val KEY_MIN = "min"
    const val KEY_MAX = "max"

    const val CRYPTO_WORKER_NAME = "crypto_worker"

}