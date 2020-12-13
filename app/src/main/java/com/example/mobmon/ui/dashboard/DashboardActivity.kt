package com.example.mobmon.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.mobmon.DraggableCoordinatorLayout
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.Widgets.Gauge
import com.example.mobmon.Widgets.Widget
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.WidgetController
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : MainActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val tag = "mobmon"
    private lateinit var dashBoardLayout: DraggableCoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        WidgetController.setDaashBoardActivity(this)
        dashBoardLayout = rootView.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout


        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val item: TextView? = widget?.findViewById(R.id.progress_text)
        item?.text = "YO it works"
        card.addView(widget)
        dashBoardLayout.addView(card)
        parentCoordinatorLayout.addDraggableChild(card)

        MainController.metricsData.observe(this, Observer {
            for(i in 0 until WidgetController.widgetList.count()){
                var specifiedWidgetMap = MainController.metricsData.value?.get(WidgetController.widgetList[i].name)?.toMutableMap()
                WidgetController.widgetList[i].updateData(specifiedWidgetMap)
                Log.i("Dashboard","$specifiedWidgetMap")
            }
        })
    }

    fun addCard(){
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val item: TextView? = widget?.findViewById(R.id.progress_text)
        item?.text = "YO it works"
        card.addView(widget)
        dashBoardLayout.addView(card)
        parentCoordinatorLayout.addDraggableChild(card)
    }
}
