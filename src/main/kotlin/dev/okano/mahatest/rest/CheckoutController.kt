package dev.okano.mahatest.rest

import dev.okano.mahatest.converter.SkuConverter
import dev.okano.mahatest.service.CheckoutService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/checkout")
class CheckoutController(
    private val skuConverter: SkuConverter,
    private val checkoutService: CheckoutService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun checkout(@RequestBody stringSkus: List<String>): CheckoutResponse {
        val skus = stringSkus.map { skuConverter(it) }
        val total = checkoutService.getTotal(skus)
        return CheckoutResponse(total)
    }
}