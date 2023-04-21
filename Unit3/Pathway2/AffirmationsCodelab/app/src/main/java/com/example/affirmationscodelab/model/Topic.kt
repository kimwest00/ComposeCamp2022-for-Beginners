package com.example.affirmationscodelab.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes
    val stringResourceId: Int,
    val view:Int,
    @DrawableRes
    val imageResourceId: Int
)
