package com.example.vkproductapp.di

import com.example.vkproductapp.data.service.ProductService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    includes(netWorkModule)
    single {
        provideProductService(get())
    }
}

internal fun provideProductService(retrofit: Retrofit): ProductService{
    return retrofit.create(ProductService::class.java)
}