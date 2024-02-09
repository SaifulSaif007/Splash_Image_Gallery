package com.saiful.presentation.photodetails

import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.core.domain.Result
import com.saiful.domain.model.PhotoDetailsItem
import com.saiful.domain.usecase.GetPhotoDetailsUseCase
import com.saiful.presentation.utils.Constants
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDetailsViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photoDetailsUseCase: GetPhotoDetailsUseCase = mock()
    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var photoDetailsItem: PhotoDetailsItem
    private val photoId = "1"

    override fun setup() {
        photoDetailsItem = PhotoDetailsItem(
            profileImage = "https://i.imgur.com/00000000000000000000000000000000.jpg",
            profileName = "John Doe",
            mainImage = "https://i.imgur.com/00000000000000000000000000000001.jpg",
            thumbnailImage = "https://i.imgur.com/00000000000000000000000000000002.jpg",
            camera = "Canon EOS 5D Mark IV",
            focalLength = "50mm",
            aperture = "f/2.8",
            shutterSpeed = "1/100",
            iso = "100",
            dimensions = "1920x1080",
            views = "1000",
            downloads = "100",
            likes = "10",
            tags = listOf("nature", "landscape", "mountains"),
            userName = "johndoe"
        )

    }

    override fun tearDown() {
        reset(photoDetailsUseCase)
    }

    private fun initViewModel() {
        viewModel = PhotoDetailsViewModel(
            photoDetailsUseCase = photoDetailsUseCase,
            savedStateHandle = SavedStateHandle().apply {
                this[Constants.PHOTO_ID] = photoId
            }
        )
    }

    @Test
    fun `verify get photo load photo details`() {
        runTest {
            whenever(photoDetailsUseCase(photoId)).thenReturn(
                Result.Success(photoDetailsItem)
            )

            initViewModel()

            val result = viewModel.uiState.value
            assert(result is PhotoDetailsViewModel.UIState.Success)
            assert((result as PhotoDetailsViewModel.UIState.Success).photoDetails == photoDetailsItem)
        }
    }

    @Test
    fun `verify get photo load error`() {
        runTest {
            whenever(photoDetailsUseCase(photoId)).thenReturn(
                Result.Error(domainException)
            )
            initViewModel()
            val result = viewModel.uiState.value
            assert(result is PhotoDetailsViewModel.UIState.Error)
            assert((result as PhotoDetailsViewModel.UIState.Error).exception == domainException)
        }
    }

    @Test
    fun `verify navigate up event`() {
        runTest {
            initViewModel()
            viewModel.setEvent(PhotoDetailsContract.Event.NavigateUp)

            assert(viewModel.effect.first() is PhotoDetailsContract.Effect.Navigation.NavigateUp)
        }
    }

    @Test
    fun `verify navigate to profile event`() {
        runTest {
            initViewModel()
            viewModel.setEvent(PhotoDetailsContract.Event.SelectProfile("saiful", "Saiful"))

            assert(viewModel.effect.first() is PhotoDetailsContract.Effect.Navigation.ToProfile)
        }
    }
}