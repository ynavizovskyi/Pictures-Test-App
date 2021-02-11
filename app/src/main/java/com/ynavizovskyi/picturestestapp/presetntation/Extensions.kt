package com.ynavizovskyi.picturestestapp.presetntation

import android.widget.ImageView
import com.bumptech.glide.Glide

inline fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}