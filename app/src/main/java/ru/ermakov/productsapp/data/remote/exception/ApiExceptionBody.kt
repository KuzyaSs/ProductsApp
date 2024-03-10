package ru.ermakov.productsapp.data.remote.exception

data class ApiExceptionBody(
    val timestamp: String,
    val status: Int,
    val errorMessage: String,
    val path: String
)