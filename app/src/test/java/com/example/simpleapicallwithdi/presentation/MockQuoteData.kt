package com.example.simpleapicallwithdi.presentation

import com.example.simpleapicallwithdi.data.model.Quote
import com.example.simpleapicallwithdi.data.model.Result

class MockQuoteData {
    companion object {
        val data = Quote(
            count = 20,
            totalCount = 2127,
            page = 1,
            totalPages = 107,
            lastItemIndex = 20,
            results = listOf(
                Result(
                    _id = "An5NAXPrbN",
                    author = "Thomas Edison",
                    content = "Hell, there are no rules here-- we're trying to accomplish something.",
                    tags = emptyList(),
                    authorSlug = "thomas-edison",
                    length = 69,
                    dateAdded = "2023-04-14",
                    dateModified = "2023-04-14"
                )
            )
        )
    }
}
