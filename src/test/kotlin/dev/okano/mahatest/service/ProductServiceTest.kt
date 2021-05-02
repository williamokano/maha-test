package dev.okano.mahatest.service

import dev.okano.mahatest.exception.ProductNotFoundException
import dev.okano.mahatest.model.Sku
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductServiceTest {

    private val underTest = ProductService()

    @ParameterizedTest
    @ValueSource(strings = ["001", "002", "003", "004"])
    fun `should find product`(value: String) {
        underTest.findProduct(Sku.of(value))
    }

    @Test
    fun `should throw product not found exception for non existing product`() {
        assertThrows<ProductNotFoundException> { underTest.findProduct(Sku.of("XPTO")) }
    }
}