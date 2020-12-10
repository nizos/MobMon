package com.example.mobmon.ui.dashboard

import android.os.Bundle
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
import com.example.mobmon.controller.MainController


// import com.example.mobmon.data.connect

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)

        var widgetList = mutableListOf<Widget>()
        widgetList.add(Gauge("GPU usage"))
        widgetList.add(Gauge("Core clock"))
        MainController.metricsData.observe(viewLifecycleOwner, Observer {
            for(i in 0 until widgetList.count()){
                var specifiedWidgetMap = MainController.metricsData.value?.get(widgetList[i].name)?.toMutableMap()
                widgetList[i].updateData(specifiedWidgetMap)
                Log.i("Dashboard","$specifiedWidgetMap")
            }
        })

        //val circleBar = root.findViewById<ProgressBar>(R.id.progress_bar)
        //showWidgetPopUpMenu(circleBar, root)
        return root
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

    //TODO: Add widget data as args to navgiation? In order configure the specific widget
    fun handleWidgetMenuChoice(bar: ProgressBar, item: MenuItem, root: View) {
        when(item.title){
            "Configure" -> {
                root.findNavController().navigate(R.id.action_dashboard_to_widget)
            }
            "Remove" -> (bar.getParent() as ViewGroup).removeAllViews() //TODO: Remove associated widget class
            "Cancel" -> Toast.makeText(context, "Cancel action", Toast.LENGTH_SHORT).show()
            else -> {
                println("Nothing was selected")
            }
        }
    }
}