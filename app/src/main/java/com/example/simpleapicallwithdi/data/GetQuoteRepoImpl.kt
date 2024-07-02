package com.example.simpleapicallwithdi.data

import com.example.simpleapicallwithdi.data.model.Quote
import com.example.simpleapicallwithdi.domain.GetQuoteRepo

class GetQuoteRepoImpl(private val getQuoteApi: GetQuoteApi):GetQuoteRepo {
    override suspend fun getQuotes(): Quote {
        return getQuoteApi.getQuote()
    }
}