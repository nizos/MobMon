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
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : MainActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val tag = "mobmon"
    var widgetList = mutableListOf<Widget>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        Log.i(tag, "Dashboard Activity")

        //Inflates a card layout and puts the widget inside, afterwards adding to dashboard
        var dashBoardLayout = rootView.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val item: TextView? = widget?.findViewById(R.id.progress_text)
        item?.text = "YO it works"
        card.addView(widget)
        dashBoardLayout.addView(card)

        widgetList.add(Gauge("GPU usage"))
        widgetList.add(Gauge("Core clock"))

        MainController.metricsData.observe(this, Observer {
            for(i in 0 until widgetList.count()){
                var specifiedWidgetMap = MainController.metricsData.value?.get(widgetList[i].name)?.toMutableMap()
                widgetList[i].updateData(specifiedWidgetMap)
                Log.i("Dashboard","$specifiedWidgetMap")
            }
        })

        parentCoordinatorLayout.addDraggableChild(draggableCard1)
        parentCoordinatorLayout.addDraggableChild(draggableCard2)
        parentCoordinatorLayout.addDraggableChild(draggableCard3)
        parentCoordinatorLayout.addDraggableChild(draggableCard4)

        parentCoordinatorLayout.setViewDragListener(object :
                DraggableCoordinatorLayout.ViewDragListener {
            override fun onViewCaptured(view: View, i: Int) {

                when (view.id) {
                    R.id.draggableCard1 -> draggableCard1.isDragged = true
                    R.id.draggableCard2 -> draggableCard2.isDragged = true
                    R.id.draggableCard3 -> draggableCard3.isDragged = true
                    R.id.draggableCard4 -> draggableCard4.isDragged = true
                }
            }

            override fun onViewReleased(view: View, v: Float, v1: Float) {

                when (view.id) {
                    R.id.draggableCard1 -> draggableCard1.isDragged = false
                    R.id.draggableCard2 -> draggableCard2.isDragged = false
                    R.id.draggableCard3 -> draggableCard3.isDragged = false
                    R.id.draggableCard4 -> draggableCard4.isDragged = false
                }
            }
        })

        draggableCard1.setOnLongClickListener {
            draggableCard1.isChecked = !draggableCard1.isChecked
            Log.i(tag, "draggableCard1 Clicked!")
            true
        }

        draggableCard2.setOnLongClickListener {
            draggableCard2.isChecked = !draggableCard2.isChecked
            Log.i(tag, "draggableCard2 Clicked!")
            true
        }

        draggableCard3.setOnLongClickListener {
            draggableCard3.isChecked = !draggableCard3.isChecked
            Log.i(tag, "draggableCard3 Clicked!")
            true
        }

        draggableCard4.setOnLongClickListener {
            draggableCard4.isChecked = !draggableCard4.isChecked
            Log.i(tag, "draggableCard4 Clicked!")
            true
        }
    }
}
