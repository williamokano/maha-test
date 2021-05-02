package dev.okano.mahatest.exception

import dev.okano.mahatest.model.Sku
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalExceptionHandlerTest {

    private val underTest = GlobalExceptionHandler()

    @Test
    fun `should handle generic exception and return INTERNAL SERVER ERROR`() {
        val response = underTest.handleGeneric(RuntimeException("Random error"))
        assertThat(response.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @Test
    fun `should handle business exception and return BAD REQUEST`() {
        val response = underTest.handleProductNotFound(ProductNotFoundException(Sku.of("XPTO")))
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}
