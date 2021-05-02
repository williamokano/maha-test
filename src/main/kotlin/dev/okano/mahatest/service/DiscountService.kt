package dev.okano.mahatest.service

import dev.okano.mahatest.discounthandler.DiscountRule
import dev.okano.mahatest.model.Checkout
import org.springframework.stereotype.Service

@Service
class DiscountService(private val discountRuleRules: List<DiscountRule>) {
    fun getDiscount(checkout: Checkout): Long {
        return discountRuleRules.foldRight(0) { discount, acc ->
            acc + discount.getDiscount(checkout)
        }
    }
}