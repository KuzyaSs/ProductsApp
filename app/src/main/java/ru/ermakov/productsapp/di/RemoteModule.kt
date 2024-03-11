package ru.ermakov.productsapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ermakov.productsapp.data.remote.api.CategoryApi
import ru.ermakov.productsapp.data.remote.api.ProductApi
import ru.ermakov.productsapp.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.productsapp.data.remote.dataSource.CategoryRemoteDataSourceImpl
import ru.ermakov.productsapp.data.remote.dataSource.ProductRemoteDataSource
import ru.ermakov.productsapp.data.remote.dataSource.ProductRemoteDataSourceImpl
import ru.ermakov.productsapp.data.remote.exception.ApiExceptionHandler
import ru.ermakov.productsapp.data.remote.exception.ApiExceptionHandlerImpl

private const val BASE_URL = "https://dummyjson.com/"

@Module
class RemoteModule {
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideApiExceptionHandler(gson: Gson): ApiExceptionHandler {
        return ApiExceptionHandlerImpl(gson = gson)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    fun provideCategoryRemoteDataSource(
        categoryApi: CategoryApi,
        apiExceptionHandler: ApiExceptionHandler
    ): CategoryRemoteDataSource {
        return CategoryRemoteDataSourceImpl(
            categoryApi = categoryApi,
            apiExceptionHandler = apiExceptionHandler
        )
    }

    @Provides
    fun provideProductRemoteDataSource(
        productApi: ProductApi,
        apiExceptionHandler: ApiExceptionHandler
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(
            productApi = productApi,
            apiExceptionHandler = apiExceptionHandler
        )
    }
}