package com.example.cryptotracker.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.cryptotracker.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CryptoNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val CRYPTO_RATE_TRACKING_STARTED_ID = 1
        const val CRYPTO_RATE_CHANGED_ID = 2
        const val CRYPTO_RATE_TRACKING_STARTED_CHANNEL_ID = "crypto_rate_started_channel"
        const val CRYPTO_RATE_CHANGED_CHANNEL_ID = "crypto_rate_changed_channel"
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showBackgroundWorkStarted(message: String) {
        val backgroundWorkNotification = NotificationCompat.Builder(context, CRYPTO_RATE_TRACKING_STARTED_CHANNEL_ID)
            .setContentTitle("Crypto tracking started!")
            .setSmallIcon(R.drawable.ic_money)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CRYPTO_RATE_TRACKING_STARTED_CHANNEL_ID,
                    "Crypto Rate Tracking Start",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
        notificationManager.notify(CRYPTO_RATE_TRACKING_STARTED_ID, backgroundWorkNotification)
    }

    fun showCryptoRateChanged(notificationId: Int, message: String) {
        val cryptoRateChangedNotification = NotificationCompat.Builder(context, CRYPTO_RATE_CHANGED_CHANNEL_ID)
            .setContentTitle("Wohoo! Crypto rate changed!")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_money)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CRYPTO_RATE_CHANGED_CHANNEL_ID,
                    "Crypto Rate Changed",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
        notificationManager.notify(notificationId, cryptoRateChangedNotification)
    }


}