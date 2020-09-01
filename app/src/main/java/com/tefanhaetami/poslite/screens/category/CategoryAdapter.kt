package com.tefanhaetami.poslite.screens.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tefanhaetami.poslite.database.iface.Category
import com.tefanhaetami.poslite.databinding.RecordCategoryBinding

class CategoryAdapter(val clickListener: CategoryListener): ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecordCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: CategoryListener, item: Category) {
            binding.category = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecordCategoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}

class CategoryListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(item: Category) {
        clickListener(item.id)
    }
}