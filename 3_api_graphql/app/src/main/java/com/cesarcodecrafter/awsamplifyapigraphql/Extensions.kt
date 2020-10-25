package com.cesarcodecrafter.awsamplifyapigraphql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachRoot)
}