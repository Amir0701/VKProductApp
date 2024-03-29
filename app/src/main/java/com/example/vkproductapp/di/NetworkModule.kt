package com.example.vkproductapp.di

import com.example.vkproductapp.BuildConfig
import com.example.vkproductapp.data.service.ProductService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netWorkModule = module {
    includes(okHttpClient, gsonConverter)

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }
}
