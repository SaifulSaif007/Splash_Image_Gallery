package com.saiful.presentation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.saiful.core.domain.DomainException
import com.saiful.presentation.collectionphotos.CollectionPhotosContract
import com.saiful.presentation.collectionphotos.CollectionPhotosScreen
import com.saiful.presentation.photodetails.PhotoDetailsContract
import com.saiful.presentation.photodetails.PhotoDetailsScreen
import com.saiful.presentation.profile.ProfileContract
import com.saiful.presentation.profile.ProfileScreen

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
                            Routes.CollectionPhotos(
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
                            Routes.PhotoDetails(photoId = it.photoId)
                        )
                    }

                    is HomeContract.Effect.Navigation.ToProfile -> {
                        navController.navigate(
                            Routes.Profile(
                                userName = it.userName,
                                userProfileName = it.profileName
                            )
                        )
                    }
                }
            }
        )
    }


    composable<Routes.PhotoDetails> {
        PhotoDetailsScreen { navigationRequest ->
            when (navigationRequest) {
                is PhotoDetailsContract.Effect.Navigation.NavigateUp -> {
                    navController.navigateUp()
                }

                is PhotoDetailsContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(
                        Routes.Profile(
                            navigationRequest.userName,
                            navigationRequest.profileName
                        )
                    )
                }
            }
        }
    }


    composable<Routes.CollectionPhotos> {
        CollectionPhotosScreen { navigationRequest ->
            when (navigationRequest) {
                is CollectionPhotosContract.Effect.Navigation.ToPhotoDetail -> {
                    navController.navigate(
                        Routes.PhotoDetails(
                            navigationRequest.photoId
                        )
                    )
                }

                is CollectionPhotosContract.Effect.Navigation.NavigateBack -> {
                    navController.navigateUp()
                }

                is CollectionPhotosContract.Effect.Navigation.ToProfile -> {
                    navController.navigate(
                        Routes.Profile(
                            navigationRequest.userName,
                            navigationRequest.profileName
                        )
                    )
                }
            }
        }
    }

    composable<Routes.Profile> { entry ->
        val profile: Routes.Profile = entry.toRoute()
        ProfileScreen(
            profileUserName = profile.userName
        ) { navigationRequest ->
            when (navigationRequest) {
                is ProfileContract.Effect.Navigation.NavigateUp -> navController.navigateUp()
                is ProfileContract.Effect.Navigation.ToPhotoDetails -> {
                    navController.navigate(
                        Routes.PhotoDetails(photoId = navigationRequest.photoId)
                    )
                }

                is ProfileContract.Effect.Navigation.ToCollectionDetail -> {
                    navController.navigate(
                        Routes.CollectionPhotos(
                            collectionId = navigationRequest.collectionId,
                            collectionTitle = navigationRequest.title,
                            collectionDescription = navigationRequest.desc,
                            collectionPhotoCount = navigationRequest.count,
                            collectionAuthor = navigationRequest.author
                        )
                    )
                }
            }
        }
    }
}