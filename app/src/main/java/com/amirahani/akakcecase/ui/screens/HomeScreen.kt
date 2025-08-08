package com.example.akakcecase.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amirahani.akakcecase.viewmodel.ProductViewModel
import com.example.akakcecase.model.Product
import com.example.akakcecase.ui.component.HorizontalProductCard
import com.example.akakcecase.ui.component.VerticalProductCard
import com.google.accompanist.pager.*
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val allProducts by viewModel.allProducts.collectAsState()
    val horizontalProducts by viewModel.horizontalProducts.collectAsState()

    val pagerState = rememberPagerState()

    // ✅ Take only first 5 products
    val featuredProducts = horizontalProducts.take(5)

    // ✅ Group into pages of 2 products
    val groupedProducts = featuredProducts.chunked(2)

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
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (groupedProducts.isNotEmpty()) {
            HorizontalPager(
                count = horizontalProducts.chunked(2).size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // slightly smaller than before
                    .padding(horizontal = 4.dp) // remove vertical padding here
            ) { page ->
                val pair = horizontalProducts.chunked(2)[page]
                Row(
                    Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // smaller gap between cards
                ) {
                    HorizontalProductCard(
                        product = pair[0],
                        onClick = onProductClick,
                        modifier = Modifier.weight(1f)
                    )
                    if (pair.size == 2) {
                        HorizontalProductCard(
                            product = pair[1],
                            onClick = onProductClick,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 0.1.dp) // much smaller gap
            )

        }

        Text(
            text = "All Products",
            style = MaterialTheme.typography.headlineSmall
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
