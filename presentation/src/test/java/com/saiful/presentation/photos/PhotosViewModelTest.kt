package com.saiful.presentation.photos

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.domain.model.HomeItem
import com.saiful.domain.usecase.GetPhotosUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest : BaseViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photosUseCase: GetPhotosUseCase = mock()
    private lateinit var viewModel: PhotosViewModel

    private lateinit var flowPagingData: Flow<PagingData<HomeItem>>

    override fun setup() {
        flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    HomeItem(
                        profileImage = "profile-image",
                        profileName = "profile-name",
                        sponsored = true,
                        mainImage = "main-image",
                        mainImageBlurHash = "",
                        mainImageHeight = 4,
                        mainImageWidth = 3
                    ),
                    HomeItem(
                        profileImage = "profile-image",
                        profileName = "profile-name",
                        sponsored = false,
                        mainImage = "main-image",
                        mainImageBlurHash = "",
                        mainImageHeight = 4,
                        mainImageWidth = 3
                    ),
                )
            )
        )
    }

    override fun tearDown() {
        reset(photosUseCase)
    }

    private fun initViewModel() {
        viewModel = PhotosViewModel(photosUseCase)
    }

    @Test
    fun `load data gets flow pager data`() {
        runTest {
            whenever(photosUseCase(Unit)).thenReturn(flowPagingData)

            initViewModel()

            val result = flowOf(viewModel.photoState.first()).asSnapshot()

            verify(photosUseCase, only()).invoke(Unit)
            assert(result.isNotEmpty())
            assert(result.size == 2)
        }
    }

    @Test
    fun `load data gets exception flow pager data`() {
        runTest {
            whenever(photosUseCase(Unit)).thenReturn(
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

            val result = flowOf(viewModel.photoState.first()).asSnapshot()

            verify(photosUseCase, only()).invoke(Unit)
            assert(result.isEmpty())
        }
    }

}