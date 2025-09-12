package com.saiful.presentation.search.user

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.SearchUserItem
import com.saiful.domain.usecase.GetSearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val getSearchUserUseCase: GetSearchUserUseCase
) : BaseViewModel<SearchUserContract.Event, SearchUserContract.Effect>() {

    private val _userListState: MutableStateFlow<PagingData<SearchUserItem>> =
        MutableStateFlow(value = PagingData.empty())
    val userState: StateFlow<PagingData<SearchUserItem>> get() = _userListState

    private fun searchUser(query: String) {
        viewModelScope.launch {
            getSearchUserUseCase.execute(query)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _userListState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is SearchUserContract.Event.SearchUser -> {
                if (event.query.isNotEmpty()) searchUser(event.query)
            }
        }
    }
}