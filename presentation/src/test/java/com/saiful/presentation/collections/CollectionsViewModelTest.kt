package com.saiful.presentation.collections

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetCollectionUseCase
import com.saiful.test.unit.BaseViewModelTest
import com.saiful.test.unit.rules.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CollectionsViewModelTest : BaseViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val collectionUseCase: GetCollectionUseCase = mockk()
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
                        description = "desc",
                        profileUserName = "neom"
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
                        description = "desc",
                        profileUserName = "abc"
                    )
                )
            )
        )
    }

    override fun tearDown() {
        unmockkAll()
    }

    private fun initViewModel() {
        viewModel = CollectionsViewModel(collectionUseCase)
    }

    @Test
    fun `load data gets flow pager data`() {
        runTest {
            coEvery { collectionUseCase(Unit) } returns flowPagingData

            initViewModel()

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            assert(result.isNotEmpty())
            assert(result.size == 2)
            coVerify(exactly = 1) { collectionUseCase.invoke(Unit) }
        }
    }

    @Test
    fun `load data gets exception flow pager data`() {
        runTest {
            coEvery {
                collectionUseCase(Unit)
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

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            assert(result.isEmpty())
            coVerify(exactly = 1) { collectionUseCase.invoke(Unit) }
        }
    }

    @Test
    fun `verify select profile`() {
        runTest {
            `load data gets flow pager data`()

            viewModel.setEvent(CollectionsContract.Event.SelectProfile("saiufl", "Saiful"))

            assert(viewModel.effect.first() is CollectionsContract.Effect.Navigation.ToProfile)
        }
    }

    @Test
    fun `verify select collection`() {
        runTest {
            `load data gets flow pager data`()

            viewModel.setEvent(
                CollectionsContract.Event.SelectCollection(
                    "1",
                    "abc",
                    "desc",
                    "12",
                    "saiful"
                )
            )

            assert(viewModel.effect.first() is CollectionsContract.Effect.Navigation.ToCollectionDetails)
        }
    }

}