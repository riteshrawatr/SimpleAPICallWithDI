package com.example.simpleapicallwithdi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleapicallwithdi.data.ApiStatus
import com.example.simpleapicallwithdi.data.model.MaterialBody
import com.example.simpleapicallwithdi.domain.GetQuoteUseCase
import com.example.simpleapicallwithdi.domain.PostSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getQuoteUseCase: GetQuoteUseCase,
    private val postSearchUseCase: PostSearchUseCase
) : ViewModel() {
    private val _state1 = MutableStateFlow(QuoteState())
    val state1: StateFlow<QuoteState> get() = _state1

    private val _state = MutableStateFlow(ResponseState())
    val state: StateFlow<ResponseState> get() = _state

    fun getQuotes() {
        var abc = "This is a test string before coroutine"
        Log.v("VM_ABC", "$abc")
        getQuoteUseCase().flowOn(IO).onEach { result ->
            when (result) {
                is ApiStatus.Success -> {
                    abc = "API call in Success"
                    Log.v("VM_ABC", "$abc")
                    _state1.update { quoteState -> quoteState.copy(data = result.data) }
                }

                is ApiStatus.Error -> {
                    abc = "API call in Error"
                    Log.v("VM_ABC", "$abc")
                    _state1.update {
                        it.copy(
                            isLoading = false,
                            apiError = result.message ?: "An unexpected error occurred"
                        )
                    }
                }

                is ApiStatus.Loading -> {
                    abc = "API call in Loading"
                    Log.v("VM_ABC", "$abc")
                    _state1.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
        abc = "This is updated string, after coroutine code"
        Log.v("VM_ABC", "$abc")
    }

    suspend fun fetchData(): String {
        delay(1000L)
        return "Hello world"
    }

    fun postSearch(material: String, type: String) {
        var abc = "This is a test string before coroutine"
        postSearchUseCase(MaterialBody(material, type)).flowOn(IO).onEach { result ->
            when (result) {
                is ApiStatus.Success -> {
                    abc = "API call in Success"
                    Log.v("VM_ABC", "$abc")
                    _state.update { quoteState -> quoteState.copy(data = result.data) }
                }

                is ApiStatus.Error -> {
                    abc = "API call in Error"
                    Log.v("VM_ABC", "$abc")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            apiError = result.message ?: "An unexpected error occurred"
                        )
                    }
                }

                is ApiStatus.Loading -> {
                    abc = "API call in Loading"
                    Log.v("VM_ABC", "$abc")
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope)
        abc = "This is updated string, after coroutine code"
        Log.v("VM_ABC", "$abc")
    }
}