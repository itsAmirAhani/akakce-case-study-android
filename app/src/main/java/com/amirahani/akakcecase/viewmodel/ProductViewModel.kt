package com.amirahani.akakcecase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akakcecase.model.Product
import com.example.akakcecase.network.ApiService
import com.example.akakcecase.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val api: ApiService = RetrofitClient.api // default to real API, allow mock for tests
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> get() = _allProducts

    private val _horizontalProducts = MutableStateFlow<List<Product>>(emptyList())
    val horizontalProducts: StateFlow<List<Product>> get() = _horizontalProducts

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val all = api.getAllProducts()
                _allProducts.value = all
                _horizontalProducts.value = all.take(5)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
