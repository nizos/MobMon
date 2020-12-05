package com.example.mobmon.Widgets

import android.util.Log

abstract class Widget {
    open val name : String = ""
    open var widgetColor : String = ""
    open var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    open fun printDataValues(){}
    abstract fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?)
}

