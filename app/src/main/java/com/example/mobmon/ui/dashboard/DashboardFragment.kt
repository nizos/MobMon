package com.example.mobmon.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.DraggableCoordinatorLayout
import androidx.navigation.findNavController
import com.example.mobmon.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_dashboard.*
import com.example.mobmon.Widgets.*
import com.example.mobmon.controller.MainController
import com.example.mobmon.ui.widgets.WidgetsFragment
import com.google.android.material.card.MaterialCardView


// import com.example.mobmon.data.connect

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val TAG = "mobmon"
    var widgetList = mutableListOf<Widget>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        //Inflates a card layout and puts the widget inside, afterwards adding to dashboard
        var dashBoardLayout = root.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val item: TextView? = widget?.findViewById(R.id.progress_text)
        item?.text = "YO it works"
        card.addView(widget)
        dashBoardLayout.addView(card)

        widgetList.add(Gauge("GPU usage"))
        widgetList.add(Gauge("Core clock"))

        MainController.metricsData.observe(viewLifecycleOwner, Observer {
            for(i in 0 until widgetList.count()){
                var specifiedWidgetMap = MainController.metricsData.value?.get(widgetList[i].name)?.toMutableMap()
                widgetList[i].updateData(specifiedWidgetMap)
                Log.i("Dashboard","$specifiedWidgetMap")
            }
        })
        /*
        for(i in 0 until circleBar.childCount){
            val nextChild = circleBar.getChildAt(i)

            //nextChild.javaClass.name.contains("Progressbar",true)
            if(nextChild.javaClass.name.contains("Progressbar",true)){
                Log.i("Dashboarad", nextChild.javaClass.name.toString())
                var progressBar: ProgressBar = nextChild as ProgressBar
                progressBar.progress
                println("yo")
            }
        }
        */
        return root
    }
    /*
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
    */
}