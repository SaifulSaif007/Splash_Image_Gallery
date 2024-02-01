package com.saiful.presentation.collectionphotos

import androidx.lifecycle.SavedStateHandle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.domain.model.PhotoItem
import com.saiful.domain.usecase.GetCollectionPhotoUseCase
import com.saiful.presentation.utils.Constants
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
class CollectionPhotosViewModelTest : BaseViewModelTest() {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val collectionPhotoUseCase: GetCollectionPhotoUseCase = mock()
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
    }

    override fun tearDown() {
        reset(collectionPhotoUseCase)
    }

    private fun initViewModel() {
        viewModel = CollectionPhotosViewModel(
            collectionPhotoUseCase = collectionPhotoUseCase,
            savedStateHandle = SavedStateHandle().apply {
                this[Constants.COLLECTION_ID] = collectionId
                this[Constants.COLLECTION_TITLE] = collectionTitle
                this[Constants.COLLECTION_DESCRIPTION] = collectionDesc
                this[Constants.COLLECTION_PHOTO_COUNT] = collectionTotalPhotos
                this[Constants.COLLECTION_AUTHOR] = collectionAuthor
            }
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

            viewModel.setEvent(CollectionPhotosContract.Event.SelectProfile("saiful"))

            assert(viewModel.effect.first() is CollectionPhotosContract.Effect.Navigation.ToProfile)
        }
    }

}