package dev.okano.mahatest.service

import dev.okano.mahatest.exception.ProductNotFoundException
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
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
        Assertions.assertThatThrownBy { underTest.findProduct(Sku.of("XPTO")) }
            .isInstanceOf(ProductNotFoundException::class.java)
    }
}
