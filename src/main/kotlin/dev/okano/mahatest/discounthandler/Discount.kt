package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout

interface Discount {
    fun getDiscount(checkout: Checkout): Long
}