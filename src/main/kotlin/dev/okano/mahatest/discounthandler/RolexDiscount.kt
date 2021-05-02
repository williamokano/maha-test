package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Sku
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RolexDiscount : Discount {

    private val log = LoggerFactory.getLogger(RolexDiscount::class.java)

    companion object {
        private val ROLEX_SKU = Sku.of("001")
        private const val DISCOUNT_THRESHOLD = 3
        private const val DISCOUNT_AMOUNT: Long = 100
    }

    override fun getDiscount(checkout: Checkout): Long {
        log.debug("Calculating rolex discount for checkout ${checkout.id}")

        if (checkout.containsRolex()) {
            val numberOfDiscounts = checkout.findQuantity(ROLEX_SKU) / DISCOUNT_THRESHOLD
            log.debug("Found $numberOfDiscounts sets for $ROLEX_SKU")

            return numberOfDiscounts * DISCOUNT_AMOUNT
        }

        return 0
    }

    private fun Checkout.containsRolex() = this.items.any { it.product.sku == ROLEX_SKU }
}