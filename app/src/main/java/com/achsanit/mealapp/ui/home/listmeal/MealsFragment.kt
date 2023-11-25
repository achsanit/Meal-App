package com.achsanit.mealapp.ui.home.listmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.mealapp.R
import com.achsanit.mealapp.databinding.FragmentMealsBinding
import com.achsanit.mealapp.ui.modal.BottomSheetErrorFragment
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.collectLatestState
import com.achsanit.mealapp.utils.makeGone
import com.achsanit.mealapp.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealsFragment : Fragment() {

    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!
    private val mealsViewModel: MealsViewModel by viewModel()
    private val adapter: MealsAdapter by lazy {
        MealsAdapter()
    }
    private val args: MealsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.categoryItem.strCategory?.let {
            mealsViewModel.getMeasByCategory(it)
            binding.tvTitleToolbar.text = getString(R.string.meals, it)
        }

        collectLatestState(mealsViewModel.meals) { result ->
            with(binding) {
                when (result) {
                    is Resource.Loading -> pbLoading.makeVisible()
                    is Resource.Success -> {
                        pbLoading.makeGone()
                        result.data?.let {
                            adapter.submitData(it)
                        }
                    }

                    else -> {
                        pbLoading.makeGone()
                        BottomSheetErrorFragment(result.message.toString()).show(
                            childFragmentManager,
                            BottomSheetErrorFragment.MODAL_TAG
                        )
                    }
                }
            }
        }

        with(binding) {
            rvMeals.adapter = adapter
            rvMeals.layoutManager = LinearLayoutManager(requireContext())

            ibBackToolbar.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}