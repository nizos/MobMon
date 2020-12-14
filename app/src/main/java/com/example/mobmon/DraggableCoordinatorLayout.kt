package com.example.mobmon

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.customview.widget.ViewDragHelper
import kotlinx.android.synthetic.main.activity_dashboard.view.*
import java.util.*

class DraggableCoordinatorLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null)
    : CoordinatorLayout(context!!, attrs) {

    private val tag = "mobmon"

    /** A listener to use when a child view is dragged  */
    interface ViewDragListener {
        fun onViewCaptured(view: View, i: Int)
        fun onViewReleased(view: View, v: Float, v1: Float)
    }

    interface ViewScaleListener {
        fun onScale(detector: ScaleGestureDetector)
    }

    private val viewDragHelper: ViewDragHelper
    private val draggableChildren: MutableList<View> = ArrayList()
    private var viewDragListener: ViewDragListener? = null
    private var viewScaleListener:ViewScaleListener? = null

    fun addDraggableChild(child: View) {
        require(!(child.parent !== this))
        draggableChildren.add(child)
    }

    fun removeDraggableChild(child: View) {
        require(!(child.parent !== this))
        draggableChildren.remove(child)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return viewDragHelper.shouldInterceptTouchEvent(ev) || super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        viewDragHelper.processTouchEvent(ev)
        return super.onTouchEvent(ev)
    }

    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(view: View, i: Int): Boolean {
            return view.visibility == VISIBLE && viewIsDraggableChild(view)
        }

        override fun onViewCaptured(view: View, i: Int) {
            if (viewDragListener != null) {
                viewDragListener!!.onViewCaptured(view, i)
            }
        }

        override fun onViewReleased(view: View, v: Float, v1: Float) {
            if (viewDragListener != null) {
                Log.d(tag, "onViewReleased: id = ${view.resources.getResourcePackageName(view.id)}, x = ${view.x.toString()}, y = ${view.y.toString()}")
                viewDragListener!!.onViewReleased(view, v, v1)
            }
        }

        override fun getViewHorizontalDragRange(view: View): Int {
            return view.width
        }

        override fun getViewVerticalDragRange(view: View): Int {
            return view.height
        }

        override fun clampViewPositionHorizontal(view: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(view: View, top: Int, dy: Int): Int {
            return top
        }

    }

    private fun viewIsDraggableChild(view: View): Boolean {

        var allowDrag = false
        when (view.id) {
            R.id.draggableCard1 -> allowDrag = draggableCard1.isChecked
            R.id.draggableCard2 -> allowDrag = draggableCard2.isChecked
            R.id.draggableCard3 -> allowDrag = draggableCard3.isChecked
            R.id.draggableCard4 -> allowDrag = draggableCard4.isChecked
        }
//        val resourceId = context.resources.getIdentifier(
//                view.resources.getResourceEntryName(view.id),
//                "drawable",
//                context.packageName
//        )
//        var newView: View = findViewById(R.id.$resourceId)
//        Log.d(tag, "viewIsDraggableChild: newView = ${view.getResources().getResourceName(view.()) newView . contentDescription . toString ()}")
//        Log.d(tag, "allowDrag = ${allowDrag.toString() }")
//
//        var myNewView: View = findViewById(view.id)
//        Log.d(tag, "view = ${ view.toString() }")
//        Log.d(tag, "myNewView = ${ myNewView.toString() }")

        return draggableChildren.isEmpty() || draggableChildren.contains(view) && allowDrag
    }

    fun setViewDragListener(viewDragListener: ViewDragListener?) {
        this.viewDragListener = viewDragListener
    }

    fun setViewScaleListener(viewScaleListener: ViewScaleListener?) {
        this.viewScaleListener = viewScaleListener
    }

    init {
        viewDragHelper = ViewDragHelper.create(this, dragCallback)
    }
}