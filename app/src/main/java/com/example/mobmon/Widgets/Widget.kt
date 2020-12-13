package com.example.mobmon.Widgets

import android.util.Log
import com.google.android.material.card.MaterialCardView

abstract class Widget {
    open val name : String = ""
    open var widgetColor : String = ""
    open var dataValues : MutableMap<String, String>? = null
    open lateinit var widgetCard : MaterialCardView
    abstract fun updateData(sentValues: MutableMap<String, String>?)

}

