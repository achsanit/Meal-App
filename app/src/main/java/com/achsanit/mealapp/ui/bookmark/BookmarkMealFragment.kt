package com.achsanit.mealapp.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.mealapp.databinding.FragmentBookmarkMealBinding
import com.achsanit.mealapp.ui.home.listmeal.MealsAdapter
import com.achsanit.mealapp.utils.mapToMealsItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkMealFragment : Fragment() {

    private var _binding: FragmentBookmarkMealBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarkMealViewModel by viewModel()
    private val mealsAdapter: MealsAdapter by lazy {
        MealsAdapter {
            val action =
                BookmarkMealFragmentDirections.actionBookmarkMealFragmentToDetailMealFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListBookmark()

        with(binding) {
            rvBookmark.apply {
                adapter = mealsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ibTrash.setOnClickListener {
                viewModel.deleteAllBookmarkMeal()
            }
        }
    }

    private fun getListBookmark() {
        viewModel.getListBookmark().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.clNoBookmarks.visibility = View.VISIBLE
                binding.rvBookmark.visibility = View.GONE
            } else {
                binding.clNoBookmarks.visibility = View.GONE
                binding.rvBookmark.visibility = View.VISIBLE
                mealsAdapter.submitData(it.mapToMealsItem())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}