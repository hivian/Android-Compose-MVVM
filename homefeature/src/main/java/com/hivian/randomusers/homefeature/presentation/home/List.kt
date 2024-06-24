package com.hivian.randomusers.homefeature.presentation.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore : () -> Unit
){
    var previousIndex = 0

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false

            val scrolledToBottom = lastVisibleItem.index > previousIndex
            val isLastItem = lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
            val shouldLoadMore = scrolledToBottom && isLastItem

            previousIndex = lastVisibleItem.index

            shouldLoadMore
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) loadMore() }
    }
}
