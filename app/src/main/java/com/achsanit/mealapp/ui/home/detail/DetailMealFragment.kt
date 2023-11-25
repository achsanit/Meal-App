package com.achsanit.mealapp.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.achsanit.mealapp.R
import com.achsanit.mealapp.data.response.MealByIdItem
import com.achsanit.mealapp.databinding.FragmentDetailMealBinding
import com.achsanit.mealapp.ui.modal.BottomSheetErrorFragment
import com.achsanit.mealapp.utils.Resource
import com.achsanit.mealapp.utils.collectLatestState
import com.achsanit.mealapp.utils.convertToMap
import com.achsanit.mealapp.utils.makeGone
import com.achsanit.mealapp.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class DetailMealFragment : Fragment() {

    private var _binding: FragmentDetailMealBinding? = null
    private val binding get() = _binding!!
    private val detailMealViewModel: DetailMealViewModel by viewModel()
    private val args: DetailMealFragmentArgs by navArgs()
    private var isBookmarkLatest by Delegates.notNull<Boolean>()
    private lateinit var mealByIdItem: MealByIdItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.mealItem.idMeal?.let { detailMealViewModel.getMealById(it) }

        with(binding) {
            collectLatestState(detailMealViewModel.meal) { result ->
                when (result) {
                    is Resource.Loading -> {
                        pbLoading.makeVisible()
                    }

                    is Resource.Success -> {
                        pbLoading.makeGone()
                        result.data?.let {
                            setUpView(it)
                            mealByIdItem = it
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

            ibBookmark.setOnClickListener {
                if (::mealByIdItem.isInitialized) {
                    if (!isBookmarkLatest) {
                        detailMealViewModel.insertBookmarkMeal(mealByIdItem)
                        Toast.makeText( requireContext(),"bookmark successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        detailMealViewModel.deleteBookmarkMeal(args.mealItem.idMeal.toString().toInt())
                        Toast.makeText( requireContext(), "bookmark successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setUpView(data: MealByIdItem) {
        with(binding) {
            tvMealName.text = data.strMeal
            tvCountry.text = getString(R.string.text_country_placeholder, data.strArea)
            tvCategory.text = getString(R.string.text_category_placeholder, data.strCategory)
            tvInstruction.text = data.strInstructions
            ivThumbMeal.load(data.strMealThumb)
            ibBackToolbar.setOnClickListener {
                findNavController().popBackStack()
            }

            val map = convertToMap(data)
            val ingredients = StringBuilder()

            for (i in 1..20) {
                val ingredient = map["strIngredient$i"]

                if (ingredient.toString().isNotBlank() && ingredient != null) {
                    ingredients.append("${map["strIngredient$i"]} ${map["strMeasure$i"]}\n")
                }
            }

            tvIngredients.text = ingredients.toString()

            detailMealViewModel.getMealBookmarkById(data.idMeal.toString().toInt())
                .observe(viewLifecycleOwner) { result ->
                    result?.let {
                        setButtonBookmark(true)
                    } ?: run {
                        setButtonBookmark(false)
                    }
                }
        }
    }

    private fun setButtonBookmark(isBookmark: Boolean) {
        with(binding) {
            if (isBookmark) {
                ibBookmark.load(R.drawable.ic_bookmark)
            } else {
                ibBookmark.load(R.drawable.ic_bookmark_outlined)
            }
            isBookmarkLatest = isBookmark
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}