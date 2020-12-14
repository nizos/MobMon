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
import com.google.android.material.card.MaterialCardView

object WidgetController {
    var widgetList = mutableListOf<Widget>()
    var widgetListSize = MutableLiveData<Int>()
    var mContext: DashboardActivity? = null
    val cardList: MutableList<MaterialCardView> = mutableListOf()

    fun setDaashBoardActivity(activity: DashboardActivity){
        mContext = activity
    }

    fun addWidget(name: String, widgetType: String){
        if(mContext == null){
            Log.e("Widgetmanager","Error creating widget")
            return
        }
        when(widgetType){
            "Gauge" -> {
                widgetList.add(Gauge(name))
                if(name == "Ambient Light")
                    mContext!!.addLightSensorCard()
                else
                    mContext!!.addCard(name)
            }
            else -> {
                Log.e("Widgetmanager","Error creating widget")
            }
        }
    }

    fun removeCard(view: View) {
        val position = cardList.indexOf(view)

        if(position >= 0) {
            cardList.removeAt(position)
            widgetList.removeAt(position)
        }
    }
}