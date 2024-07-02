package com.example.simpleapicallwithdi.data

import com.example.simpleapicallwithdi.data.model.MaterialBody
import com.example.simpleapicallwithdi.data.model.MaterialResponse
import com.example.simpleapicallwithdi.domain.PostSearchRepo

class PostSearchRepoImpl(private val postSearchApi: PostSearchApi) : PostSearchRepo {
    override suspend fun postSearch(post: MaterialBody): MaterialResponse {
        return postSearchApi.postSearch(post)
    }
}