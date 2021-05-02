package dev.okano.mahatest.service

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Sku
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CheckoutService(
    private val productService: ProductService,
    private val discountService: DiscountService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getTotal(skus: List<Sku>): Long {
        val checkout = buildCheckout(skus)
        val discountAmount = discountService.getDiscount(checkout)
        val totalCheckout = checkout.items.fold(0L) { acc, item ->
            acc + item.product.price * item.quantity
        }

        log.debug("Checkout ID ${checkout.id}. Sub-total: $totalCheckout, discount: $discountAmount")

        return totalCheckout - discountAmount
    }

    private fun buildCheckout(skus: List<Sku>): Checkout {
        val items = skus.groupingBy { it }
            .eachCount()
            .mapKeys { productService.findProduct(it.key) }
            .map { (product, quantity) -> Checkout.Item(product, quantity) }

        return Checkout.of(items.toSet())
    }
}
