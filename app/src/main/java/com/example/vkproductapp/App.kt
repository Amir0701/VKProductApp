package com.example.vkproductapp

import android.app.Application
import com.example.vkproductapp.di.netWorkModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(netWorkModule)
        }
    }
}