package com.example.vkproductapp.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val gsonConverter= module {
    factory {
        GsonConverterFactory.create(get())
    }

    factory {
        Gson()
    }
}