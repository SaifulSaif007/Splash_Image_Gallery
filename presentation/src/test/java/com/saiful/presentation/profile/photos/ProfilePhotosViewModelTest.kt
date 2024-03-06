package com.saiful.presentation.profile.photos

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetProfilePhotosUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset

@OptIn(ExperimentalCoroutinesApi::class)
class ProfilePhotosViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val userCase: GetProfilePhotosUseCase = mock()
    private lateinit var viewModel: ProfilePhotosViewModel
    private val userName: String = "saiful"
    private lateinit var flowPagingData: Flow<PagingData<PhotoItem>>

    override fun setup() {
        flowPagingData = flowOf(
            PagingData.from(
                listOf(
                    PhotoItem(
                        photoId = "1",
                        profileImage = "profile-image",
                        profileName = "profile-name",
                        sponsored = true,
                        mainImage = "main-image",
                        mainImageBlurHash = "",
                        mainImageHeight = 4,
                        mainImageWidth = 3,
                        profileUserName = "profile-user-name"
                    ),
                    PhotoItem(
                        photoId = "2",
                        profileImage = "profile-image",
                        profileName = "profile-name",
                        sponsored = false,
                        mainImage = "main-image",
                        mainImageBlurHash = "",
                        mainImageHeight = 4,
                        mainImageWidth = 3,
                        profileUserName = "profile-user-name"
                    ),
                )
            )
        )
    }

    override fun tearDown() {
        reset(userCase)
    }

    private fun initViewModel() {
        viewModel = ProfilePhotosViewModel(userCase)
    }


    @Test
    fun `load data gets flow paging data`() {
        runTest {
            whenever(userCase(userName)).thenReturn(flowPagingData)

            initViewModel()
            viewModel.setEvent(ProfilePhotosContract.Event.Initialize(userName))

            val result = flowOf(viewModel.photoState.first()).asSnapshot()
            verify(userCase, times(1)).invoke(userName)

            assert(result.isNotEmpty())
            assert(result.size == 2)
            assert(result[0].photoId == "1")
            assert(result[1].photoId == "2")
        }
    }

    @Test
    fun `load data gets empty flow paging data`() {
        runTest {
            whenever(userCase(userName)).thenReturn(
                flowOf(
                    PagingData.from(
                        data = emptyList(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Exception("ex")),
                            prepend = LoadState.NotLoading(endOfPaginationReached = true),
                            append = LoadState.NotLoading(endOfPaginationReached = true)
                        )
                    )
                )
            )

            initViewModel()
            viewModel.setEvent(ProfilePhotosContract.Event.Initialize(userName))

            val result = flowOf(viewModel.photoState.first()).asSnapshot()
            verify(userCase, times(1)).invoke(userName)

            assert(result.isEmpty())
        }
    }

    @Test
    fun `verify select photo event`() {
        runTest {
            whenever(userCase(userName)).thenReturn(flowPagingData)
            initViewModel()
            viewModel.setEvent(ProfilePhotosContract.Event.Initialize(userName))

            viewModel.setEvent(ProfilePhotosContract.Event.SelectPhoto("1"))

            assert(viewModel.effect.first() is ProfilePhotosContract.Effect.Navigation.ToPhotoDetails)
        }
    }
}