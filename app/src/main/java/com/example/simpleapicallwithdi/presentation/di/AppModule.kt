package com.example.simpleapicallwithdi.presentation.di

import com.example.simpleapicallwithdi.data.GetQuoteApi
import com.example.simpleapicallwithdi.data.GetQuoteRepoImpl
import com.example.simpleapicallwithdi.domain.GetQuoteRepo
import com.example.simpleapicallwithdi.domain.GetQuoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideQuoteAPI(): GetQuoteApi {
        return Retrofit.Builder()
            .baseUrl("https://quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetQuoteApi::class.java)
    }

    @Provides
    @Singleton
    fun provideQuoteRepo(): GetQuoteRepo {
        return GetQuoteRepoImpl(provideQuoteAPI())
    }

    @Provides
    @Singleton
    fun provideQuoteUseCase(): GetQuoteUseCase {
        return GetQuoteUseCase(provideQuoteRepo())
    }
}