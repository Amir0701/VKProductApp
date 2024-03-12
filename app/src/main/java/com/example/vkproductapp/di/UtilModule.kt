package com.example.vkproductapp.di

import com.example.vkproductapp.util.InternetConnectionChecker
import org.koin.dsl.module

val utilModule = module {
    single {
        InternetConnectionChecker(get())
    }
}