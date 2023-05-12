package com.example.cryptotracker.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

//    @Provides
//    @Singleton
//    fun provideRoomDatabase(@ApplicationContext context: Context): CountriesDatabase {
//        return Room
//            .databaseBuilder(context, CountriesDatabase::class.java, "countries")
//            .build()
//    }

//    @Provides
//    @Singleton
//    fun provideDao(countriesDatabase: CountriesDatabase): CountriesDao {
//        return countriesDatabase.countriesDao()
//    }

}