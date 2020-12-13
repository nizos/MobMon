package com.example.mobmon.Widgets

import android.util.Log

abstract class Widget {
    open val name : String = ""
    open var widgetColor : String = ""
    open var dataValues : MutableMap<String, String>? = null
    abstract fun updateData(sentValues: MutableMap<String, String>?)
}

