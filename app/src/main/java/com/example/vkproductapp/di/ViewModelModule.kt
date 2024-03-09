package com.example.vkproductapp.di

import com.example.vkproductapp.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ProductsViewModel)
}