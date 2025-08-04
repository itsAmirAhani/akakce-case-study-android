package com.example.akakcecase.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.amirahani.akakcecase.viewmodel.ProductViewModel
import com.example.akakcecase.model.Product
import com.example.akakcecase.ui.component.ProductCard

@Composable
fun HomeScreen(viewModel: ProductViewModel = viewModel(), onProductClick: (Product) -> Unit) {
    val allProducts by viewModel.allProducts.collectAsState()
    val horizontalProducts by viewModel.horizontalProducts.collectAsState()

    // Fetch once when Composable loads
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Horizontal Products", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(horizontalProducts) { product ->
                ProductCard(product, onProductClick)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("All Products", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(allProducts) { product ->
                ProductCard(product, onProductClick)
            }
        }
    }
}
