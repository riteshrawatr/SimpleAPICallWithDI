package com.example.simpleapicallwithdi.domain

import com.example.simpleapicallwithdi.data.ApiStatus
import com.example.simpleapicallwithdi.data.model.MaterialBody
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostSearchUseCase @Inject constructor(private val postSearchRepo: PostSearchRepo) {
    operator fun invoke(post:MaterialBody) = flow {
        try {
            emit(ApiStatus.Loading())
            val result = postSearchRepo.postSearch(post)
            emit(ApiStatus.Success(result))
        } catch (e: HttpException) {
            emit(ApiStatus.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ApiStatus.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(ApiStatus.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
