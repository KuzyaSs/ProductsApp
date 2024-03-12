package ru.ermakov.productsapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteProducts(
    @SerializedName("limit")
    val limit: Long,
    @SerializedName("products")
    val products: List<RemoteProduct>,
    @SerializedName("skip")
    val skip: Long,
    @SerializedName("total")
    val total: Int
)