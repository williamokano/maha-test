package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Sku
import org.springframework.stereotype.Component

@Component
class RolexDiscount : AbstractSetDiscount() {
    override fun itemSku(): Sku = Sku.of("001")

    override fun discountThreshold(): Int = 3

    override fun discountAmount(): Long = 100
}