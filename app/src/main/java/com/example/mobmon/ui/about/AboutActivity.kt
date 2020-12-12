package com.example.mobmon.ui.about

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mobmon.MainActivity
import com.example.mobmon.R

class AboutActivity : MainActivity() {
    private val tag = "mobmon"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_about, frameLayout)
        Log.i(tag, "About Activity")
    }
}
