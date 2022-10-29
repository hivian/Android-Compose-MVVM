package com.hivian.lydia_test.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.hivian.lydia_test.presentation.ViewModelVisualState

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
