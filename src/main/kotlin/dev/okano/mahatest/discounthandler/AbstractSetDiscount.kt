package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Sku
import org.slf4j.LoggerFactory

abstract class AbstractSetDiscount : Discount {

    private val log = LoggerFactory.getLogger(javaClass)

    abstract fun itemSku(): Sku
    abstract fun discountThreshold(): Int
    abstract fun discountAmount(): Long

    override fun getDiscount(checkout: Checkout): Long {

        if (checkout.containsItem()) {
            val numberOfSets = checkout.findQuantity(itemSku()) / discountThreshold()
            log.debug("Applying discount for $numberOfSets sets on ${javaClass.simpleName}")

            return numberOfSets * discountAmount()
        }

        return 0
    }

    private fun Checkout.containsItem() = this.items.any { it.product.sku == itemSku() }
}