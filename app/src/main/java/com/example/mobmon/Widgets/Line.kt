package com.example.mobmon.Widgets
import android.util.Log

class Line (val sentName:String) : Widget(){
    override val name = sentName
    override var widgetColor = "#FFFFFFFF" //White
    override var dataValues : MutableMap<String, MutableMap<String, String>>? = null
    override fun updateData(sentValues: MutableMap<String, MutableMap<String, String>>?){
        dataValues = sentValues
    }
}