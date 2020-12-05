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
import android.widget.ProgressBar
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

            var widgetList = mutableListOf<Widget>()
            widgetList.add(Gauge("Gauge"))
            //pass context
            var widgetDataHandler = WidgetData(getActivity())
            //TODO: Assign progressbar to each individual widget class
            //-------------Stack overflow----------------------
            //widgetList[0].progressDrawable = root.findViewById<ProgressBar>(R.id.progress_bar)
            //------------------------------------------------
            val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
            //TODO:
            circleBar?.setOnClickListener{
                circleBar.progress += 3
            }

            // TODO: Preferably add this to WidgetData class
            mainHandler.post(object : Runnable {
                override fun run() {
                    widgetDataHandler.update()
                    mainHandler.postDelayed(this, interval)
                    val dashBoardText = root.findViewById<TextView>(R.id.text_dashboard)
                    dashBoardText.text = widgetDataHandler.getDataResponse()
                    widgetList.forEach {
                        it.updateData(widgetDataHandler.mapResponse)
                        it.printDataValues()
                    }
                }
            })
            return root
    }
}
