package com.example.mobmon.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R
import com.example.mobmon.Widgets.*
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
        val dataResponse = widgetDataHandler.mapResponse
        widgetList[0].dataValues = dataResponse
        //TODO: Assign progressbar to each individual widget class
        //-------------Stack overflow----------------------
        //widgetList[0].progressDrawable = root.findViewById<ProgressBar>(R.id.progress_bar)
        //------------------------------------------------

        /*TODO:Assign progress and text according to mutablemap -> widgetDataHandler.getDataResponse()
        val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        circleBar?.setOnClickListener(){
            circleBar.progress += 3
        }
        */
        popUpMenu(root)

        val dashBoardText = root.findViewById<TextView>(R.id.text_dashboard)
        // TODO: Preferably add this to WidgetData class
        mainHandler.post(object : Runnable {
            override fun run() {
                widgetDataHandler.update()
                mainHandler.postDelayed(this, interval)
                //dashBoardText.text = widgetDataHandler.mapResponse.get("GPU usage").toString()
                var sourceStringList: String = ""

                widgetList.forEach {
                    //it.updateData(widgetDataHandler.mapResponse)
                    //it.printDataValues()
                }
                widgetDataHandler.mapResponse.forEach { entry ->
                    sourceStringList += (entry.key.toString())
                }

                dashBoardText.text = sourceStringList
            }
        })
        return root
    }
    
    fun popUpMenu(root: View) {
        val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        //TODO: Hold event instead
        circleBar.setOnClickListener(View.OnClickListener {
            //Creating the instance of PopupMenu
            val popup = PopupMenu(context, circleBar)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->
                Toast.makeText(context, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                //TODO: Switch case for item
                true
            }
            popup.show() //showing popup menu
        }) //closing the setOnClickListener method
    }
}