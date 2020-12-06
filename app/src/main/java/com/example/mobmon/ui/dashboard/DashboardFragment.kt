package com.example.mobmon.ui.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mobmon.R
import com.example.mobmon.Widgets.*
import com.example.mobmon.data.WidgetData
import java.lang.NumberFormatException


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
        val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        showWidgetPopUpMenu(circleBar, root)
        val dashBoardText = root.findViewById<TextView>(R.id.text_dashboard)

        // TODO: Preferably add this to WidgetData class
        mainHandler.post(object : Runnable {
            override fun run() {
                widgetDataHandler.update()
                mainHandler.postDelayed(this, interval)
                //dashBoardText.text = widgetDataHandler.mapResponse.get("GPU usage").toString()
                var sourceStringList: String = ""
                setWidgetProperties(root,widgetDataHandler.mapResponse)
                /*
                widgetList.forEach {
                    //it.updateData(widgetDataHandler.mapResponse)
                    //it.printDataValues()
                }
                */
                widgetDataHandler.mapResponse.forEach { entry ->
                    sourceStringList += (entry.key.toString())
                }

                dashBoardText.text = sourceStringList
            }
        })
        return root
    }

    fun setWidgetProperties(root: View, mapResponse: MutableMap<String, MutableMap<String, String>>) {
        val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        var widgetText = root.findViewById<TextView>(R.id.progress_text)
        //circleBar.progress = mapResponse.get("GPU usage").
        Log.e("mapresponse", "${mapResponse}")
        var sb = StringBuilder()
        sb.append(mapResponse?.get("GPU usage")?.get("localizedSrcName").toString())
        sb.append("\n" + mapResponse?.get("GPU usage")?.get("localizedSrcUnits").toString())
        sb.append(" " + mapResponse?.get("GPU usage")?.get("data").toString())
        widgetText.text = sb
        try {
            val parseInt = mapResponse.get("GPU usage")?.get("data")?.toInt()
            if (parseInt != null) {
                circleBar.progress = parseInt
            }
        } catch (nfe: NumberFormatException) {
            Log.e("Invalid", "Invalid number")
        }

    }

    fun showWidgetPopUpMenu(sentProgressBar: ProgressBar, root: View) {
        val settingIcon = root.findViewById<ImageView>(R.id.widget_setting_button)

        settingIcon.setOnClickListener{
            root.findNavController().navigate(R.id.action_dashboard_to_widget)
        }

        sentProgressBar.setOnLongClickListener {
            settingIcon.visibility = View.VISIBLE
            //Creating the instance of PopupMenu
            val popup = PopupMenu(context, sentProgressBar)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->
                handleWidgetMenuChoice(sentProgressBar, item,root)
                true
            }
            //showing popup menu
            popup.show()
            //closing the setOnClickListener method
            return@setOnLongClickListener true
        }
    }

    //TODO: Add widget data as args to navgiation?
    fun handleWidgetMenuChoice(bar: ProgressBar, item: MenuItem, root: View) {
        when(item.title){
            "Configure" -> root.findNavController().navigate(R.id.action_dashboard_to_widget)
            "Remove" -> (bar.getParent() as ViewGroup).removeAllViews()
            "Cancel" -> Toast.makeText(context, "Cancel action", Toast.LENGTH_SHORT).show()
            else -> {
                println("Nothing was selected")
            }
        }
    }
}