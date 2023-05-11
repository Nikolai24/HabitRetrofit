package com.example.habitretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitretrofit.Habit
import com.example.habitretrofit.HabitRepository
import com.example.habitretrofit.NewHabit
import com.example.habitretrofit.internet.HabitsApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SecondViewModel(private val model: HabitRepository, var id: String) {

    private var mutableHabit: MutableLiveData<Habit?> = MutableLiveData()
    var habit: LiveData<Habit?> = mutableHabit
    var position = -1
    var newHabit = Habit(0,0,0,"",0,0,0, "New habit", 0, false, id)
    var oldType = "Good habit"

    init {
        getHabit(id)
    }

    private fun getHabit(id: String){
        mutableHabit.value = model.getHabit(id)
    }

    fun updateList() {
//        val job: Job = GlobalScope.launch(Dispatchers.IO) {
//            if (id == -1) {
//                model.addHabit(newHabit)
//            }
//            if (id > -1) {
//                model.changeHabit(id, newHabit)
//            }
//        }
//        job.start()

        if (id == "-1") {
            var newId = ""
            val a = (10..100).random()
            val b = (150..500).random()
            val c = (550..900).random()
            newId = "$a-$b-$c"
            println("newId = $newId")
            newHabit.uid = newId
            println(newHabit)
            model.addHabit(newHabit)
        } else {
            model.changeHabit(id, newHabit)
        }
        var new = NewHabit(newHabit.color, newHabit.count, newHabit.date, newHabit.description,
        newHabit.done_dates, newHabit.frequency, newHabit.priority, newHabit.title,
        newHabit.type, newHabit.uid)
        GlobalScope.launch {
            HabitsApiImpl.putHabit(new)
        }
    }

    fun name(s: String){
        newHabit.title = s
    }

    fun description(s: String){
        newHabit.description = s
    }

    fun quantity(s: String){
        if (s == ""){
            newHabit.count = 0
        } else {
            newHabit.count = s.toInt()
        }
    }

    fun periodicity(s: String){
        if (s == ""){
            newHabit.frequency = 0
        } else {
            newHabit.frequency = s.toInt()
        }
    }

    fun priority(s: String){
        if (s == "High") {
            newHabit.priority = 0
        }
        if (s == "Medium") {
            newHabit.priority = 1
        }
        if (s == "Low") {
            newHabit.priority = 2
        }
    }

    fun type(s: String){
        if (s == "Good habit"){
            newHabit.type = 0
        } else {
            newHabit.type = 1
        }
    }

    fun position(p: Int){
        position = p
    }

    fun oldType(s: String){
        oldType = s
    }

//    fun id(id: Int){
//        newHabit.id = id
//    }

}