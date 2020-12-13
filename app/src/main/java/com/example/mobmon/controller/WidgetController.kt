package com.example.mobmon.controller

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobmon.R
import com.example.mobmon.Widgets.Gauge
import com.example.mobmon.Widgets.Widget
import com.example.mobmon.ui.dashboard.DashboardActivity

object WidgetController {
    var widgetList = mutableListOf<Widget>()
    var widgetListSize = MutableLiveData<Int>()
    var mContext: Context? = null as DashboardActivity

    fun setDaashBoardActivity(activity: DashboardActivity){
        mContext = activity
    }

    fun addWidget(name: String,widgetType: String){
        when(widgetType){
            "Gauge" -> {
                widgetList.add(Gauge(name))
                mContext.addCard()
                println("yoo")
            }
            else -> {
                Log.e("Widgetmanager","Error creating widget")
            }
        }
    }
}