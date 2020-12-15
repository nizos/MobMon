package com.example.mobmon.ui.dashboard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.mobmon.DraggableCoordinatorLayout
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.WidgetController
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlin.math.roundToInt

class DashboardActivity : MainActivity(), SensorEventListener {
    private val tag = "mobmon"
    private var mSensors: Sensor? = null
    private var maxSensorValue: Float = 0f
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private lateinit var dashBoardLayout: DraggableCoordinatorLayout
    private lateinit var mSensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        WidgetController.setDashBoardActivity(this)
        dashBoardLayout = rootView.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)


        if (mSensors != null) {
            maxSensorValue = mSensors!!.maximumRange
            Log.v("Sensors", "Success")
        } else {
            // Failure!
            Log.v("Sensors", "No sensor found")
        }

        MainController.metricsData.observe(this, Observer {
            for (i in 0 until WidgetController.widgetList.count()) {
                val specifiedWidgetMap = it.get(WidgetController.widgetList[i].name)?.toMutableMap()
                WidgetController.widgetList[i].updateData(specifiedWidgetMap)
//                Log.i("Dashboard","$specifiedWidgetMap")
                updateCardVisuals(WidgetController.widgetList[i].name, i)
            }
        })
    }

    fun updateCardVisuals(name: String, iteration: Int){
        if(WidgetController.cardList.size == 0) return
        val widget = WidgetController.cardList[iteration].getChildAt(0) as RelativeLayout
        val progBar = widget.findViewById(R.id.progress_bar) as ProgressBar
        val textView = widget.findViewById(R.id.progress_text) as TextView
        val progressValue: String? = MainController.metricsData.value?.get(WidgetController.widgetList[iteration].name)?.get("data")?.substringBefore(".")
        if (progressValue != null) {
            textView?.text = "%s \n %s %s".format(name, progressValue.toString(),
                    MainController.metricsData.value?.get(name)?.getValue("srcUnits").toString())
            progBar.progress = progressValue.toInt()
        }
        WidgetController.cardList[iteration].refreshDrawableState()
    }

    //Only here because its an abstract function <- Isn't that just wonderful :) ?
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val sVal = p0!!.values[0]

        if (p0.sensor.type == Sensor.TYPE_LIGHT){
            //Update ambient light widget visual values
            for(i in 0 until WidgetController.cardList.count()){
                if(WidgetController.widgetList[i].name == "Ambient Light"){
                    val widget = WidgetController.cardList[i].getChildAt(0) as RelativeLayout
                    val textView = widget.findViewById(R.id.progress_text) as TextView
                    val progBar = widget.findViewById(R.id.progress_bar) as ProgressBar
                    textView?.text = "%s \n %s %s".format("Ambient Light", sVal, "Lum")
                    progBar.progress = sVal.toInt()
                    WidgetController.cardList[i].refreshDrawableState()
                }

            }
//            Log.i("Light","Light changed to ${sVal} luminosity.")
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
        for(i in 0 until WidgetController.cardList.count()) {
            val currentCard = WidgetController.cardList[i]
            val currentWidget = WidgetController.widgetList[i]
            currentWidget.posX = currentCard.x.toFloat()
            currentWidget.posY = currentCard.y.toFloat()
            dashBoardLayout.removeView(currentCard)
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_FASTEST)

        for (i in 0 until WidgetController.cardList.count()) {
            val currentCard = WidgetController.cardList[i]
            val currentWidget = WidgetController.widgetList[i]

            val factor: Float = dashBoardLayout.context.resources.displayMetrics.density
            val size: Int = 150 * factor.toInt()

            val params = RelativeLayout.LayoutParams(size, size)
            params.leftMargin = currentWidget.posX.toInt()
            params.topMargin = currentWidget.posY.toInt()

            dashBoardLayout.addView(currentCard, params)

            parentCoordinatorLayout.addDraggableChild(currentCard)
            parentCoordinatorLayout.setViewDragListener(object :
                    DraggableCoordinatorLayout.ViewDragListener {

                override fun onViewCaptured(view: View, i: Int) {
                    currentCard.isDragged = true
                }

                override fun onViewReleased(view: View, v: Float, v1: Float) {
                    currentCard.isDragged = false
                }
            })
        }
    }

    fun addLightSensorCard(){
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null, false)
        val progBar = widget?.findViewById(R.id.progress_bar) as ProgressBar
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            progBar.max = maxSensorValue.roundToInt() ?: 100
            progBar.min = 5000
        }
        card.addView(widget)
        widget.id = View.generateViewId()
        parentCoordinatorLayout.addDraggableChild(card)
        val settingsIcon = ContextCompat.getDrawable(card.context, R.drawable.icon_settings)
        card.checkedIcon = settingsIcon
        card.setOnLongClickListener {
            card.isChecked = !card.isChecked
            card.isSelected = !card.isSelected
            true
        }
        card.setOnClickListener {
            if (card.isChecked) {
                val popup = PopupMenu(this, card)
                popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    handleWidgetMenuChoice(progBar, item, card)
                    true
                }
                popup.show()
            }
            true
        }
        WidgetController.cardList.add(card)
    }

    fun addCard(name: String) {
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        card.id = View.generateViewId()
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null, false)
        widget.id = View.generateViewId()
        val progBar = widget?.findViewById(R.id.progress_bar) as ProgressBar
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            progBar.max = MainController.metricsData.value?.get(name)?.getValue("maxLimit")?.toInt()
                    ?: 100
            progBar.min = 0
        }
        card.addView(widget)
        parentCoordinatorLayout.addDraggableChild(card)
        val settingsIcon = ContextCompat.getDrawable(card.context, R.drawable.icon_settings)
        card.checkedIcon = settingsIcon
        card.setOnLongClickListener {
            card.isChecked = !card.isChecked
            card.isSelected = !card.isSelected
            true
        }
        card.setOnClickListener {
            if (card.isChecked) {
                val popup = PopupMenu(this, card)
                popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    handleWidgetMenuChoice(progBar, item, card)
                    true
                }
                popup.show()
            }
            true
        }
        parentCoordinatorLayout.setViewDragListener(object :
                DraggableCoordinatorLayout.ViewDragListener {
            override fun onViewCaptured(view: View, i: Int) {
                card.isDragged = true
            }

            override fun onViewReleased(view: View, v: Float, v1: Float) {
                card.isDragged = false
            }
        })
        WidgetController.cardList.add(card)
    }

    fun handleWidgetMenuChoice(bar: ProgressBar, item: MenuItem, rootView: View) {
        when(item.title){
            "Remove" -> {
                (bar.parent as ViewGroup).removeAllViews()
                rootView.visibility = View.GONE
                WidgetController.removeCard(rootView)
                dashBoardLayout.removeView(rootView)
            }
            "Cancel" -> Toast.makeText(this, "Cancel action", Toast.LENGTH_SHORT).show()
            else -> {
                println("Nothing was selected")
            }
        }
    }
}