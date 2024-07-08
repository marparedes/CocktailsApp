package com.marparedes.cocktailsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.marparedes.cocktailsapp.databinding.ActivityMainBinding
import com.marparedes.cocktailsapp.ui.adapter.CocktailListAdapter
import com.marparedes.cocktailsapp.ui.viewmodel.CocktailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterList: CocktailListAdapter
    private val viewModel: CocktailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // First set a list without searching an specific Cocktail
        viewModel.searchCocktail("")

        adapterList = CocktailListAdapter()
        binding.recyclerView.apply {
            adapter = adapterList
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {
                binding.searchView.clearFocus()
                if(!name.isNullOrBlank()) {
                    viewModel.searchCocktail(name)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        collectData()
    }

    private fun collectData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when {
                        uiState.isLoading -> binding.progressBar.visibility = View.VISIBLE
                        uiState.data.isNotEmpty() -> {
                            binding.progressBar.visibility = View.GONE
                            adapterList.submitList(uiState.data)
                        }
                        uiState.isError.isNotBlank() -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(applicationContext,uiState.isError, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}