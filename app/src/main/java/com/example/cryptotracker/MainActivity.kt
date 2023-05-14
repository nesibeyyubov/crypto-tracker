package com.example.cryptotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.cryptotracker.data.background_work.CryptoWorker
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.example.cryptotracker.utils.Constants.CRYPTO_WORKER_NAME
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startTrackingCryptoRates()
    }

    private fun startTrackingCryptoRates() {
        val currentWorkerInfo = workManager.getWorkInfosForUniqueWork(CRYPTO_WORKER_NAME)
        if (currentWorkerInfo.isDone || currentWorkerInfo.isCancelled) {
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


    }


}