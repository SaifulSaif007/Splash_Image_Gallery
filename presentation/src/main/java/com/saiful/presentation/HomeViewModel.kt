package com.saiful.presentation

import com.saiful.core.ui.BaseViewModel
import com.saiful.core.ui.ViewEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeContract.Event, HomeContract.Effect>() {
    override fun handleEvents(event: ViewEvent) {
        when (event) {
            is HomeContract.Event.SelectCollection -> {
                setEffect {
                    HomeContract.Effect.Navigation.ToCollectionDetail(
                        event.collectionId,
                        event.title,
                        event.desc,
                        event.count,
                        event.author
                    )
                }
            }

            is HomeContract.Event.SelectPhoto -> {
                setEffect { HomeContract.Effect.Navigation.ToPhotoDetail(event.photoId) }
            }

            is HomeContract.Event.SelectProfile -> {
                setEffect {
                    HomeContract.Effect.Navigation.ToProfile(
                        event.userName,
                        event.profileName
                    )
                }
            }
        }
    }
}
