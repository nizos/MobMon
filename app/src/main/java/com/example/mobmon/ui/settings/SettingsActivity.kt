package com.example.mobmon.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mobmon.MainActivity
import com.example.mobmon.R

class SettingsActivity : MainActivity() {
    private val tag = "mobmon"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_settings, frameLayout)
        Log.i(tag, "Settings Activity")
    }
}
