package com.example.habitretrofit.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import com.example.habitretrofit.Habit
import com.example.habitretrofit.HabitRepository
import com.example.habitretrofit.R
import com.example.habitretrofit.database.HabitRoomDatabase
import com.example.habitretrofit.databinding.FragmentEditBinding
import com.example.habitretrofit.viewmodel.SecondViewModel


class EditFragment: Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SecondViewModel
    private var name: String = HABIT
    private var description: String = DESCRIPTION
    private var priority: String = HIGH
    private var type: String = GOOD_HABIT
    private var quantity: Int = 0
    private var periodicity: Int = 0
    var oldType = GOOD_HABIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = SecondViewModel(HabitRepository(HabitRoomDatabase.getDatabase(requireContext()).habitDao()), arguments?.getString(ID) as String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position: Int = arguments?.getInt(POSITION) as Int
        println("EDIT FRAGMENT")
        var item = Habit(0,0,0,description,0,0,0, name, 0, false, "-1")

//        viewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
//            item = habit!!
//            println(item)
//        })
//        val db = HabitRoomDatabase.getDatabase(requireContext()).habitDao()
//        item = db.findByID( arguments?.getString(ID) as String)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priorities_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
        binding.radioButtonGood.setOnClickListener {
            type = GOOD_HABIT
            viewModel.type(type)
        }
        binding.radioButtonBad.setOnClickListener {
            type = BAD_HABIT
            viewModel.type(type)
        }
        val saveButton: Button = view.findViewById(R.id.save_habit)
        val cancelButton: Button = view.findViewById(R.id.cancel)
        if (position == -1){
            saveButton.setOnClickListener {
                viewModel.priority(binding.spinner.selectedItem.toString())
                viewModel.updateList()
//                sendResult(item, -1, binding.spinner)
                activity?.onBackPressed()
            }
        }
        if (position > -1){

            val db = HabitRoomDatabase.getDatabase(requireContext()).habitDao()
            item = db.findByID( arguments?.getString(ID) as String)

            binding.nameHabit.setText(item.title)
            binding.description.setText(item.description)
            binding.quantity.setText(item.count!!.toString())
            viewModel.quantity(item.count!!.toString())
            binding.periodicity.setText(item.frequency!!.toString())
            viewModel.periodicity(item.frequency!!.toString())
            if (item.type == 0){
                binding.radioGroup.check(R.id.radioButtonGood)
                type = GOOD_HABIT
                oldType = GOOD_HABIT
            } else {
                binding.radioGroup.check(R.id.radioButtonBad)
                type = BAD_HABIT
                oldType = BAD_HABIT
            }
            var priority = ""
            if (item.priority == 0){
                binding.spinner.setSelection(0)
                priority = HIGH
            }
            if (item.priority == 1){
                binding.spinner.setSelection(1)
                priority = MEDIUM
            }
            if (item.priority == 2){
                binding.spinner.setSelection(2)
                priority = LOW
            }
            saveButton.setOnClickListener {
                viewModel.priority(binding.spinner.selectedItem.toString())
                viewModel.updateList()
//                sendResult(item, position, binding.spinner)
                activity?.onBackPressed()
            }
            viewModel.name(item.title!!)
            viewModel.priority(priority)
            viewModel.type(type)
//            viewModel.id(item.id!!)
        }
        viewModel.position(position)
        viewModel.oldType(oldType)
        binding.nameHabit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.name(s.toString())
            }
        })
        binding.description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.description(s.toString())
            }
        })
        binding.quantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.quantity(s.toString())
            }
        })
        binding.periodicity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                viewModel.periodicity(s.toString())
            }
        })
//        cancelButton.setOnClickListener {
//            sendResult(item, -2, binding.spinner)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String, position: Int): EditFragment {
            val fragment = EditFragment()
            val args = Bundle()
            args.putString(ID, id)
            args.putInt(POSITION, position)
            fragment.arguments = args
            return fragment
        }
        private const val HABIT = "Habit"
        private const val ID = "ID"
        private const val DESCRIPTION = ""
        private const val GOOD_HABIT = "Good habit"
        private const val BAD_HABIT = "Bad habit"
        private const val HIGH = "High"
        private const val MEDIUM = "Medium"
        private const val LOW = "Low"
        private const val POSITION = "Position"
        private const val REQUEST_KEY = "Request_key"
    }
}