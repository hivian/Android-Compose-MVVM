package com.hivian.lydia_test.ui

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

interface IToolbarNavigationListener {
    fun navigateUp()
}

@set:BindingAdapter("visible")
var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("onToolbarNavigateUp")
fun Toolbar.onNavigateUp(toolbarNavigationListener: IToolbarNavigationListener) {
    setNavigationOnClickListener {
        toolbarNavigationListener.navigateUp()
    }
}
