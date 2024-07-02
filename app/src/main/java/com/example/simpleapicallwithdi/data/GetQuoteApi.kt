package com.example.simpleapicallwithdi.data

import com.example.simpleapicallwithdi.data.model.Quote
import retrofit2.http.GET

interface GetQuoteApi {
    @GET("quotes?page=1")
    suspend fun getQuote(): Quote
}