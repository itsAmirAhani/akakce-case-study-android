package com.amirahani.akakcecase

import app.cash.turbine.test
import com.amirahani.akakcecase.viewmodel.ProductViewModel
import com.example.akakcecase.model.Product
import com.example.akakcecase.model.Rating
import com.example.akakcecase.network.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var vm: ProductViewModel
    private val api: ApiService = mockk()

    private val fakeProducts = listOf(
        Product(1, "A", 10.0, "img1", "desc1", Rating(4.2f, 50)),
        Product(2, "B", 20.0, "img2", "desc2", Rating(3.8f, 30)),
        Product(3, "C", 30.0, "img3", "desc3", Rating(4.9f, 120)),
        Product(4, "D", 40.0, "img4", "desc4", Rating(4.1f, 70)),
        Product(5, "E", 50.0, "img5", "desc5", Rating(2.9f, 10)),
        Product(6, "F", 60.0, "img6", "desc6", Rating(4.6f, 300)),
    )

    @Before
    fun setup() {
        coEvery { api.getAllProducts() } returns fakeProducts
        vm = ProductViewModel(api)
    }

    @Test
    fun `fetchProducts fills allProducts and horizontalProducts`() = runTest {
        assertTrue(vm.allProducts.value.isEmpty())
        assertTrue(vm.horizontalProducts.value.isEmpty())

        vm.fetchProducts()
        advanceUntilIdle() // wait for coroutine to finish

        vm.allProducts.test {
            val value = awaitItem()
            assertEquals(6, value.size)
            cancelAndConsumeRemainingEvents()
        }

        vm.horizontalProducts.test {
            val value = awaitItem()
            assertEquals(5, value.size)
            assertEquals(listOf(1, 2, 3, 4, 5), value.map { it.id })
            cancelAndConsumeRemainingEvents()
        }
    }
}
