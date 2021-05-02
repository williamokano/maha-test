package dev.okano.mahatest.service

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Sku
import org.springframework.stereotype.Service

@Service
class CheckoutService(private val productService: ProductService) {
    fun getTotal(skus: List<Sku>): Long {

        return 1
    }

    private fun buildCheckout(skus: List<Sku>): Checkout {
        val items = skus.groupingBy { it }
            .eachCount()
            .mapKeys { productService.findProduct(it.key) }
            .map { (product, quantity) -> Checkout.Item(product, quantity) }

        return Checkout.of(items.toSet())
    }
}