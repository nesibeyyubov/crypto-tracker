package com.example.cryptotracker.data.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.cryptotracker.R
import com.example.cryptotracker.utils.Constants.BITCOIN
import com.example.cryptotracker.utils.Constants.ETHEREUM
import com.example.cryptotracker.utils.Constants.LAST_UPDATED_AT
import com.example.cryptotracker.utils.Constants.RIPPLE
import com.example.cryptotracker.utils.Constants.USD
import com.example.cryptotracker.utils.Constants.USD_24H_CHANGE
import com.example.cryptotracker.utils.Constants.USD_24H_VOL
import com.example.cryptotracker.utils.Constants.USD_MARKET_CAP
import com.example.cryptotracker.utils.orEmptyString
import java.io.Serializable
import java.util.jar.Attributes.Name

typealias CoinsResponse = Map<String, Map<String, String>>

fun CoinsResponse.toUiModel(): CoinsUi {
    val coins = mutableListOf<Coin>()
    this.mapKeys {
        val coinType = when (it.key) {
            BITCOIN -> CoinType.Bitcoin
            RIPPLE -> CoinType.Ripple
            ETHEREUM -> CoinType.Ethereum
            else -> CoinType.Bitcoin
        }
        coins.add(
            Coin(
                name = it.key.lowercase().capitalize(),
                type = coinType,
                usd = it.value[USD]?.toFloat() ?: 0f,
                usdMarketCap = it.value[USD_MARKET_CAP].orEmptyString(),
                usd24hChange = it.value[USD_24H_CHANGE]?.toFloat() ?: 0f,
                usd24hVol = it.value[USD_24H_VOL].orEmptyString(),
                lastUpdatedAt = it.value[LAST_UPDATED_AT].orEmptyString()
            )
        )
    }

    return CoinsUi(coins)
}


data class CoinsUi(
    val coins: List<Coin>
)

enum class CoinType(@DrawableRes val drawable: Int, val shortName: String) {
    Bitcoin(R.drawable.ic_bitcoin, "BTC"),
    Ethereum(R.drawable.ic_ethereum, "ETH"),
    Ripple(R.drawable.ic_ripple, "XRP")
}

data class Coin(
    val name: String,
    val type: CoinType,
    val usd: Float,
    val usdMarketCap: String,
    val usd24hVol: String,
    val usd24hChange: Float,
    val lastUpdatedAt: String,
) : Serializable

fun Coin.percentageColor(): Int {
    return if (usd24hChange > 0) R.color.green else R.color.red
}





