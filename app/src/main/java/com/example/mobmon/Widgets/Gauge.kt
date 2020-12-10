package com.example.mobmon.Widgets

import android.util.Log
import android.widget.ProgressBar

class Gauge () : Widget(){
    override var name = ""
    override var widgetColor = "#FFFFFFFF" // White
    override var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    override fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?){
        dataValues = sentValues
        name = sentValues?.get("localizedSrcName").toString()
    }
}