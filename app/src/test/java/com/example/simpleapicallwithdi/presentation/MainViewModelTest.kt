package com.example.simpleapicallwithdi.presentation

import com.example.simpleapicallwithdi.data.ApiStatus
import com.example.simpleapicallwithdi.data.model.Quote
import com.example.simpleapicallwithdi.domain.GetQuoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    @MockK
    lateinit var getQuoteUseCase: GetQuoteUseCase

    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")
    private lateinit var viewModel: MainViewModel
    private lateinit var mockQuoteData: Quote

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = UnconfinedTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
//        Dispatchers.setMain(mainThreadSurrogate)
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        mockQuoteData = MockQuoteData.data

        coEvery { getQuoteUseCase() } returns flowOf(
            ApiStatus.Success(
                mockQuoteData
            )
        )

        viewModel = MainViewModel(getQuoteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Invokes getQuoteUseCase, receives loading event, state shows loading with no error`() {
        runTest/*(UnconfinedTestDispatcher())*/ {
//            val dispatcher = StandardTestDispatcher(testScheduler)
            launch(Dispatchers.Main) {
                coEvery { getQuoteUseCase() } returns flowOf(
                    ApiStatus.Loading(null)
                )

                viewModel = MainViewModel(getQuoteUseCase)
                viewModel.getQuotes()
                val state = viewModel.state.first()
                assert(state.isLoading.not() && state.apiError.isEmpty())
            }
        }
    }

    @Test
    fun `Invokes getHomeDataUseCase, receives loading event, state shows loading with no error`() {
        runBlocking {
            launch(Dispatchers.Main) {
                coEvery { getQuoteUseCase() } returns flowOf(
                    ApiStatus.Loading(null)
                )

                viewModel = MainViewModel(getQuoteUseCase)
                viewModel.getQuotes()
                val state = viewModel.state.first()
                assert(state.isLoading.not() && state.apiError.isEmpty())
            }
        }
    }

    @Test
    fun `Invokes getHomeDataUseCase, receives error, state shows error`() {
        runBlocking {
            launch(Dispatchers.Main) {
                val error = "This is a error msg"
                coEvery { getQuoteUseCase() } returns flowOf(
                    ApiStatus.Error(
                        error, null
                    )
                )
                val viewModel = MainViewModel(getQuoteUseCase)
                viewModel.getQuotes()
                val state = viewModel.state.drop(1).first()
                assert(state.apiError == error)
            }
        }
    }

    @Test
    fun `invokes getHomeDataUseCase, receives data, state shows same data`() {
        runBlocking {
            launch(Dispatchers.Main) {
                coEvery { getQuoteUseCase() } returns flowOf(
                    ApiStatus.Success(mockQuoteData)
                )
            }

            val viewModel = MainViewModel(getQuoteUseCase)
            viewModel.getQuotes()
            val state = viewModel.state.drop(1).first()
            assert(state.data == mockQuoteData)
        }
    }

    @Test
    fun dataShouldBeHelloWorld() = runTest {
        val viewModel = MainViewModel(getQuoteUseCase)
        val data = viewModel.fetchData()
        assertEquals("Hello world", data)
    }

    @Test
    fun dataShouldBeHelloWorld2() = runBlocking {
        val viewModel = MainViewModel(getQuoteUseCase)
        val data = viewModel.fetchData()
        assertEquals("Hello world", data)
    }
}