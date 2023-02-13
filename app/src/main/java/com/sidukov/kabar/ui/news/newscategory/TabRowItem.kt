package com.sidukov.kabar.ui.news.newscategory

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit
)