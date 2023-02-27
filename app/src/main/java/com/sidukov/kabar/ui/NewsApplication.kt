package com.sidukov.kabar.ui

import android.app.Application
import com.sidukov.kabar.di.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class NewsApplication : Application() {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    var instance: NewsApplication? = null

    fun androidInjector(): AndroidInjector<Any> = androidInjector

    companion object {
        lateinit var appComponent: NewsAppComponent

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerNewsAppComponent.builder()
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule())
            .appModule(AppModule(this))
            .storageModule(StorageModule())
            .build()
    }

}