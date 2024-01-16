package com.saiful.presentation.collections

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetCollectionUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
                        collectionId = "1",
                        mainImage = "",
                        mainImageBlurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
                        profileImage = "",
                        profileName = "NEOM",
                        title = "City",
                        totalPhoto = 10,
                        mainImageHeight = 4,
                        mainImageWidth = 3,
                        description = "desc"
                    ),
                    CollectionItem(
                        collectionId = "2",
                        mainImage = "",
                        mainImageBlurHash = "L:HLk^%0s:j[_Nfkj[j[%hWCWWWV",
                        profileImage = "",
                        profileName = "ABC",
                        title = "Adventure",
                        totalPhoto = 101,
                        mainImageHeight = 4,
                        mainImageWidth = 3,
                        description = "desc"
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