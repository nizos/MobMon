package com.example.mobmon.Widgets

abstract class Widget {
    open val name : String = ""
    open var widgetColor : String = ""
    open var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    abstract fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?)
}

