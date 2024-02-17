package com.saiful.presentation.profile

import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.core.domain.Result
import com.saiful.domain.mapper.COLLECTIONS
import com.saiful.domain.mapper.LIKES
import com.saiful.domain.mapper.PHOTOS
import com.saiful.domain.model.ProfileInfo
import com.saiful.domain.usecase.GetProfileInfoUseCase
import com.saiful.presentation.utils.Constants
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val profileInfoUseCase: GetProfileInfoUseCase = mock()
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
    }

    override fun tearDown() {
        reset(profileInfoUseCase)
    }

    private fun initViewModel() {
        viewModel = ProfileViewModel(
            profileInfoUseCase,
            savedStateHandle = SavedStateHandle().apply {
                set(Constants.USER_NAME, userName)
                set(Constants.USER_PROFILE_NAME, profileName)
            }
        )
    }

    @Test
    fun `get profile info returns success result`() {
        runTest {
            whenever(profileInfoUseCase.invoke(userName)).thenReturn(
                Result.Success(profileInfo)
            )

            initViewModel()
            verify(profileInfoUseCase, times(1)).invoke(userName)

            val result = viewModel.uiState.value
            assert(result is ProfileViewModel.UIState.Success)
            assert((result as ProfileViewModel.UIState.Success).data == profileInfo)
        }
    }

    @Test
    fun `get profile info returns error result`() {
        runTest {
            whenever(profileInfoUseCase.invoke(userName)).thenReturn(
                Result.Error(domainException)
            )

            initViewModel()
            verify(profileInfoUseCase, times(1)).invoke(userName)

            val result = viewModel.uiState.value
            assert(result is ProfileViewModel.UIState.Error)
            assert((result as ProfileViewModel.UIState.Error).exception == domainException)
        }
    }

    @Test
    fun `verify navigate back event`() {
        runTest {
            initViewModel()

            viewModel.setEvent(ProfileContract.Event.NavigateBack)
            assert(viewModel.effect.first() is ProfileContract.Effect.Navigation.NavigateUp)
        }
    }

    @Test
    fun `verify navigate to photoDetails event`() {
        runTest {
            initViewModel()

            viewModel.setEvent(ProfileContract.Event.NavigateToPhotoDetails("1"))
            assert(viewModel.effect.first() is ProfileContract.Effect.Navigation.ToPhotoDetails)
        }
    }
}