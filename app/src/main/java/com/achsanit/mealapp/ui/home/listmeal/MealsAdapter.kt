package com.achsanit.mealapp.ui.home.listmeal

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.mealapp.data.response.MealsItem
import com.achsanit.mealapp.databinding.ItemMealBinding

class MealsAdapter(
    private val onClick: (MealsItem) -> Unit
) : RecyclerView.Adapter<MealsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemMealBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(data: MealsItem) {
                with(binding) {
                    tvMealName.text = data.strMeal
                    ivThumbnailMeal.load(data.strMealThumb)

                    root.setOnClickListener {
                        onClick.invoke(data)
                    }
                }
            }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<MealsItem>() {
        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(data: List<MealsItem?>) {
        differ.submitList(data)

        Log.d("ADAPTER BOOKMARK", data.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}