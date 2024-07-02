package com.example.simpleapicallwithdi.presentation

import com.example.simpleapicallwithdi.data.model.Quote

data class QuoteState(
    var data: Quote? = null,
    var apiError: String = "",
    var isLoading: Boolean = false
)