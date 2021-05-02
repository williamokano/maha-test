package dev.okano.mahatest.service

import dev.okano.mahatest.exception.ProductNotFoundException
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.stream.Stream

class CheckoutServiceTest {

    private val productService: ProductService = mock()
    private val discountService: DiscountService = mock()

    private val underTest = CheckoutService(productService, discountService)

    @BeforeEach
    fun setUp() {
        whenever(productService.findProduct(ITEM_SKU1))
            .thenReturn(Product(ITEM_SKU1, "Existing", 100))

        whenever(productService.findProduct(ITEM_SKU2))
            .thenReturn(Product(ITEM_SKU2, "Existing 2", 220))

        whenever(productService.findProduct(NON_EXISTING_SKU))
            .thenThrow(ProductNotFoundException(NON_EXISTING_SKU))

        whenever(discountService.getDiscount(any())).thenReturn(30)
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun `should calculate checkout total price`(skus: List<Sku>, expectedTotal: Long) {
        val result = underTest.getTotal(skus)

        assertThat(result).isEqualTo(expectedTotal)
    }

    @Test
    fun `should throw product not found exception when product not found`() {
        assertThatThrownBy { underTest.getTotal(listOf(NON_EXISTING_SKU)) }
            .isInstanceOf(ProductNotFoundException::class.java)
    }

    companion object {
        private val ITEM_SKU1 = Sku.of("EXISTING")
        private val ITEM_SKU2 = Sku.of("ANOTHER_ONE")
        private val NON_EXISTING_SKU = Sku.of("NON_EXISTING_SKU")

        @JvmStatic
        fun argumentsProvider() = Stream.of(
            Arguments.of(listOf(ITEM_SKU1), 70),
            Arguments.of(listOf(ITEM_SKU1, ITEM_SKU1), 170),
            Arguments.of(listOf(ITEM_SKU1, ITEM_SKU1, ITEM_SKU1), 270),
            Arguments.of(listOf(ITEM_SKU1, ITEM_SKU2), 290),
            Arguments.of(listOf(ITEM_SKU1, ITEM_SKU2, ITEM_SKU2), 510),
            Arguments.of(listOf(ITEM_SKU1, ITEM_SKU1, ITEM_SKU2, ITEM_SKU2), 610)
        )
    }
}
