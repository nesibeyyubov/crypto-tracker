package com.example.cryptotracker.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.cryptotracker.data.local.room.CoinsDatabase
import com.example.cryptotracker.data.local.room.CryptoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CoinsDatabase {
        return Room
            .databaseBuilder(context, CoinsDatabase::class.java, "coins_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(countriesDatabase: CoinsDatabase): CryptoDao {
        return countriesDatabase.cryptoDao()
    }


}