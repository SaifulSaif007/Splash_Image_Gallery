package com.saiful.presentation.photos

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetPhotosUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotosViewModelTest : BaseViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photosUseCase: GetPhotosUseCase = mockk()
    private lateinit var viewModel: PhotosViewModel

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
        unmockkAll()
    }

    private fun initViewModel() {
        viewModel = PhotosViewModel(photosUseCase)
    }

    @Test
    fun `load data gets flow pager data`() {
        runTest {
            coEvery { photosUseCase(Unit) } returns flowPagingData

            initViewModel()

            val result = flowOf(viewModel.photoState.first()).asSnapshot()

            assert(result.isNotEmpty())
            assert(result.size == 2)
            coVerify(exactly = 1) { photosUseCase.invoke(Unit) }
        }
    }

    @Test
    fun `load data gets exception flow pager data`() {
        runTest {
            coEvery {
                photosUseCase(Unit)
            } returns
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

            initViewModel()

            val result = flowOf(viewModel.photoState.first()).asSnapshot()

            assert(result.isEmpty())
            coVerify(exactly = 1) { photosUseCase.invoke(Unit) }
        }
    }

    @Test
    fun `verify select photo event`() {
        runTest {
            `load data gets flow pager data`()

            viewModel.setEvent(PhotosContract.Event.SelectPhoto("1"))
            assert(viewModel.effect.first() is PhotosContract.Effect.Navigation.ToPhotoDetails)
        }
    }

    @Test
    fun `verify select profile event`() {
        runTest {
            `load data gets flow pager data`()

            viewModel.setEvent(PhotosContract.Event.SelectProfile("saiful", "Saiful"))
            assert(viewModel.effect.first() is PhotosContract.Effect.Navigation.ToProfile)
        }
    }

}