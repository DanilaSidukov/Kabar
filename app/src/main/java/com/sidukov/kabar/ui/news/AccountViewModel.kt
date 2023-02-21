package com.sidukov.kabar.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sidukov.kabar.data.settings.AccountRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

open class AccountViewModel @Inject constructor(
    val repository: AccountRepository
): ViewModel() {


}