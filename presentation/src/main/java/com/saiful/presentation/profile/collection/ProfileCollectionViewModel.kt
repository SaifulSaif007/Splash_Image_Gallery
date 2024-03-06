package com.saiful.presentation.profile.collection

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import com.saiful.domain.model.CollectionItem
import com.saiful.domain.usecase.GetProfileCollectionUseCase
import com.saiful.domain.usecase.userName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileCollectionViewModel @Inject constructor(
    private val profileCollectionUseCase: GetProfileCollectionUseCase
) : BaseViewModel<ProfileCollectionContract.Event, ProfileCollectionContract.Effect>() {

    private val _collectionState: MutableStateFlow<PagingData<CollectionItem>> =
        MutableStateFlow(value = PagingData.empty())

    val collectionState: StateFlow<PagingData<CollectionItem>> get() = _collectionState

    private fun loadData(userName: userName) {
        viewModelScope.launch {
            profileCollectionUseCase(userName)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _collectionState.value = it
                }
        }
    }

    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is ProfileCollectionContract.Event.Initialize -> loadData(event.username)

            is ProfileCollectionContract.Event.SelectCollection -> {
                setEffect {
                    ProfileCollectionContract.Effect.Navigation.ToCollectionDetails(
                        collectionId = event.collectionId,
                        collectionName = event.collectionName,
                        collectionDesc = event.collectionDesc,
                        totalPhotos = event.totalPhotos,
                        collectionAuthor = event.collectionAuthor
                    )
                }
            }
        }
    }


}