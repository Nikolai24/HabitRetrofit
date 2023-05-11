package com.example.habitretrofit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habitretrofit.Habit
import com.example.habitretrofit.R
import com.example.habitretrofit.adapter.ViewAdapter
import com.example.habitretrofit.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewAdapter: ViewAdapter
    private var habits: ArrayList<Habit> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                habits = getSerializable(LIST) as ArrayList<Habit>
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewAdapter = ViewAdapter(this)
        binding.viewPager.adapter = viewAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = GOOD_HABITS
                else -> tab.text = BAD_HABITS
            }
        }.attach()
        binding.fab.setOnClickListener {
            val id = "-1"
            val position = -1
            commitTransaction(id, position)
        }
        val bottomSheetFragment = BottomSheetFragment()
        binding.filter.setOnClickListener {
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(LIST, habits)
        super.onSaveInstanceState(outState)
    }

    fun commitTransaction(id: String, position: Int) {
        val editFragment: Fragment = EditFragment.newInstance(id, position)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, editFragment)
            .addToBackStack(null).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
        private const val LIST = "List"
        private const val HABIT = "Habit"
        private const val POSITION = "Position"
        private const val REQUEST_KEY = "Request_key"
        private const val TOAST = "The list of habits has not changed"
        private const val GOOD_HABITS = "Good habits"
        private const val BAD_HABITS = "Bad habits"
    }
}