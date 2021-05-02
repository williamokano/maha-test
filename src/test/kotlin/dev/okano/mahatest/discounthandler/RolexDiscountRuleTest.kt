package dev.okano.mahatest.discounthandler

import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RolexDiscountRuleTest {

    private val underTest = RolexDiscountRule()

    @DisplayName("should calculate rolex discount for quantity {1}")
    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun `should calculate rolex discount`(quantity: Int, expectedDiscount: Long) {
        val checkoutItem = Checkout.Item(ROLEX, quantity)
        val checkout = Checkout(items = setOf(checkoutItem))
        val response = underTest.getDiscount(checkout)

        assertThat(response).isEqualTo(expectedDiscount)
    }

    companion object {

        private val ROLEX = Product(Sku.of("001"), "Rolex", 100)

        @JvmStatic
        fun argumentsProvider() = Stream.of(
            Arguments.of(0, 0),
            Arguments.of(1, 0),
            Arguments.of(2, 0),
            Arguments.of(3, 100),
            Arguments.of(4, 100),
            Arguments.of(5, 100),
            Arguments.of(6, 200),
            Arguments.of(7, 200),
            Arguments.of(8, 200),
            Arguments.of(9, 300),
            Arguments.of(10, 300),
        )
    }
}