package com.example.myapplication


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class LiveKitMeetConferenceViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveKitMeetConferenceViewModel::class.java)) {
            return LiveKitMeetConferenceViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}