package com.example.simpleapicallwithdi.data

import com.example.simpleapicallwithdi.data.model.MaterialBody
import com.example.simpleapicallwithdi.data.model.MaterialResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostSearchApi {
    @Headers("Accept: application/json")
    @POST("material-location/search")
    suspend fun postSearch(@Body post: MaterialBody): MaterialResponse
}