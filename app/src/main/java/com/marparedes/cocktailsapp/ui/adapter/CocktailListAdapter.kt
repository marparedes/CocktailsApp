package com.marparedes.cocktailsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.marparedes.cocktailsapp.R
import com.marparedes.cocktailsapp.databinding.ItemCardBinding
import com.marparedes.cocktailsapp.domain.model.CocktailModel

class CocktailListAdapter: RecyclerView.Adapter<CocktailListAdapter.myViewHolder>() {
    inner class myViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CocktailModel>() {
        override fun areItemsTheSame(oldItem: CocktailModel, newItem: CocktailModel): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: CocktailModel, newItem: CocktailModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<CocktailModel>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder = myViewHolder(
        ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            cocktailImage.load(item.strDrinkThumb)
            cocktailName.text = item.strDrink
            if (item.strAlcoholic == "Alcoholic") {
                alcoholicDrink.setImageResource(R.drawable.local_bar_24px)
            } else {
                alcoholicDrink.setImageResource(R.drawable.no_drinks_24px)
            }
            if(!item.ingredients.isNullOrEmpty()) {
                ingredientsList.layoutManager = GridLayoutManager(root.context, 2)
                ingredientsList.adapter = IngredientListAdapter(item.ingredients)
                ingredientsList.setHasFixedSize(false)
            }
        }
    }
}