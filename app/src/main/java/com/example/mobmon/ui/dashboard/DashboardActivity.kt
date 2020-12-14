package com.example.mobmon.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.room.*
import com.example.mobmon.DraggableCoordinatorLayout
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.data.Widget
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : MainActivity() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var coordinatorLayout: DraggableCoordinatorLayout
    private val tag = "mobmon"
    private lateinit var gestureDetector: GestureDetector
//    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_dashboard, frameLayout)
        Log.i(tag, "Dashboard Activity")

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

            override fun onViewReleased(view: View, v: Float, v1: Float) {

                when (view.id) {
                    R.id.draggableCard1 -> draggableCard1.isDragged = false
                    R.id.draggableCard2 -> draggableCard2.isDragged = false
                    R.id.draggableCard3 -> draggableCard3.isDragged = false
                    R.id.draggableCard4 -> draggableCard4.isDragged = false
                }
            }
        })

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

        draggableCard2.setOnLongClickListener {
            draggableCard2.isChecked = !draggableCard2.isChecked
            draggableCard2.isSelected = !draggableCard2.isSelected
            Log.i(tag, "draggableCard2 Clicked!")
            true
        }

        draggableCard3.setOnLongClickListener {
            draggableCard3.isChecked = !draggableCard3.isChecked
            draggableCard3.isSelected = !draggableCard3.isSelected
            Log.i(tag, "draggableCard3 Clicked!")
            true
        }

        draggableCard4.setOnLongClickListener {
            draggableCard4.isChecked = !draggableCard4.isChecked
            draggableCard4.isSelected = !draggableCard4.isSelected
            Log.i(tag, "draggableCard4 Clicked!")
            true
        }
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
