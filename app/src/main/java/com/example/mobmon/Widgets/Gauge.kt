package com.example.mobmon.Widgets

class Gauge (sentName: String) : Widget(){
    override var name = sentName
    override var widgetColor = "#FFFFFFFF" // White
    override var dataValues : MutableMap<String, String>? = null
    override fun updateData(sentValues: MutableMap<String, String>?) {
        dataValues = sentValues
    }
}