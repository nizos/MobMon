package com.example.mobmon.controller

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobmon.widget.Gauge
import com.example.mobmon.widget.Widget
import com.example.mobmon.ui.dashboard.DashboardActivity
import com.google.android.material.card.MaterialCardView

object WidgetController {
    private var tag : String = "mobmon"
    var widgetList = mutableListOf<Widget>()
    var widgetListSize = MutableLiveData<Int>()
    var mContext: DashboardActivity? = null
    val cardList: MutableList<MaterialCardView> = mutableListOf()

    fun setDashBoardActivity(activity: DashboardActivity){
        mContext = activity
    }

    fun addWidget(name: String, widgetType: String){
        if(mContext == null){
            Log.e(tag,"Error creating widget")
            return
        }
        when(widgetType){
            "Gauge" -> {
                widgetList.add(Gauge(name))
                if (name == "Ambient Light") {
                    mContext!!.addLightSensorCard()
                } else {
                    mContext!!.addCard(name)
                }
            } else -> {
                Log.e(tag,"Error creating widget")
            }
        }
    }

    fun removeCard(view: View) {
        val position = cardList.indexOf(view)
        if (position >= 0) {
            cardList.removeAt(position)
            widgetList.removeAt(position)
        }
    }
}
