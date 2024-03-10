package com.example.vkproductapp.di

import com.example.vkproductapp.util.InternetConnection
import org.koin.dsl.module

val utilModule = module {
    single {
        InternetConnection(get())
    }
}