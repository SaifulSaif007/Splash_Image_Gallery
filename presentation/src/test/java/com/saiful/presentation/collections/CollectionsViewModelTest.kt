package com.saiful.presentation.collections

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetCollectionUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionsViewModelTest : BaseViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val collectionUseCase: GetCollectionUseCase = mock()
    private lateinit var viewModel: CollectionsViewModel

    private lateinit var flowPagingData: Flow<PagingData<CollectionItem>>

    override fun setup() {
        flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    CollectionItem(
                        mainImage = "",
                        profileImage = "",
                        profileName = "NEOM",
                        title = "City",
                        totalPhoto = 10
                    ),
                    CollectionItem(
                        mainImage = "",
                        profileImage = "",
                        profileName = "ABC",
                        title = "Adventure",
                        totalPhoto = 101
                    )
                )
            )
        )
    }

    override fun tearDown() {
        reset(collectionUseCase)
    }

    private fun initViewModel() {
        viewModel = CollectionsViewModel(collectionUseCase)
    }

    @Test
    fun `load data gets flow pager data`() {
        runTest {
            whenever(collectionUseCase(Unit)).thenReturn(flowPagingData)

            initViewModel()

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            verify(collectionUseCase, only()).invoke(Unit)
            assert(result.isNotEmpty())
            assert(result.size == 2)
        }
    }

    @Test
    fun `load data gets exception flow pager data`() {
        runTest {
            whenever(collectionUseCase(Unit)).thenReturn(
                flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Exception("ex")),
                            prepend = LoadState.NotLoading(true),
                            append = LoadState.NotLoading(true)
                        )
                    )
                )
            )

            initViewModel()

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            verify(collectionUseCase, only()).invoke(Unit)
            assert(result.isEmpty())
        }
    }
}