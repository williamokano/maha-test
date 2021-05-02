package dev.okano.mahatest.service

import dev.okano.mahatest.exception.ProductNotFoundException
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.springframework.stereotype.Service

@Service
class ProductService {
    companion object {
        private val catalog by lazy {
            listOf(
                Product(Sku.of("001"), "Rolex", 100),
                Product(Sku.of("002"), "Michael Kors", 80),
                Product(Sku.of("003"), "Swatch", 50),
                Product(Sku.of("004"), "Casio", 30)
            ).associateBy { it.sku }
        }
    }

    fun findProduct(sku: Sku): Product = catalog[sku] ?: throw ProductNotFoundException(sku)
}
