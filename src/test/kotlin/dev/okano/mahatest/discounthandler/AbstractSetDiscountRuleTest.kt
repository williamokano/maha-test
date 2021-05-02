package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class AbstractSetDiscountRuleTest {

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun `should calculate with given constraints`(testCase: TestCase) {
        val underTest = object : AbstractSetDiscountRule() {
            override fun itemSku(): Sku = ITEM_SKU
            override fun discountThreshold(): Int = testCase.threshold
            override fun discountAmount(): Long = testCase.discount
        }

        val checkout = Checkout.of(setOf(Checkout.Item(PRODUCT, testCase.quantity)))

        val result = underTest.getDiscount(checkout)

        assertThat(result).isEqualTo(testCase.expectedDiscount)
    }

    companion object {

        val ITEM_SKU = Sku.of("XPTO_TEST")
        val PRODUCT = Product(ITEM_SKU, "any name", 100)

        @JvmStatic
        fun argumentsProvider() = Stream.of(
            Arguments.of(TestCase(1, 1, 10, 10)),
            Arguments.of(TestCase(1, 2, 10, 0)),
            Arguments.of(TestCase(2, 2, 10, 10)),
            Arguments.of(TestCase(3, 2, 10, 10)),
            Arguments.of(TestCase(4, 2, 10, 20)),

            Arguments.of(TestCase(1, 1, 20, 20)),
            Arguments.of(TestCase(1, 2, 20, 0)),
            Arguments.of(TestCase(2, 2, 20, 20)),
            Arguments.of(TestCase(3, 2, 20, 20)),
            Arguments.of(TestCase(4, 2, 20, 40)),
        )

        data class TestCase(val quantity: Int, val threshold: Int, val discount: Long, val expectedDiscount: Long)
    }
}
