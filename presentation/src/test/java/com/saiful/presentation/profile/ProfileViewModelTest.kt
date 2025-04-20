package com.saiful.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.saiful.core.domain.Result
import com.saiful.domain.mapper.COLLECTIONS
import com.saiful.domain.mapper.LIKES
import com.saiful.domain.mapper.PHOTOS
import com.saiful.domain.model.ProfileInfo
import com.saiful.domain.usecase.GetProfileInfoUseCase
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
class ProfileViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val profileInfoUseCase: GetProfileInfoUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val userName = "saiful"
    private val profileName = "Saiful"
    private lateinit var viewModel: ProfileViewModel
    private lateinit var profileInfo: ProfileInfo

    override fun setup() {
        profileInfo = ProfileInfo(
            profileName = "John Doe",
            location = "New York City",
            bio = "I am a professional photographer based in New York City.",
            profileImage = "https://example.com/profile_image.jpg",
            photos = "10,000",
            likes = "1,000",
            collection = "100",
            visibleTabs = listOf(PHOTOS, LIKES, COLLECTIONS)
        )

        mockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    override fun tearDown() {
        unmockkAll()
    }

    private fun initViewModel() {
        every { savedStateHandle.toRoute<Routes.Profile>() } returns Routes.Profile(
            userName,
            profileName
        )

        viewModel = ProfileViewModel(
            profileInfoUseCase,
            savedStateHandle
        )
    }

    @Test
    fun `get profile info returns success result`() {
        runTest {
            coEvery {
                profileInfoUseCase.invoke(userName)
            } returns Result.Success(profileInfo)

            initViewModel()

            val result = viewModel.uiState.value
            assert(result is ProfileViewModel.UIState.Success)
            assert((result as ProfileViewModel.UIState.Success).data == profileInfo)
            coVerify(exactly = 1) { profileInfoUseCase.invoke(userName) }
        }
    }

    @Test
    fun `get profile info returns error result`() {
        runTest {
            coEvery {
                profileInfoUseCase.invoke(userName)
            } returns Result.Error(domainException)


            initViewModel()

            val result = viewModel.uiState.value
            assert(result is ProfileViewModel.UIState.Error)
            assert((result as ProfileViewModel.UIState.Error).exception == domainException)
            coVerify(exactly = 1) { profileInfoUseCase.invoke(userName) }
        }
    }

    @Test
    fun `verify navigate back event`() {
        runTest {
            `get profile info returns success result`()

            viewModel.setEvent(ProfileContract.Event.NavigateBack)
            assert(viewModel.effect.first() is ProfileContract.Effect.Navigation.NavigateUp)
        }
    }

    @Test
    fun `verify navigate to photoDetails event`() {
        runTest {
            `get profile info returns success result`()

            viewModel.setEvent(ProfileContract.Event.NavigateToPhotoDetails("1"))
            assert(viewModel.effect.first() is ProfileContract.Effect.Navigation.ToPhotoDetails)
        }
    }
}