package dev.okano.mahatest.rest

import com.fasterxml.jackson.databind.ObjectMapper
import dev.okano.mahatest.converter.SkuConverter
import dev.okano.mahatest.model.Sku
import dev.okano.mahatest.service.CheckoutService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [CheckoutController::class])
class CheckoutControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var checkoutService: CheckoutService

    @MockBean
    private lateinit var skuConverter: SkuConverter

    @Test
    fun `should return calculated value`() {
        whenever(checkoutService.getTotal(any()))
            .thenReturn(10L)
        TEST_CASE_SKUS.forEach { sku -> whenever(skuConverter.invoke(sku.value)).thenReturn(sku) }

        mockMvc.perform(
            post("/checkout")
                .contentType("application/json")
                .content(objectMapper.writeValueAsBytes(listOf("001", "002", "003")))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.price").value(10L))

        verify(checkoutService).getTotal(TEST_CASE_SKUS)
    }

    companion object {
        val TEST_CASE_SKUS = listOf(Sku.of("001"), Sku.of("002"), Sku.of("003"))
    }
}