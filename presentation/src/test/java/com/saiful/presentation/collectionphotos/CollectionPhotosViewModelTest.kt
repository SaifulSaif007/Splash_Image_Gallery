package com.saiful.presentation.collectionphotos

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.*
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetCollectionPhotoUseCase
import com.saiful.presentation.Routes
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionPhotosViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val collectionPhotoUseCase: GetCollectionPhotoUseCase = mock()
    private val savedStateHandle: SavedStateHandle = Mockito.mock()
    private lateinit var viewModel: CollectionPhotosViewModel
    private lateinit var flowPagingData: Flow<PagingData<PhotoItem>>
    private val collectionId = "1"
    private val collectionTitle = "collection-title"
    private val collectionDesc = "collection-desc"
    private val collectionTotalPhotos = "10"
    private val collectionAuthor = "author"

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

        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<Routes.CollectionPhotos>() } returns Routes.CollectionPhotos(
            collectionId,
            collectionTitle,
            collectionDesc,
            collectionTotalPhotos,
            collectionAuthor
        )
    }

    override fun tearDown() {
        reset(collectionPhotoUseCase)
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    private fun initViewModel() {
        viewModel = CollectionPhotosViewModel(
            collectionPhotoUseCase = collectionPhotoUseCase,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `get collection photos returns paging data`() {
        runTest {
            whenever(
                collectionPhotoUseCase(collectionId)
            ).thenReturn(flowPagingData)

            initViewModel()

            val result = flowOf(viewModel.photoState.first()).asSnapshot()
            verify(collectionPhotoUseCase, only()).invoke(collectionId)
            assert(result.isNotEmpty())
            assert(result.size == 2)
        }
    }

    @Test
    fun `get collection photos returns empty paging data`() {
        runTest {
            whenever(
                collectionPhotoUseCase(collectionId)
            ).thenReturn(
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

            verify(collectionPhotoUseCase, only()).invoke(collectionId)
            assert(result.isEmpty())
        }
    }


    @Test
    fun `verify select photo event`() {
        runTest {
            `get collection photos returns paging data`()

            viewModel.setEvent(CollectionPhotosContract.Event.SelectPhoto("1"))

            assert(viewModel.effect.first() is CollectionPhotosContract.Effect.Navigation.ToPhotoDetail)

        }
    }

    @Test
    fun `verify navigate back event`() {
        runTest {
            `get collection photos returns paging data`()

            viewModel.setEvent(CollectionPhotosContract.Event.NavigateBack)

            assert(viewModel.effect.first() is CollectionPhotosContract.Effect.Navigation.NavigateBack)
        }
    }

    @Test
    fun `verify select profile event`() {
        runTest {
            `get collection photos returns paging data`()

            viewModel.setEvent(CollectionPhotosContract.Event.SelectProfile("saiful", "Saiful"))

            assert(viewModel.effect.first() is CollectionPhotosContract.Effect.Navigation.ToProfile)
        }
    }

}