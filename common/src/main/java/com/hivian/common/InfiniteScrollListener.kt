package com.hivian.common

import androidx.recyclerview.widget.RecyclerView

interface IScrollMoreDelegate {

    fun loadMore()

}

class InfiniteScrollListener(
    private val viewModelScrollDelegate: IScrollMoreDelegate
): RecyclerView.OnScrollListener() {

    var lastPercentage = 0f

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val offset = recyclerView.computeVerticalScrollOffset()
        val extent = recyclerView.computeVerticalScrollExtent()
        val range = recyclerView.computeVerticalScrollRange()

        val percentage = (100.0f * offset / (range - extent))

        if (percentage >= 80 && lastPercentage < 80) {
            viewModelScrollDelegate.loadMore()
        }
        lastPercentage = percentage
    }

}
