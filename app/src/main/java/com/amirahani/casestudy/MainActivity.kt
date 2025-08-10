package com.amirahani.casestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amirahani.casestudy.model.NavRoutes
import com.amirahani.casestudy.viewmodel.ProductViewModel
import com.amirahani.casestudy.ui.screens.HomeScreen
import com.amirahani.casestudy.ui.screens.ProductDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppNav() }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()
    // ONE shared ViewModel for the whole graph
    val vm: ProductViewModel = viewModel()

    // Fetch once (you can move this to HomeScreen if you prefer, but don't do both)
    LaunchedEffect(Unit) { vm.fetchProducts() }

    NavHost(navController = navController, startDestination = NavRoutes.HOME) {

        composable(NavRoutes.HOME) {
            HomeScreen(
                viewModel = vm,
                onProductClick = { product ->
                    navController.navigate("${NavRoutes.PRODUCT_DETAIL}/${product.id}")
                }
            )
        }

        composable(
            route = "${NavRoutes.PRODUCT_DETAIL}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            // IMPORTANT: reuse the SAME vm (do NOT create a new one here)
            val allProducts = vm.allProducts.collectAsState().value
            val product = allProducts.find { it.id == productId }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text("Loading product…")
            }
        }
    }
}
