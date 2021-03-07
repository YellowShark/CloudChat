package ru.yellowshark.cloudchat.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import ru.yellowshark.cloudchat.R

@BindingAdapter("layoutMarginEnd")
fun setLayoutMarginEnd(view: View, dimen: Float) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.marginEnd = dimen.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("layoutMarginStart")
fun setLayoutMarginStart(view: View, dimen: Float) {
    val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.marginStart = dimen.toInt()
    view.layoutParams = layoutParams
}

@BindingAdapter("loadAvatar")
fun loadAvatar(imageView: ImageView, url: String) {
    Glide.with(imageView.context.applicationContext)
        .load("https://i.kym-cdn.com/entries/icons/mobile/000/013/564/doge.jpg")
        .placeholder(R.drawable.ic_person)
        .circleCrop()
        .into(imageView)
}