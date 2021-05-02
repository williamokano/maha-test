package dev.okano.mahatest.service

import dev.okano.mahatest.model.Checkout
import org.springframework.stereotype.Service

@Service
class DiscountService {
    fun getDiscount(checkout: Checkout): Long {
        return 0
    }
}