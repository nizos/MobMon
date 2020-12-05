package com.example.mobmon.Widgets

import android.util.Log
import android.widget.ProgressBar

class Gauge (val sentName:String) : Widget(){
    override val name = sentName
    override var widgetColor = "#FFFFFFFF" // White
    override var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    override fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?){
        dataValues = sentValues
    }
    //TODO: Update values from widgethandler instead?
    override var progressDrawable: ProgressBar
        get() { return progressDrawable }
        set(sentDrawable) { progressDrawable.progress = 50}
}