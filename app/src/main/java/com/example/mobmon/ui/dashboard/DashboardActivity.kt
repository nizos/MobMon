package com.example.mobmon.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
//import andr oid.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
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
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val tag = "mobmon"
    private lateinit var dashBoardLayout: DraggableCoordinatorLayout
    private lateinit var mSensorManager: SensorManager
    private var mSensors: Sensor? = null
    private var maxSensorValue: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        WidgetController.setDaashBoardActivity(this)
        dashBoardLayout = rootView.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensors = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (mSensors != null) {
            maxSensorValue = mSensors!!.maximumRange
            Log.v("Sensors","Success")
        } else {
            // Failure!
            Log.v("Sensors","No sensor found")
        }

        MainController.metricsData.observe(this, Observer {
            for(i in 0 until WidgetController.widgetList.count()){
                val specifiedWidgetMap = it.get(WidgetController.widgetList[i].name)?.toMutableMap()
                WidgetController.widgetList[i].updateData(specifiedWidgetMap)
//                Log.i("Dashboard","$specifiedWidgetMap")
                updateCardVisuals(WidgetController.widgetList[i].name,i)
            }
        })
    }

    fun updateCardVisuals(name: String,iteration: Int){
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

    //Only here because its an abstract function
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val sVal = p0!!.values[0]

        if (p0.sensor.type == Sensor.TYPE_LIGHT){
            val newValue: Int = (255f * sVal / maxSensorValue).roundToInt()
            //Ambient Light
            Log.i("Light","Light changed to ${sVal} luminosity.")
        }
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)

        for(i in 0 until WidgetController.cardList.count()) {
            val addCard = WidgetController.cardList[i]
            val cardClass = WidgetController.widgetList[i]
            cardClass.posX = addCard.x
            cardClass.posY = addCard.y

            dashBoardLayout.removeView(addCard)
        }

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mSensors, SensorManager.SENSOR_DELAY_FASTEST)

        for(i in 0 until WidgetController.cardList.count()){
            val addCard = WidgetController.cardList[i]
//            val cardClass = WidgetController.widgetList[i]

            dashBoardLayout.addView(addCard)
            parentCoordinatorLayout.addDraggableChild(addCard)
        }
    }

    fun addCard(name: String){
        val card: MaterialCardView = layoutInflater.inflate(R.layout.material_card_layout, null) as MaterialCardView
        val widget: View = layoutInflater.inflate(R.layout.circle_widget_layout, null,false)
        val progBar = widget?.findViewById(R.id.progress_bar) as ProgressBar
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            progBar.max = MainController.metricsData.value?.get(name)?.getValue("maxLimit")?.toInt()
                    ?: 100
            progBar.min = 0
        }
        card.addView(widget)
        WidgetController.cardList.add(card)
    }
}