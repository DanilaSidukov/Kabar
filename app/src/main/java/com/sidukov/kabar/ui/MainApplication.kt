package com.sidukov.kabar.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import org.koin.core.context.startKoin
import com.sidukov.kabar.di.provideApiModule
import com.sidukov.kabar.di.provideNewsRepositoryModule
import com.sidukov.kabar.di.provideNewsViewModel
import com.sidukov.kabar.di.provideSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class MainApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(listOf(
                provideApiModule,
                provideSettings,
                provideNewsRepositoryModule,
                provideNewsViewModel
            )
            )
        }

    }

}