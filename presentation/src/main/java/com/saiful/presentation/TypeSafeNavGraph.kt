package com.saiful.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.saiful.core.domain.DomainException

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onError: (DomainException) -> Unit
) {
    composable<Routes.Home> {
        HomeScreen(
            onError = onError,
            onNavigationRequest = {
                when (it) {
                    is HomeContract.Effect.Navigation.ToCollectionDetail -> {
                        navController.navigate(
                            HomeNavRoute.CollectionPhotos.createRoute(
                                collectionId = it.collectionId,
                                collectionTitle = it.title,
                                collectionDescription = it.desc,
                                collectionPhotoCount = it.count,
                                collectionAuthor = it.author
                            )
                        )
                    }

                    is HomeContract.Effect.Navigation.ToPhotoDetail -> {
                        navController.navigate(
                            HomeNavRoute.PhotoDetails.createRoute(photoId = it.photoId)
                        )
                    }

                    is HomeContract.Effect.Navigation.ToProfile -> {
                        navController.navigate(
                            HomeNavRoute.Profile.createRoute(
                                userName = it.userName,
                                profileName = it.profileName
                            )
                        )
                    }
                }
            }
        )
    }

}