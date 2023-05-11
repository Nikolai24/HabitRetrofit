package com.example.habitretrofit.internet

import com.example.habitretrofit.Habit
import com.example.habitretrofit.NewHabit
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface HabitsApi {
    @Headers("Authorization: f203c9ed-a5c2-4f2c-a482-d6ebd56a8183")
    @GET("api/habit")
    suspend fun getListOfHabits(): List<ApiData>

    @Headers("Authorization: f203c9ed-a5c2-4f2c-a482-d6ebd56a8183")
    @PUT("api/habit")
    suspend fun putHabit(@Body habit: String)
}

object HabitsApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://droid-test-server.doubletapp.ru/")
        .build()

    private val habitsService = retrofit.create(HabitsApi::class.java)

    suspend fun getListOfHabits(): List<Habit> {
        return withContext(Dispatchers.IO) {
            habitsService.getListOfHabits()
                .map { result ->
                    Habit(
                        result.color,
                        result.count,
                        result.date,
                        result.description,
                        0,
                        result.frequency,
                        result.priority,
                        result.title,
                        result.type,
                        true,
                        result.uid
                    )
                }
        }
    }

//    @OptIn(ExperimentalStdlibApi::class)
    suspend fun putHabit(habit: NewHabit){

//        val moshi: Moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        val jsonAdapter: JsonAdapter<NewHabit> = moshi.adapter<NewHabit>()
//        var json: String = jsonAdapter.toJson(habit)
        var json = "{ \"color\": 0, \"count\": 0, \"date\": 0, \"description\": \"123\", \"done_dates\": [ 0 ], \"frequency\": 0, \"priority\": 0, \"title\": \"123\", \"type\": 0, \"uid\": \"string\"}"
        println(json)
        habitsService.putHabit(json)

//        habitsService.putHabit(habit)
    }
}