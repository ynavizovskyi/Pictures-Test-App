package com.ynavizovskyi.picturestestapp.presetntation

import android.widget.ImageView
import com.bumptech.glide.Glide

inline fun ImageView.loadImage(url: String, width: Int) {
    Glide.with(context).load(url).override(width).into(this)
}