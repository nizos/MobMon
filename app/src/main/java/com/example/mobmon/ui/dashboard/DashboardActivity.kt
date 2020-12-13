package com.example.mobmon.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.mobmon.DraggableCoordinatorLayout
import com.example.mobmon.MainActivity
import com.example.mobmon.R
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

        MainController.metricsData.observe(this, Observer {
            for(i in 0 until WidgetController.widgetList.count()){
                Log.d("mobmon/observer", "Running observer! (onStart)")
                var specifiedWidgetMap = it.get(WidgetController.widgetList[i].name)?.toMutableMap()
                WidgetController.widgetList[i].updateData(specifiedWidgetMap)
                Log.i("Dashboard","$specifiedWidgetMap")
            }
        })
    }

    override fun onPause() {
        super.onPause()

        for(i in 0 until WidgetController.cardList.count()) {
            val addCard = WidgetController.cardList[i]
            val cardClass = WidgetController.widgetList[i]

            val posArr = IntArray(2)
            addCard.getLocationOnScreen(posArr)
            cardClass.posArr = posArr

            dashBoardLayout.removeView(addCard)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("mobmon/cards", "lateAdd has ${WidgetController.cardList.count()} cards to add. cards is ${WidgetController.cardList.hashCode()}")

        for(i in 0 until WidgetController.cardList.count()){
            val addCard = WidgetController.cardList[i]
//            val cardClass = WidgetController.widgetList[i]
            dashBoardLayout.addView(addCard)

            parentCoordinatorLayout.addDraggableChild(addCard)

        }
    }

    fun addCard(){
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val item: TextView? = widget?.findViewById(R.id.progress_text)
        item?.text = "YO it works"
        card.addView(widget)

        if(!WidgetController.cardList.add(card)) {
            Log.e("mobmon/cards", "FAILED TO ADD CARD")
        }
    }
}
