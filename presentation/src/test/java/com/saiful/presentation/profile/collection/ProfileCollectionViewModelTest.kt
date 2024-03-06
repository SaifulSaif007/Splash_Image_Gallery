package com.saiful.presentation.profile.collection

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.nhaarman.mockito_kotlin.only
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetProfileCollectionUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileCollectionViewModelTest : BaseViewModelTest() {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val profileCollectionUseCase: GetProfileCollectionUseCase = mock()
    private lateinit var viewModel: ProfileCollectionViewModel
    private lateinit var flowPagingData: Flow<PagingData<CollectionItem>>

    private val userName = "saiful"

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
        reset(profileCollectionUseCase)
    }

    private fun initViewModel() {
        viewModel = ProfileCollectionViewModel(profileCollectionUseCase)
    }

    @Test
    fun `verify loadData gets profile collections flow data`() {
        runTest {
            whenever(profileCollectionUseCase(userName)).thenReturn(flowPagingData)

            initViewModel()
            viewModel.setEvent(ProfileCollectionContract.Event.Initialize(userName))

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            verify(profileCollectionUseCase, only()).invoke(userName)
            assert(result.size == 2)
            assert(result[0].collectionId == "1")
            assert(result[1].collectionId == "2")
        }
    }

    @Test
    fun `verify loadData gets empty profile collections flow data`() {
        runTest {
            whenever(profileCollectionUseCase(userName)).thenReturn(
                flowOf(
                    PagingData.from(
                        data = listOf(),
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.Error(Exception("ex")),
                            prepend = LoadState.NotLoading(true),
                            append = LoadState.NotLoading(true)
                        )
                    )
                )
            )

            initViewModel()
            viewModel.setEvent(ProfileCollectionContract.Event.Initialize(userName))

            val result = flowOf(viewModel.collectionState.first()).asSnapshot()

            verify(profileCollectionUseCase, only()).invoke(userName)
            assert(result.isEmpty())
        }
    }

    @Test
    fun `verify select collection gets selected collection`() {
        runTest {
            `verify loadData gets profile collections flow data`()

            viewModel.setEvent(
                ProfileCollectionContract.Event.SelectCollection(
                    "1",
                    "abc",
                    "desc",
                    "12",
                    "saiful"
                )
            )

            assert(viewModel.effect.first() is ProfileCollectionContract.Effect.Navigation.ToCollectionDetails)
        }
    }
}