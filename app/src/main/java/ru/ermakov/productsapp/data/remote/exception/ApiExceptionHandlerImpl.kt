package ru.ermakov.productsapp.data.remote.exception

import com.google.gson.Gson
import retrofit2.Response

class ApiExceptionHandlerImpl(private val gson: Gson) : ApiExceptionHandler {
    override fun handleApiException(response: Response<*>): Exception {
        val apiExceptionBody = gson.fromJson(
            response.errorBody()?.string(),
            ApiExceptionBody::class.java
        )
        return Exception(apiExceptionBody.errorMessage)
    }
}