package com.example.simpleapicallwithdi.domain

import com.example.simpleapicallwithdi.data.model.Quote

interface GetQuoteRepo{
    suspend fun getQuotes(): Quote
}
