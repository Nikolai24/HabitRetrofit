package com.example.habitretrofit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.habitretrofit.Habit
import com.example.habitretrofit.HabitRepository
import com.example.habitretrofit.R
import com.example.habitretrofit.adapter.DataAdapter
import com.example.habitretrofit.database.HabitRoomDatabase
import com.example.habitretrofit.databinding.FragmentListBinding
import com.example.habitretrofit.internet.HabitsApiImpl
import com.example.habitretrofit.viewmodel.FirstViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataAdapter: DataAdapter
    private var typeHabit = 0
    private lateinit var viewModel: FirstViewModel

    private val listener: DataAdapter.OnItemClickListener = object: DataAdapter.OnItemClickListener{
        override fun onItemClick(item: Habit, position: Int) {
            commitTransaction(item, position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = FirstViewModel(HabitRepository(HabitRoomDatabase.getDatabase(requireContext()).habitDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var list = mutableListOf<Habit>()
        dataAdapter = DataAdapter(list as ArrayList<Habit>, listener)
        binding.recyclerView.adapter = dataAdapter
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            typeHabit = getInt(ARG_OBJECT)
        }
        val db = HabitRoomDatabase.getDatabase(requireContext()).habitDao()

        var listHab = mutableListOf<Habit>(Habit(0, 0, 0, "123",0, 0, 0, "qwerty",0,false,"-1"))
        GlobalScope.launch {
            listHab.addAll(HabitsApiImpl.getListOfHabits())
        }

        dataAdapter.setHabits(listHab as ArrayList<Habit>)



//        if (typeHabit == 0){
//
//            val habitsLiveData: LiveData<List<Habit>> = db.getByType(0)
////            val habitsLiveData: LiveData<List<Habit>> = db.getAll()
//            habitsLiveData.observe(viewLifecycleOwner, Observer { habits ->
//                println(habits)
//                dataAdapter.setHabits(habits as ArrayList<Habit>)
////                dataAdapter = DataAdapter(habits as ArrayList<Habit>, listener)
////                binding.recyclerView.adapter = dataAdapter
//            })
////
////            viewModel.goodList.observe(viewLifecycleOwner, Observer { goodList ->
////                dataAdapter = DataAdapter(goodList as ArrayList<Habit>, listener)
////                binding.recyclerView.adapter = dataAdapter
////            })
//        } else {
//
//            val habitsLiveData: LiveData<List<Habit>> = db.getByType(1)
////            val habitsLiveData: LiveData<List<Habit>> = db.getAll()
//            habitsLiveData.observe(viewLifecycleOwner, Observer { habits ->
//                println(habits)
//                dataAdapter.setHabits(habits as ArrayList<Habit>)
////                dataAdapter = DataAdapter(habits as ArrayList<Habit>, listener)
////                binding.recyclerView.adapter = dataAdapter
//            })
//
////            viewModel.badList.observe(viewLifecycleOwner, Observer { badList ->
////                dataAdapter = DataAdapter(badList as ArrayList<Habit>, listener)
////                binding.recyclerView.adapter = dataAdapter
////            })
//        }
        val lManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.apply { layoutManager = lManager }
    }

    fun setNewList(){
        dataAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun commitTransaction(item: Habit, position: Int) {
//        val editHabitFragment: Fragment = EditHabitFragment.newInstance(item, position)
//        activity?.supportFragmentManager?.beginTransaction()
//            ?.replace(R.id.fragment_container, editHabitFragment)
//            ?.addToBackStack(null)?.commit()
        var id = item.uid!!
        val editFragment: Fragment = EditFragment.newInstance(id, position)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, editFragment)
            ?.addToBackStack(null)?.commit()
    }
    companion object {
        private const val ARG_OBJECT = "Object"
        private const val ARRAY_LIST = "Array list"
        private const val GOOD_HABIT = "Good habit"
        private const val BAD_HABIT = "Bad habit"
    }
}