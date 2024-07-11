package com.marparedes.cocktailsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marparedes.cocktailsapp.databinding.IngredientItemBinding

class IngredientListAdapter(private val items: List<String>) :
    RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>() {

    inner class IngredientListViewHolder(val binding: IngredientItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.ingredient.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        val binding = IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        holder.bind(items[position])
    }
}