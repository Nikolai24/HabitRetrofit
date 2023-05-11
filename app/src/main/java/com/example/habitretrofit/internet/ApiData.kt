package com.example.habitretrofit.internet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    @Json(name = "color") val color: Int,
    @Json(name = "count") val count: Int,
    @Json(name = "date") val date: Int,
    @Json(name = "description") val description: String,
    @Json(name = "frequency") val frequency: Int,
    @Json(name = "priority") val priority: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "uid") val uid: String
)