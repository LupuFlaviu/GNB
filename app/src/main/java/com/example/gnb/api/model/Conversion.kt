package com.example.gnb.api.model

import com.google.gson.annotations.SerializedName

data class Conversion(
    @SerializedName("from")
    val from: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("to")
    val to: String
)