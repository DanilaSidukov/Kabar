package com.sidukov.kabar.di

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.sidukov.kabar.data.settings.Settings
import com.sidukov.kabar.ui.news.newscategory.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val provideSettings = module {
    single {
        Settings(context = get())
    }
}

var newsViewModel: NewsViewModel? = null

@RequiresApi(Build.VERSION_CODES.O)
val provideNewsViewModel = module {
    this@module.viewModelOf(::NewsViewModel)
}

//    viewModelFactory {
//        viewModel {
//            NewsViewModel(
//                NewsRepository(ApiClient(context = get()))
//            )
//        }
//    }