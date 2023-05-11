package com.example.habitretrofit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits_table")
data class Habit(
    @ColumnInfo var color: Int? = null,
    @ColumnInfo var count: Int? = null,
    @ColumnInfo var date: Int? = null,
    @ColumnInfo var description: String? = null,
    @ColumnInfo var done_dates: Int? = null,
    @ColumnInfo var frequency: Int? = null,
    @ColumnInfo var priority: Int? = null,
    @ColumnInfo var title: String? = null,
    @ColumnInfo var type: Int? = null,
    @ColumnInfo var isSynced: Boolean? = null,
    @PrimaryKey var uid: String
)
