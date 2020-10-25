package com.cesarcodecrafter.upto3.models

import java.util.*

data class Task (
    var name: String,
    var state: Boolean,
    var createdAt: Date,
    var updatedAt: Date
)