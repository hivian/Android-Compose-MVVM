package com.hivian_compose_mvvm.basic_feature.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hivian.compose_mvvm.core.base.ViewModelVisualState
import com.hivian_compose_mvvm.basic_feature.domain.models.RandomUser

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    viewModel.initialize()

    HomeContent(
        HomeViewModelArg(
            randomUsers = viewModel.items,
            isListLoading = viewModel.showLoadMoreLoader,
            title = viewModel.title,
            errorMessage = viewModel.errorMessage,
            retryMessage = viewModel.retryMessage,
            refresh = { viewModel.refresh() },
            loadNext = { viewModel.loadNext() },
            openDetail = { userId -> viewModel.openRandomUserDetail(userId) },
            visualState = viewModel.viewModelVisualState
        )
    )
}


@Preview(name = "Light mode")
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    @PreviewParameter(HomeViewModelArgProvider::class) viewModelArg: HomeViewModelArg
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = viewModelArg.title) },
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            when (viewModelArg.visualState.value) {
                is ViewModelVisualState.Loading -> CircularProgressIndicator()
                is ViewModelVisualState.Success -> {
                    InitUserList(
                        viewModelArg.randomUsers,
                        isLoadingMore = viewModelArg.isListLoading.value,
                        onItemClick = { userId -> viewModelArg.openDetail(userId) },
                        onLoadMore = { viewModelArg.loadNext() }
                    )
                }
                is ViewModelVisualState.Error -> InitErrorView(
                    errorViewArgs = InitErrorViewArg(viewModelArg.errorMessage,viewModelArg.retryMessage),
                    onRetry = { viewModelArg.refresh() }
                )
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitErrorView(
    @PreviewParameter(InitErrorViewArgProvider::class)
    errorViewArgs: InitErrorViewArg,
    onRetry : () -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = errorViewArgs.errorMessage, style = MaterialTheme.typography.bodyMedium)
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { onRetry() }
        ) {
            Text(text = errorViewArgs.retryMessage)
        }
    }
}

@Composable
fun InitUserList(
    randomUsers: List<RandomUser>,
    isLoadingMore: Boolean,
    onItemClick : (Int) -> Unit,
    onLoadMore : () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(randomUsers) { user ->
            UserListItem(user = user, onItemClick = onItemClick)
        }
        if (!isLoadingMore) return@LazyColumn

        item {
            UserListLoadingItem()
        }
    }
    listState.OnBottomReached(buffer = 3) {
        onLoadMore()
    }
}

@Composable
fun UserListItem(user: RandomUser, onItemClick : (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(user.id) },
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            Modifier.height(IntrinsicSize.Min)
        ) {
            UserImage(imageUrlPath = user.picture)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(text = user.fullName, style = MaterialTheme.typography.headlineSmall)
                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(widthDp = 50, heightDp = 50)
@Composable
fun UserListLoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(widthDp = 50, heightDp = 50)
@Composable
private fun UserImage(imageUrlPath : String = "") {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrlPath)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = Modifier.size(66.dp),
        contentAlignment = Alignment.Center
    ) {
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize(0.5f),
                strokeWidth = 2.dp
            )
        }

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = CornerSize(8.dp),
                        topEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(8.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
        )
    }
}
