package com.example.mobmon.ui.widget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WidgetViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is widgets Fragment"
    }
    val text: LiveData<String> = _text
}