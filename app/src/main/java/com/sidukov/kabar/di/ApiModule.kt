package com.sidukov.kabar.di

import com.sidukov.kabar.data.remote.api.ApiClient
import org.koin.dsl.module

val provideApiModule = module {
    single { ApiClient(context = get()) }
}