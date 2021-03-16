package com.example.android.politicalpreparedness


import androidx.multidex.MultiDexApplication
import com.example.android.politicalpreparedness.di.Module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CustomApplication)
            modules(listOf(Module.getModule()))
        }
    }
}