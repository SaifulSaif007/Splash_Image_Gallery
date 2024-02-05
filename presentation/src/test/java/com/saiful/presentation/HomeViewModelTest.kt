package com.saiful.presentation

import com.saiful.test.unit.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    private fun initViewModel() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `verify select collection event`() {
        runTest {
            initViewModel()
            viewModel.setEvent(
                HomeContract.Event.SelectCollection(
                    "1",
                    "title",
                    "description",
                    "22",
                    "saiful"
                )
            )
            assert(viewModel.effect.first() is HomeContract.Effect.Navigation.ToCollectionDetail)
        }
    }

    @Test
    fun `verify select photo event`() {
        runTest {
            initViewModel()
            viewModel.setEvent(HomeContract.Event.SelectPhoto("1"))

            assert(viewModel.effect.first() is HomeContract.Effect.Navigation.ToPhotoDetail)
        }
    }

    @Test
    fun `verify select profile event`() {
        runTest {
            initViewModel()
            viewModel.setEvent(HomeContract.Event.SelectProfile("username"))

            assert(viewModel.effect.first() is HomeContract.Effect.Navigation.ToProfile)
        }
    }

}