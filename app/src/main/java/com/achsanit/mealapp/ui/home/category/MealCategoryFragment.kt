package com.achsanit.mealapp.ui.home.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.achsanit.mealapp.databinding.FragmentMealCategoryBinding
import com.achsanit.mealapp.ui.modal.BottomSheetErrorFragment
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.collectLatestState
import com.achsanit.mealapp.utils.makeGone
import com.achsanit.mealapp.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealCategoryFragment : Fragment() {

    private var _binding: FragmentMealCategoryBinding? = null
    private val binding get() = _binding!!
    private val adapter: MealCategoryAdapter by lazy {
        MealCategoryAdapter()
    }
    private val mealCategoryViewModel: MealCategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMealCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectLatestState(mealCategoryViewModel.listCategories) { result ->
            with(binding) {
                when (result) {
                    is Resource.Loading -> {
                        pbLoading.makeVisible()
                    }

                    is Resource.Success -> {
                        pbLoading.makeGone()
                        result.data?.let {
                            adapter.submitData(it)
                        }
                    }

                    is Resource.Error -> {
                        pbLoading.makeGone()
                        BottomSheetErrorFragment(result.message.toString()).show(
                            childFragmentManager,
                            BottomSheetErrorFragment.MODAL_TAG
                        )
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
            rvCategory.adapter = adapter
            rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}