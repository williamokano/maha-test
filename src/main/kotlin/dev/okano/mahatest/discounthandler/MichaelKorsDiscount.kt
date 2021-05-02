package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Sku
import org.springframework.stereotype.Component

@Component
class MichaelKorsDiscount : AbstractSetDiscount() {
    override fun itemSku(): Sku = Sku.of("002")

    override fun discountThreshold(): Int = 2

    override fun discountAmount(): Long = 40
}