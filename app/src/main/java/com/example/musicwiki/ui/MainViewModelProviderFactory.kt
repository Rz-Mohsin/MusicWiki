package com.example.musicwiki.ui

import android.app.Application
import android.text.Spannable.Factory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicwiki.repository.MusicRepository

class MainViewModelProviderFactory(
    val app : Application,
    val musicRepository: MusicRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app,musicRepository) as T
    }
}