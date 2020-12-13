package com.example.mobmon.ui.widgets

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mobmon.MainActivity
import com.example.mobmon.R

class WidgetsActivity : MainActivity() {
    private val tag = "mobmon"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_widgets, frameLayout)
        Log.i(tag, "Widgets Activity")
    }
}