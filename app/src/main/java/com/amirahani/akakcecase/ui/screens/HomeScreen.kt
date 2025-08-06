package com.example.akakcecase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amirahani.akakcecase.viewmodel.ProductViewModel
import com.example.akakcecase.model.Product
import com.example.akakcecase.ui.component.VerticalProductCard
import com.example.akakcecase.ui.component.HorizontalProductCard
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.asPaddingValues

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel = viewModel(),
    onProductClick: (Product) -> Unit
) {
    val allProducts by viewModel.allProducts.collectAsState()
    val horizontalProducts by viewModel.horizontalProducts.collectAsState()

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                WindowInsets.statusBars
                    .only(WindowInsetsSides.Top)
                    .asPaddingValues()
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = "Featured Products",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (horizontalProducts.isNotEmpty()) {
            HorizontalPager(
                count = horizontalProducts.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp) // optionally increased for more space
            ) { page ->
                HorizontalProductCard(product = horizontalProducts[page])
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp, bottom = 16.dp)
            )
        }

        Text(
            text = "All Products",
            style = MaterialTheme.typography.headlineSmall,
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(allProducts) { product ->
                VerticalProductCard(product = product, onClick = onProductClick)
            }
        }
    }
}
