
package com.example.mobmon.Widgets

import com.google.android.material.card.MaterialCardView

class Line (sentName: String) : Widget(){
    override var name = sentName
    override var widgetColor = "#FFFFFFFF" //White
    override var dataValues : MutableMap<String, String>? = null
    override lateinit var widgetCard : MaterialCardView
    override fun updateData(sentValues: MutableMap<String, String>?){
        dataValues = sentValues
    }
}