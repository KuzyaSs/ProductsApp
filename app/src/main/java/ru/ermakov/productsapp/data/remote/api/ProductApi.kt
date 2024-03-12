package ru.ermakov.productsapp.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.productsapp.data.remote.model.RemoteProduct
import ru.ermakov.productsapp.data.remote.model.RemoteProducts

interface ProductApi {
    @GET("products")
    suspend fun getProductPage(
        @Query("limit") limit: Long,
        @Query("skip") skip: Long
    ): Response<RemoteProducts>

    @GET("products/category/{category}")
    suspend fun getProductPageByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Long,
        @Query("skip") skip: Long
    ): Response<RemoteProducts>

    @GET("products/search")
    suspend fun getProductPageBySearchQuery(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Long,
        @Query("skip") skip: Long
    ): Response<RemoteProducts>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<RemoteProduct>
}