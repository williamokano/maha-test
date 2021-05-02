package dev.okano.mahatest.converter

import dev.okano.mahatest.model.Sku
import org.springframework.stereotype.Component

@Component
class SkuConverter {
    operator fun invoke(value: String) = Sku(value)
}
