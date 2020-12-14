package com.example.mobmon.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "widget_table")
data class Widget(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "widget_id") val widgetId: Int?,
    @ColumnInfo(name = "widget_name") val widgetName: String?,
    @ColumnInfo(name = "widget_type") val widgetType: String?,
    @ColumnInfo(name = "widget_metrics") val widgetMetrics: String?,
    @ColumnInfo(name = "widget_color") val widgetColor: String?,
    @ColumnInfo(name = "widget_position_x") val widgetPositionX: Float?,
    @ColumnInfo(name = "widget_position_y") val widgetPositionY: Float?
)