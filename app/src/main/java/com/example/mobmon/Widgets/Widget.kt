package com.example.mobmon.Widgets

import android.util.Log
import android.widget.ProgressBar

abstract class Widget {
    open val name : String = ""
    open var widgetColor : String = ""
    open var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    abstract var progressDrawable : ProgressBar
    open fun printDataValues(){}
    abstract fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?)
}

