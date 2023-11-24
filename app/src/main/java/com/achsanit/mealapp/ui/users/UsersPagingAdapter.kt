package com.achsanit.mealapp.ui.users

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.achsanit.mealapp.data.response.DataItem
import com.achsanit.mealapp.databinding.ItemUserLayoutBinding

class UsersPagingAdapter : PagingDataAdapter<DataItem, UsersPagingAdapter.ViewHolder>(DIFFUTIL) {
    inner class ViewHolder(private val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(data:DataItem) {
                with(binding) {
                    tvFullname.text = "${data.firstName} ${data.lastName}"
                    tvEmail.text = data.email
                    ivAvatar.load(data.avatar)
                }
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    companion object {
        private val DIFFUTIL = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}