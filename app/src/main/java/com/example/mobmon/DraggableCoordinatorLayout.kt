package com.example.mobmon

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.customview.widget.ViewDragHelper
import com.example.mobmon.controller.WidgetController
import com.google.android.material.card.MaterialCardView
import java.util.*

class DraggableCoordinatorLayout @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null)
    : CoordinatorLayout(context!!, attrs) {



    /** A listener to use when a child view is dragged  */
    interface ViewDragListener {
        fun onViewCaptured(view: View, i: Int)
        fun onViewReleased(view: View, v: Float, v1: Float)
    }

    /** A listener to use when a child view is long clicked  */
    interface ViewLongClickListener {
        fun onLongClick(view: View)
    }

    private val tag = "mobmon"
    private val viewDragHelper: ViewDragHelper
    private val draggableChildren: MutableList<View> = ArrayList()
    private var viewDragListener: ViewDragListener? = null
    private var viewLongClickListener: ViewLongClickListener? = null

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
                for (widget in WidgetController.widgetList) {
                    if (widget.name == view.resources.getResourcePackageName(view.id)) {
                        widget.posX = view.x
                        widget.posY = view.y
                    }
                }
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
        var draggable = false
        val cardView = view as MaterialCardView

        if (cardView.isChecked)
            draggable = true

        return draggableChildren.isEmpty() || draggableChildren.contains(view) && draggable
    }

    fun setViewDragListener(viewDragListener: ViewDragListener?) {
        this.viewDragListener = viewDragListener
    }

    init {
        viewDragHelper = ViewDragHelper.create(this, dragCallback)
    }
}