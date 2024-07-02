package com.example.simpleapicallwithdi.presentation.di

import com.example.simpleapicallwithdi.data.GetQuoteApi
import com.example.simpleapicallwithdi.data.GetQuoteRepoImpl
import com.example.simpleapicallwithdi.data.PostSearchApi
import com.example.simpleapicallwithdi.data.PostSearchRepoImpl
import com.example.simpleapicallwithdi.domain.GetQuoteRepo
import com.example.simpleapicallwithdi.domain.GetQuoteUseCase
import com.example.simpleapicallwithdi.domain.PostSearchRepo
import com.example.simpleapicallwithdi.domain.PostSearchUseCase
import com.google.gson.GsonBuilder
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

    @Provides
    @Singleton
    fun provideSearchAPI(): PostSearchApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("http://ams.2allons.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PostSearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(): PostSearchRepo {
        return PostSearchRepoImpl(provideSearchAPI())
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(): PostSearchUseCase {
        return PostSearchUseCase(provideSearchRepo())
    }
}