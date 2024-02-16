package com.saiful.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.saiful.core.domain.DomainException
import com.saiful.presentation.collectionphotos.CollectionPhotosContract
import com.saiful.presentation.collectionphotos.CollectionPhotosScreen
import com.saiful.presentation.photodetails.PhotoDetailsContract
import com.saiful.presentation.photodetails.PhotoDetailsScreen
import com.saiful.presentation.profile.ProfileContract
import com.saiful.presentation.profile.ProfileScreen
import com.saiful.presentation.utils.Constants.COLLECTION_AUTHOR
import com.saiful.presentation.utils.Constants.COLLECTION_DESCRIPTION
import com.saiful.presentation.utils.Constants.COLLECTION_ID
import com.saiful.presentation.utils.Constants.COLLECTION_PHOTO_COUNT
import com.saiful.presentation.utils.Constants.COLLECTION_TITLE
import com.saiful.presentation.utils.Constants.PHOTO_ID
import com.saiful.presentation.utils.Constants.USER_NAME
import com.saiful.presentation.utils.Constants.USER_PROFILE_NAME
import kotlinx.coroutines.CoroutineScope

private const val HOME = "home"

sealed class HomeNavRoute(val route: String) {
    object Root : HomeNavRoute("$HOME/")
    object Home : HomeNavRoute("${HOME}/home")
    object PhotoDetails : HomeNavRoute("${HOME}/photodetails?$PHOTO_ID={$PHOTO_ID}") {
        fun createRoute(photoId: String) = "$HOME/photodetails?$PHOTO_ID=${photoId}"
    }

    object CollectionPhotos :
        HomeNavRoute(
            "${HOME}/collectionphotos?$COLLECTION_ID={$COLLECTION_ID}" +
                    "?$COLLECTION_TITLE={$COLLECTION_TITLE}" +
                    "?$COLLECTION_DESCRIPTION={$COLLECTION_DESCRIPTION}" +
                    "?$COLLECTION_PHOTO_COUNT={$COLLECTION_PHOTO_COUNT}" +
                    "?$COLLECTION_AUTHOR={$COLLECTION_AUTHOR}"
        ) {
        fun createRoute(
            collectionId: String,
            collectionTitle: String,
            collectionDescription: String,
            collectionPhotoCount: String,
            collectionAuthor: String
        ) =
            "$HOME/collectionphotos?$COLLECTION_ID=${collectionId}" +
                    "?$COLLECTION_TITLE=${collectionTitle}" +
                    "?$COLLECTION_DESCRIPTION=${collectionDescription}" +
                    "?$COLLECTION_PHOTO_COUNT=${collectionPhotoCount}" +
                    "?$COLLECTION_AUTHOR=${collectionAuthor}"
    }

    object Profile : HomeNavRoute(
        "${HOME}/profile?$USER_NAME={$USER_NAME}" + "?$USER_PROFILE_NAME={$USER_PROFILE_NAME}"
    ) {
        fun createRoute(userName: String, profileName: String) =
            "$HOME/profile?$USER_NAME=${userName}" +
                    "?$USER_PROFILE_NAME=${profileName}"
    }
}

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    coroutineScope: CoroutineScope,
    onError: (DomainException) -> Unit
) {

    navigation(
        route = HomeNavRoute.Root.route,
        startDestination = HomeNavRoute.Home.route
    ) {
        composable(
            route = HomeNavRoute.Home.route
        ) {
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

        composable(
            route = HomeNavRoute.PhotoDetails.route,
            arguments = listOf(navArgument(PHOTO_ID) { type = NavType.StringType })
        ) {
            PhotoDetailsScreen { navigationRequest ->
                when (navigationRequest) {
                    is PhotoDetailsContract.Effect.Navigation.NavigateUp -> {
                        navController.navigateUp()
                    }

                    is PhotoDetailsContract.Effect.Navigation.ToProfile -> {
                        navController.navigate(
                            HomeNavRoute.Profile.createRoute(
                                navigationRequest.userName,
                                navigationRequest.profileName
                            )
                        )
                    }
                }
            }
        }

        composable(
            route = HomeNavRoute.CollectionPhotos.route,
            arguments = listOf(
                navArgument(COLLECTION_ID) { type = NavType.StringType },
                navArgument(COLLECTION_TITLE) { type = NavType.StringType },
                navArgument(COLLECTION_DESCRIPTION) { type = NavType.StringType },
                navArgument(COLLECTION_PHOTO_COUNT) { type = NavType.StringType },
                navArgument(COLLECTION_AUTHOR) { type = NavType.StringType },
            )
        ) {
            CollectionPhotosScreen { navigationRequest ->
                when (navigationRequest) {
                    is CollectionPhotosContract.Effect.Navigation.ToPhotoDetail -> {
                        navController.navigate(
                            HomeNavRoute.PhotoDetails.createRoute(
                                navigationRequest.photoId
                            )
                        )
                    }

                    is CollectionPhotosContract.Effect.Navigation.NavigateBack -> {
                        navController.navigateUp()
                    }

                    is CollectionPhotosContract.Effect.Navigation.ToProfile -> {
                        navController.navigate(
                            HomeNavRoute.Profile.createRoute(
                                navigationRequest.userName,
                                navigationRequest.profileName
                            )
                        )
                    }
                }
            }
        }

        composable(
            route = HomeNavRoute.Profile.route,
            arguments = listOf(
                navArgument(USER_NAME) { type = NavType.StringType },
                navArgument(USER_PROFILE_NAME) { type = NavType.StringType }
            )
        ) { entry ->
            ProfileScreen(
                profileUserName = entry.arguments?.getString(USER_NAME) ?: ""
            ) { navigationRequest ->
                when (navigationRequest) {
                    is ProfileContract.Effect.Navigation.NavigateUp -> navController.navigateUp()
                    is ProfileContract.Effect.Navigation.ToPhotoDetails -> {
                        navController.navigate(
                            HomeNavRoute.PhotoDetails.createRoute(photoId = navigationRequest.photoId)
                        )
                    }
                }
            }
        }
    }
}