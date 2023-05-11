package com.example.habitretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.habitretrofit.Habit
import com.example.habitretrofit.HabitRepository

class FirstViewModel(private val model: HabitRepository) {

    private val mutableGoodList: MutableLiveData<MutableList<Habit>?> = MutableLiveData()
    val goodList: LiveData<MutableList<Habit>?> = mutableGoodList

    private val mutableBadList: MutableLiveData<MutableList<Habit>?> = MutableLiveData()
    val badList: LiveData<MutableList<Habit>?> = mutableBadList

//    private val mutableGoodList: MutableLiveData<LiveData<List<Habit>>?> = MutableLiveData()
//    val goodList: LiveData<LiveData<List<Habit>>?> = mutableGoodList
//
//    private val mutableBadList: MutableLiveData<LiveData<List<Habit>>?> = MutableLiveData()
//    val badList: LiveData<LiveData<List<Habit>>?> = mutableBadList

    init {
        getList()
    }
    private fun getList() {
//        val habitsLiveData: LiveData<List<Habit>> = model.getHabitsList()
//        habitsLiveData.observe(lifecycleOwner, Observer { habits ->
//            mutableGoodList.value = habits.toMutableList()
//            mutableBadList.value = habits.toMutableList()
//        })

//        var all = model.getHabitsList()
//        mutableGoodList.value = all
//        mutableBadList.value = all

//        var good = mutableListOf<Habit>()
//        var bad = mutableListOf<Habit>()
//        for (i in all) {
//            if (i.type == "Good habit") {
//                good.add(i)
//            } else {
//                bad.add(i)
//            }
//        }
//        mutableGoodList.value = good
//        mutableBadList.value = bad
    }

    fun sort(){
        model.sort()
    }

    fun search(s: String){
        model.search(s)
    }
}