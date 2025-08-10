package com.amirahani.casestudy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.amirahani.casestudy.model.Product
import com.amirahani.casestudy.ui.component.HorizontalProductCard
import com.amirahani.casestudy.ui.component.VerticalProductCard
import com.amirahani.casestudy.viewmodel.ProductViewModel
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val allProducts by viewModel.allProducts.collectAsState(initial = emptyList())

    // iOS-like split: first 5 featured, rest below
    val featured: List<Product> = allProducts.take(5)
    val remaining: List<Product> = allProducts.drop(5)
    val featuredPages: List<List<Product>> = featured.chunked(2)
    val remainingRows: List<List<Product>> = remaining.chunked(2)

    val pagerState = rememberPagerState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars.only(WindowInsetsSides.Top)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Featured title
        item {
            Text(
                "Featured Products",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Featured horizontal pager (cards in pairs of 2)
        if (featuredPages.isNotEmpty()) {
            item {
                val cardHeight = 250.dp

                Column {
                    HorizontalPager(
                        count = featuredPages.size,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(cardHeight)
                    ) { page ->
                        val pair = featuredPages[page]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(cardHeight)
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
                            .padding(top = 8.dp)
                    )
                }
            }
        }

        // All products title
        item {
            Text(
                "All Products",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }

        // All products as rows of two (no inner scrolling)
        items(remainingRows.size) { rowIndex ->
            val row = remainingRows[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VerticalProductCard(
                    product = row[0],
                    onClick = onProductClick,
                    modifier = Modifier.weight(1f)
                )
                if (row.size == 2) {
                    VerticalProductCard(
                        product = row[1],
                        onClick = onProductClick,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}
