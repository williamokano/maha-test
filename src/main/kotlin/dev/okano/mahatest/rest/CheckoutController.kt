package dev.okano.mahatest.rest

import dev.okano.mahatest.converter.SkuConverter
import dev.okano.mahatest.service.CheckoutService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    private val skuConverter: SkuConverter,
    private val checkoutService: CheckoutService
) {

    @PostMapping
    fun checkout(@RequestBody stringSkus: List<String>): CheckoutResponse {
        val skus = stringSkus.map { skuConverter(it) }
        val total = checkoutService.getTotal(skus)
        return CheckoutResponse(total)
    }
}