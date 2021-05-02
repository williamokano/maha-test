package dev.okano.mahatest.service

import dev.okano.mahatest.discounthandler.MichaelKorsDiscountRule
import dev.okano.mahatest.discounthandler.RolexDiscountRule
import dev.okano.mahatest.model.Checkout
import dev.okano.mahatest.model.Product
import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

class DiscountServiceTest {
    private val michaelCorsDiscountRule: MichaelKorsDiscountRule = mock()
    private val rolexDiscountRule: RolexDiscountRule = mock()

    private val underTest = DiscountService(listOf(michaelCorsDiscountRule, rolexDiscountRule))

    private val checkout = Checkout.of(Checkout.Item(Product(Sku.of("any"), "any", 100), 1))

    @BeforeEach
    fun setUp() {
        whenever(michaelCorsDiscountRule.getDiscount(checkout)).thenReturn(5)
        whenever(rolexDiscountRule.getDiscount(checkout)).thenReturn(10)
    }

    @Test
    fun `should calculate discounts for product`() {
        val result = underTest.getDiscount(checkout)

        assertThat(result).isEqualTo(15)

        verify(michaelCorsDiscountRule).getDiscount(checkout)
        verify(rolexDiscountRule).getDiscount(checkout)
        verifyNoMoreInteractions(michaelCorsDiscountRule, rolexDiscountRule)
    }
}