package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout

interface DiscountRule {
    fun getDiscount(checkout: Checkout): Long
}
