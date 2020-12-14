package com.example.mobmon.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WidgetDao {

    // Get Widgets
    @Query("SELECT * FROM widget_table")
    fun getAll(): List<Widget>

    @Query("SELECT * FROM widget_table WHERE uid IN (:widgetIds)")
    fun loadAllByIds(widgetIds: IntArray): List<Widget>

    @Query("SELECT * FROM widget_table ORDER BY widget_id ASC")
    fun readAllData(): LiveData<List<Widget>>

    // Find Widget
    @Query("SELECT * FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun findByUid(uid: Int): Widget

    @Query("SELECT * FROM widget_table WHERE widget_id LIKE :id LIMIT 1")
    fun findById(id: Int): Widget

    @Query("SELECT * FROM widget_table WHERE widget_name LIKE :name LIMIT 1")
    fun findByName(name: String): Widget

    // Getters
    @Query("SELECT widget_id FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getId(uid: Int): Int

    @Query("SELECT widget_name FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getName(uid: Int): String

    @Query("SELECT widget_type FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getType(uid: Int): String

    @Query("SELECT widget_metrics FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getMetrics(uid: Int): String

    @Query("SELECT widget_color FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getColor(uid: Int): String

    @Query("SELECT widget_position_x FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getPositionX(uid: Int): Float

    @Query("SELECT widget_position_y FROM widget_table WHERE uid LIKE :uid LIMIT 1")
    fun getPositionY(uid: Int): Float

    // Setters
    @Query("UPDATE widget_table SET widget_id = :id WHERE uid LIKE :uid ")
    fun setId(id: Int, uid: Int): Int

    @Query("UPDATE widget_table SET widget_name = :name WHERE uid LIKE :uid ")
    fun setName(name: String, uid: Int): Int

    @Query("UPDATE widget_table SET widget_type = :type WHERE uid LIKE :uid ")
    fun setType(type: String, uid: Int): Int

    @Query("UPDATE widget_table SET widget_metrics = :metrics WHERE uid LIKE :uid ")
    fun setMetrics(metrics: String, uid: Int): Int

    @Query("UPDATE widget_table SET widget_color = :color WHERE uid LIKE :uid ")
    fun setColor(color: String, uid: Int): Int

    @Query("UPDATE widget_table SET widget_position_x = :positionX WHERE uid LIKE :uid ")
    fun setPositionX(positionX: Float, uid: Int): Int

    @Query("UPDATE widget_table SET widget_position_y = :positionY WHERE uid LIKE :uid ")
    fun setPositionY(positionY: Float, uid: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWidget(widget: Widget)

    @Insert
    fun insertAll(vararg widgets: Widget)

    @Delete
    fun delete(widget: Widget)
}