package com.example.simpleapicallwithdi.domain

import com.example.simpleapicallwithdi.data.model.MaterialResponse
import com.example.simpleapicallwithdi.data.model.MaterialBody

interface PostSearchRepo{
    suspend fun postSearch(post:MaterialBody): MaterialResponse
}
