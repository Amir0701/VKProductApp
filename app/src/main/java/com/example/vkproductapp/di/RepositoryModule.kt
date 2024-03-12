package com.example.vkproductapp.di

import com.example.vkproductapp.data.repository.CategoryRepositoryImpl
import com.example.vkproductapp.data.repository.ProductRepositoryImpl
import com.example.vkproductapp.domain.repository.CategoryRepository
import com.example.vkproductapp.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module{
    includes(serviceModule)

    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
}