package com.example.akakcecase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amirahani.akakcecase.viewmodel.ProductViewModel
import com.example.akakcecase.model.Product
import com.example.akakcecase.ui.component.HorizontalProductCard
import com.example.akakcecase.ui.component.VerticalProductCard
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val allProducts by viewModel.allProducts.collectAsState()
    val horizontalProducts by viewModel.horizontalProducts.collectAsState()

    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ShopEase", style = MaterialTheme.typography.headlineSmall) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (horizontalProducts.isNotEmpty()) {
                Text(
                    text = "Featured Products",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalPager(
                    count = horizontalProducts.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) { page ->
                    HorizontalProductCard(
                        product = horizontalProducts[page],
                        onClick = onProductClick
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp, bottom = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "All Products",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                items(allProducts) { product ->
                    VerticalProductCard(product = product, onClick = onProductClick)
                }
            }
        }
    }
}
