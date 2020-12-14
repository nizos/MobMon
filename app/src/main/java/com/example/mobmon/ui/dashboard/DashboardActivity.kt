package com.example.mobmon.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.room.*
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
import com.example.mobmon.data.Widget
import com.google.android.material.card.MaterialCardView
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.WidgetController
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : MainActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val tag = "mobmon"
    private lateinit var gestureDetector: GestureDetector
//    private lateinit var db: AppDatabase

    private lateinit var dashBoardLayout: DraggableCoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        WidgetController.setDaashBoardActivity(this)
        dashBoardLayout = rootView.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout

        MainController.metricsData.observe(this, Observer {
            for(i in 0 until WidgetController.widgetList.count()){
                val specifiedWidgetMap = it.get(WidgetController.widgetList[i].name)?.toMutableMap()
                WidgetController.widgetList[i].updateData(specifiedWidgetMap)
//                Log.i("Dashboard","$specifiedWidgetMap")
                updateCardVisuals(WidgetController.widgetList[i].name,i)
//        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "mobmon").build()
//        var widgets: List<Widget> = db.widgetDao().getAll()
//        for (widget in widgets) {
//            var dashBoardLayout = this.findViewById(R.id.parentCoordinatorLayout) as DraggableCoordinatorLayout
//            val card: MaterialCardView = layoutInflater.inflate(R.layout.fragment_widget, null) as MaterialCardView
//            card.x = widget.widgetPositionX!!
//            card.y = widget.widgetPositionY!!
//            dashBoardLayout.addDraggableChild(card)
//        }

//        gestureDetector = GestureDetector(this, this)
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
//        draggableCard1.setOnTouchListener(this)
//        draggableCard2.setOnTouchListener(this)
//        draggableCard3.setOnTouchListener(this)
//        draggableCard4.setOnTouchListener(this)

        draggableCard1.setOnLongClickListener {
            draggableCard1.isChecked = !draggableCard1.isChecked
            draggableCard1.isSelected = !draggableCard1.isSelected
            Log.i(tag, "draggableCard1 Clicked!")
            true
        }
        WidgetController.cardList[iteration].refreshDrawableState()
    }

    override fun onPause() {
        super.onPause()

        for(i in 0 until WidgetController.cardList.count()) {
            val addCard = WidgetController.cardList[i]
            val cardClass = WidgetController.widgetList[i]
            cardClass.posX = addCard.x
            cardClass.posY = addCard.y

            dashBoardLayout.removeView(addCard)
        draggableCard2.setOnLongClickListener {
            draggableCard2.isChecked = !draggableCard2.isChecked
            draggableCard2.isSelected = !draggableCard2.isSelected
            Log.i(tag, "draggableCard2 Clicked!")
            true
        }

    }

    override fun onResume() {
        super.onResume()
//        Log.i("mobmon/cards", "lateAdd has ${WidgetController.cardList.count()} cards to add. cards is ${WidgetController.cardList.hashCode()}")

        for(i in 0 until WidgetController.cardList.count()){
            val addCard = WidgetController.cardList[i]
//            val cardClass = WidgetController.widgetList[i]

            dashBoardLayout.addView(addCard)
            parentCoordinatorLayout.addDraggableChild(addCard)
        draggableCard3.setOnLongClickListener {
            draggableCard3.isChecked = !draggableCard3.isChecked
            draggableCard3.isSelected = !draggableCard3.isSelected
            Log.i(tag, "draggableCard3 Clicked!")
            true
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
        draggableCard4.setOnLongClickListener {
            draggableCard4.isChecked = !draggableCard4.isChecked
            draggableCard4.isSelected = !draggableCard4.isSelected
            Log.i(tag, "draggableCard4 Clicked!")
            true
        }
        card.addView(widget)
        WidgetController.cardList.add(card)
    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        gestureDetector.onTouchEvent(event)
//        return true
//    }
//
//    override fun onDown(e: MotionEvent?): Boolean {
//        Log.d(tag, "onDown: called.");
//        return false;
//    }
//
//    override fun onShowPress(e: MotionEvent?) {
//        Log.d(tag, "onShowPress: called.");
//    }
//
//    override fun onSingleTapUp(e: MotionEvent?): Boolean {
//        Log.d(tag, "onSingleTapUp: called.");
//        return false;
//    }
//
//    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
//        Log.d(tag, "onScroll: called.");
//        return false;
//    }
//
//    override fun onLongPress(e: MotionEvent?) {
//        Log.d(tag, "onLongPress: called.")
//
//        val builder = View.DragShadowBuilder(draggableCard1)
//        draggableCard1.startDrag(null, builder, null, 0)
//
//        builder.view.setOnDragListener(this)
//    }
//
//    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
//        Log.d(tag, "onFling: called.");
//        return false;
//    }
//
//    override fun onDrag(v: View?, event: DragEvent?): Boolean {
//        if (event != null) {
//            when (event.action) {
//                DragEvent.ACTION_DRAG_STARTED -> {
//                    // Log.d(tag, "onDrag: drag started.")
//                    return true
//                }
//                DragEvent.ACTION_DRAG_ENTERED -> {
//                    // Log.d(tag, "onDrag: drag entered.")
//                    return true
//                }
//                DragEvent.ACTION_DRAG_LOCATION -> {
//                    // Log.d(tag, "onDrag: current point: ( " + event.x.toString() + " , " + event.y.toString() + " )")
//                    return true
//                }
//                DragEvent.ACTION_DRAG_EXITED -> {
//                    // Log.d(tag, "onDrag: exited.")
//                    return true
//                }
//                DragEvent.ACTION_DROP -> {
//                    // Log.d(tag, "onDrag: dropped.")
//                    return true
//                }
//                DragEvent.ACTION_DRAG_ENDED -> {
//                    // Log.d(tag, "onDrag: ended.")
//                    return true
//                }
//                else -> Log.e(tag, "Unknown action type received by OnStartDragListener.")
//            }
//        }
//
//        return false
//    }
//
//    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
//        Log.d(tag, "onSingleTapConfirmed: called.");
//        return false;
//    }
//
//    override fun onDoubleTap(e: MotionEvent?): Boolean {
//        Log.d(tag, "onDoubleTap: called.");
//        return false;
//    }
//
//    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
//        Log.d(tag, "onDoubleTapEvent: called.");
//        return false;
//    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    }

}