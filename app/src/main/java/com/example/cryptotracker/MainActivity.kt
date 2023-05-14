package com.example.cryptotracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.await
import com.example.cryptotracker.data.background_work.CryptoWorker
import com.example.cryptotracker.data.local.preferences.PreferencesDataStore
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.example.cryptotracker.utils.Constants.CRYPTO_WORKER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var preferencesDataStore: PreferencesDataStore

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val navController by lazy { Navigation.findNavController(this, R.id.fragment_container_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirstFragment()
        startTrackingCryptoRates()
    }

    private fun initFirstFragment() {
        coroutineScope.launch {
            val seen = preferencesDataStore.getSplashScreenSeen().first()
            if (seen) navController.navigate(R.id.homeFragment)
        }
    }

    private fun startTrackingCryptoRates() {
        coroutineScope.launch {
            val workInfo = workManager.getWorkInfosForUniqueWork(CRYPTO_WORKER_NAME).await()
            if (workInfo.isEmpty()) {
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


}