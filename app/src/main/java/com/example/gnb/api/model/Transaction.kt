package com.example.gnb.api.model

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("sku")
    val sku: String
)