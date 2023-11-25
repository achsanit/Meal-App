package com.achsanit.mealapp.ui.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.mealapp.data.response.CategoriesItem
import com.achsanit.mealapp.databinding.ItemCategoryBinding

class MealCategoryAdapter(
    private val onClick: (CategoriesItem) -> Unit
) : RecyclerView.Adapter<MealCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: CategoriesItem) {
                with(binding) {
                    tvTitleCategory.text = data.strCategory
                    ivThumbnailCategory.load(data.strCategoryThumb)

                    cvCategory.setOnClickListener {
                        onClick.invoke(data)
                    }
                }
            }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CategoriesItem>() {
        override fun areItemsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: CategoriesItem, newItem: CategoriesItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(data: List<CategoriesItem?>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = differ.currentList[position]
        holder.bind(currentData)
    }
}