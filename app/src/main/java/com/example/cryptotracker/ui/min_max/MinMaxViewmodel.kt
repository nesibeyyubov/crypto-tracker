package com.example.cryptotracker.ui.min_max

import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.cryptotracker.base.BaseViewModel
import com.example.cryptotracker.data.background_work.CryptoWorker
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import com.example.cryptotracker.data.models.Coin
import com.example.cryptotracker.utils.Constants.CRYPTO_WORKER_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MinMaxViewmodel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val workManager: WorkManager
) : BaseViewModel<MinMaxState>(MinMaxState()) {


    private fun startTrackingCryptoRates() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val request = PeriodicWorkRequest.Builder(CryptoWorker::class.java, 15L, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        workManager.enqueueUniquePeriodicWork(
            CRYPTO_WORKER_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun saveMinMax(coin: Coin, min: Float, max: Float) {
        startTrackingCryptoRates()
        viewModelScope.launch {
            preferencesDataStore.setMinMax(coin, min, max)
        }
    }

    fun getMinMax(coin: Coin) {
        preferencesDataStore.getMinMaxValues(coin)
            .onStart { setState { it.copy(loading = true) } }
            .onEach { rate -> setState { it.copy(loading = false, minMax = rate) } }
            .catch { }
            .launchIn(viewModelScope)
    }

}