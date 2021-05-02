package dev.okano.mahatest.converter

import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SkuConverterTest {

    private val underTest = SkuConverter()

    @Test
    fun `should convert string to sku`() {
        val result = underTest("001")

        assertThat(result).isEqualTo(Sku("001"))
    }
}
