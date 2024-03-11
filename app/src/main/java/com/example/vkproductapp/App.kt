package com.example.vkproductapp

import android.app.Application
import com.example.vkproductapp.di.netWorkModule
import com.example.vkproductapp.di.repositoryModule
import com.example.vkproductapp.di.utilModule
import com.example.vkproductapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                repositoryModule,
                viewModelModule,
                utilModule
            )
        }
    }
}