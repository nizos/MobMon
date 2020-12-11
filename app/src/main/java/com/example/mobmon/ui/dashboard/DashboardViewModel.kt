package com.example.mobmon.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }

    private val _metrics = MutableLiveData<String>().apply {
        value = "This is metrics? NO THIS IS SPARTA!"
    }

    val text: LiveData<String> = _text
    val metrics: LiveData<String> = _metrics
}