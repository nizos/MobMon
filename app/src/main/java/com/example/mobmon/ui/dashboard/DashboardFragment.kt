package com.example.mobmon.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R
import com.example.mobmon.Widgets.*
import android.os.Looper
import android.util.Log
import com.example.mobmon.data.WidgetData
// import com.example.mobmon.data.connect

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    val mainHandler = Handler(Looper.getMainLooper())
    var interval: Long = 1000

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var lineData: MutableMap<String, MutableMap<String, String>> = mutableMapOf<String, MutableMap<String, String>>()
        lineData.put("First Key", mutableMapOf(Pair("Second key", "Value")))
        var widgetList = mutableListOf<Widget>()
        widgetList.add(Line("Line"))
        widgetList.forEach {
            it.updateData(lineData)
            it.printDataValues()
        }

        //pass context
        var widgetDataHandler = WidgetData(getActivity())

        // TODO: Add this to WidgetData class
        mainHandler.post(object : Runnable {
            override fun run() {
                widgetDataHandler.update()
                mainHandler.postDelayed(this, interval)
                val dashBoardText = root.findViewById<TextView>(R.id.text_dashboard)
                dashBoardText.text = widgetDataHandler.getDataResponse()
            }
        })

        return root
    }
}
