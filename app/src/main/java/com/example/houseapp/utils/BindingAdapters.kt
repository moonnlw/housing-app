package com.example.houseapp.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

@BindingAdapter("android:visibility")
fun MaterialCardView.bindVisibility(value: Boolean) {
    visibility = if (value) MaterialCardView.VISIBLE else MaterialCardView.GONE
}