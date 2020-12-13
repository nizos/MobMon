package com.example.mobmon.ui.widgets

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.WidgetController

class WidgetsActivity : MainActivity() {
    private val tag = "mobmon"
    var keyStringArray: MutableList<String> = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_widgets, frameLayout)
        Log.i(tag, "Widgets Activity")

        var listViewOfWidgets = rootView.findViewById<ListView>(R.id.widget_list)
        listViewOfWidgets.adapter = ArrayAdapter<String>(this, R.layout.listrow, keyStringArray)
        //TODO: metricsData.value can be null
        //TODO: add observer on abstractWidgetlist and its size instead?
        MainController.metricsData.observe(this, Observer {
            keyStringArray.clear()
            MainController.metricsData.value?.forEach { keyStringArray.add(it.key) }
            Log.i("Widgets", "${keyStringArray}")
        });
        /*
        var intent: Intent = Intent(this,WidgetController::class.java).apply {
            putExtra()
        }
        */
        listViewOfWidgets.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,keyStringArray[position], Toast.LENGTH_SHORT).show()
            WidgetController.addWidget(keyStringArray[position],"Gauge")
            //TODO: Navigate to specified widget settings and add to abstract classlist(widgetlist), might need string from communication
        }

    }
}
