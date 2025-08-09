package com.example.akakcecase.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val allProducts by viewModel.allProducts.collectAsState(initial = emptyList())

    // iOS-like split: first 5 featured, rest in grid
    val featured: List<Product> = allProducts.take(5)
    val remaining: List<Product> = allProducts.drop(5)
    val pages: List<List<Product>> = featured.chunked(2)

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars.only(WindowInsetsSides.Top))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text("Featured Products", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        if (pages.isNotEmpty()) {
            val cardHeight = 250.dp   // ⬅️ big enough for image + title + price + rating

            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)   // pager is as tall as each row of cards
            ) { page ->
                val pair = pages[page]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(cardHeight)         // ⬅️ enforce card height here too
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
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
                    .padding(top = 8.dp) // dots clearly below the cards
            )

            Spacer(Modifier.height(8.dp))
        }

        Text("All Products", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight(),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            items(remaining) { product ->
                VerticalProductCard(product = product, onClick = onProductClick)
            }
        }
    }
}
