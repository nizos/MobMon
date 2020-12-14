package com.example.mobmon.data

import androidx.lifecycle.LiveData

class WidgetRepository(private val widgetDao: WidgetDao) {
    val readAllData: LiveData<List<Widget>> = widgetDao.readAllData()

    suspend fun addWidget(widget: Widget) {
        widgetDao.addWidget(widget)
    }
}