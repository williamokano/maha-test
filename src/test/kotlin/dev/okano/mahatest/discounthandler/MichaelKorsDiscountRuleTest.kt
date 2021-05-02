package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class MichaelKorsDiscountRuleTest {
    private val underTest = MichaelKorsDiscountRule()

    @DisplayName("should calculate michael kors discount for quantity {1}")
    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun `should calculate rolex discount`(quantity: Int, expectedDiscount: Long) {
        val checkoutItem = Checkout.Item(MICHAEL_KORS, quantity)
        val checkout = Checkout(items = setOf(checkoutItem))
        val response = underTest.getDiscount(checkout)

        Assertions.assertThat(response).isEqualTo(expectedDiscount)
    }

    companion object {

        private val MICHAEL_KORS = Product(Sku.of("002"), "MK", 80)

        @JvmStatic
        fun argumentsProvider() = Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 0),
            Arguments.of(2, 40),
            Arguments.of(3, 40),
            Arguments.of(4, 80),
            Arguments.of(5, 80),
            Arguments.of(6, 120),
            Arguments.of(7, 120),
            Arguments.of(8, 160),
            Arguments.of(9, 160),
            Arguments.of(10, 200),
            Arguments.of(11, 200)
        )
    }
}