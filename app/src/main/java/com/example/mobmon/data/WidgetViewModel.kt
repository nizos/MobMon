package com.example.mobmon.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Widget>>
    private val repository: WidgetRepository

    init {
        val widgetDao = WidgetDatabase.getDatabase(application).widgetDao()
        repository = WidgetRepository(widgetDao)
        readAllData = repository.readAllData
    }

    fun addWidget(widget: Widget) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWidget(widget)
        }
    }
}