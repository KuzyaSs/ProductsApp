package ru.ermakov.productsapp.data.remote.exception

import retrofit2.Response

interface ApiExceptionHandler {
    fun handleApiException(response: Response<*>): Exception
}