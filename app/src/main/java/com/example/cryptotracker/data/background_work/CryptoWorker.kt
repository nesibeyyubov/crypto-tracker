package com.example.cryptotracker.data.background_work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cryptotracker.data.network.CryptoApi
import com.example.cryptotracker.data.network.CryptoRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltWorker
class CryptoWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var repository: CryptoRepository

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override suspend fun doWork(): Result {
        coroutineScope.launch {
            repository.getCoins().collect {
                /**
                 * TODO
                 * 1)fetch preferences
                 * 2)compare each crypto rating with prefs
                 * 3)if anything in range, alert with notification
                 * 4)insert fetched coins in room db
                 */
            }
        }
        return Result.success()
    }


}