package com.saiful.presentation.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.saiful.presentation.R
import com.saiful.presentation.composables.EmptyViewPreview
import com.saiful.presentation.theme.collectionInfoSubTitle
import com.saiful.presentation.theme.photoDetailsInfo
import com.saiful.presentation.theme.primaryText
import com.saiful.presentation.theme.titleText
import com.saiful.presentation.theme.toolbarText
import com.saiful.presentation.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "profile name",
                        style = MaterialTheme.typography.toolbarText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        //todo
                    }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                }
            )
        }
    ) { padding ->
        ProfileScreenContent(
            modifier = modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileScreenContent(modifier: Modifier) {
    Column(
        modifier = modifier.scrollable(
            state = rememberScrollState(),
            orientation = Orientation.Vertical
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = "url", //todo
                placeholder = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(height = 60.dp, width = 60.dp)
                    .clip(CircleShape)
                    .testTag(TestTags.PROFILE_IMAGE),
                contentScale = ContentScale.Crop
            )

            UserInfoCell(
                title = "Photos",
                info = "292"
            )
            UserInfoCell(
                title = "Likes",
                info = "122"
            )
            UserInfoCell(
                title = "Collection",
                info = "22"
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Profile Name",
                style = MaterialTheme.typography.titleText
            )
            Text(
                text = "Name with some text",
                style = MaterialTheme.typography.collectionInfoSubTitle
            )
            Text(
                text = "Description",
                style = MaterialTheme.typography.photoDetailsInfo,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }

        val pagerState = rememberPagerState()
        val tabs = LocalContext.current.resources.getStringArray(R.array.dashboardTabTitle) //todo

        Column {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.primaryText
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            //todo
                        })
                }
            }

            HorizontalPager(
                pageCount = tabs.size,
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> EmptyViewPreview()
                    1 -> EmptyViewPreview()
                }
            }
        }
    }
}

@Composable
private fun UserInfoCell(title: String, info: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.photoDetailsInfo)
        Text(
            text = info,
            style = MaterialTheme.typography.photoDetailsInfo,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(modifier = Modifier)
}