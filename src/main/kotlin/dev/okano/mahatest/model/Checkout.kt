package dev.okano.mahatest.model

import java.util.*

data class Checkout(val id: UUID = UUID.randomUUID(), val items: Set<Item>) {
    companion object {
        fun of(items: Set<Item>) = Checkout(items = items)
        fun of(vararg items: Item) = Checkout(items = items.toSet())
    }

    data class Item(val product: Product, val quantity: Int)

    fun findQuantity(sku: Sku): Int {
        return items.find { it.product.sku == sku }?.quantity ?: 0
    }
}