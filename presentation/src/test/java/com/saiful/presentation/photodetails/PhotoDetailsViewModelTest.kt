package com.saiful.presentation.photodetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.saiful.core.domain.Result
import com.saiful.domain.model.PhotoDetailsItem
import com.saiful.domain.usecase.GetPhotoDetailsUseCase
import com.saiful.presentation.Routes
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoDetailsViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val photoDetailsUseCase: GetPhotoDetailsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
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
        mockkStatic("androidx.navigation.SavedStateHandleKt")

    }

    override fun tearDown() {
        unmockkAll()
    }

    private fun initViewModel() {
        every {
            savedStateHandle.toRoute<Routes.PhotoDetails>()
        } returns Routes.PhotoDetails(photoId)

        viewModel = PhotoDetailsViewModel(
            photoDetailsUseCase = photoDetailsUseCase,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `verify get photo load photo details`() {
        runTest {
            coEvery {
                photoDetailsUseCase(photoId)
            } returns Result.Success(photoDetailsItem)


            initViewModel()

            val result = viewModel.uiState.value
            assert(result is PhotoDetailsViewModel.UIState.Success)
            assert((result as PhotoDetailsViewModel.UIState.Success).photoDetails == photoDetailsItem)
        }
    }

    @Test
    fun `verify get photo load error`() {
        runTest {
            coEvery {
                photoDetailsUseCase(photoId)
            } returns Result.Error(domainException)

            initViewModel()
            val result = viewModel.uiState.value
            assert(result is PhotoDetailsViewModel.UIState.Error)
            assert((result as PhotoDetailsViewModel.UIState.Error).exception == domainException)
        }
    }

    @Test
    fun `verify navigate up event`() {
        runTest {
            `verify get photo load photo details`()

            viewModel.setEvent(PhotoDetailsContract.Event.NavigateUp)

            assert(viewModel.effect.first() is PhotoDetailsContract.Effect.Navigation.NavigateUp)
        }
    }

    @Test
    fun `verify navigate to profile event`() {
        runTest {
            `verify get photo load photo details`()

            viewModel.setEvent(PhotoDetailsContract.Event.SelectProfile("saiful", "Saiful"))

            assert(viewModel.effect.first() is PhotoDetailsContract.Effect.Navigation.ToProfile)
        }
    }
}