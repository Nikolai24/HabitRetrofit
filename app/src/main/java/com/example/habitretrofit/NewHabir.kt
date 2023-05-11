package com.example.habitretrofit

data class NewHabit(
    var color: Int? = null,
    var count: Int? = null,
    var date: Int? = null,
    var description: String? = null,
    var done_dates: Int? = null,
    var frequency: Int? = null,
    var priority: Int? = null,
    var title: String? = null,
    var type: Int? = null,
    var uid: String
)