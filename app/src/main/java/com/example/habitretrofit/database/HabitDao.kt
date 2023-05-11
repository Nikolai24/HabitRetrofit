package com.example.habitretrofit.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habitretrofit.Habit

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits_table")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits_table WHERE type LIKE :type")
    fun getByType(type: Int): LiveData<List<Habit>>

    @Insert
    suspend fun insertAll(vararg habit: Habit)

    @Query("SELECT * FROM habits_table WHERE uid LIKE :uid LIMIT 1")
    fun findByID(uid: String): Habit

    @Update
    fun update(habit: Habit)
}