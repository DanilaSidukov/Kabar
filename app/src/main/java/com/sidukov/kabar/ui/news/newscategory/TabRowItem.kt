package com.sidukov.kabar.ui.news.newscategory

import androidx.compose.runtime.Composable

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit
)