package dev.okano.mahatest.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.stream.Stream


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class CheckoutControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun `should calculate total correctly`(productList: List<String>, expectedPrice: Int) {
        mockMvc.perform(
            post("/checkout")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productList))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.price").value(expectedPrice))
    }

    @Test
    fun `should return bad request when product not found`() {
        mockMvc.perform(
            post("/checkout")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(listOf("XPTO")))
        )
            .andExpect(status().isBadRequest)
    }

    companion object {
        @JvmStatic
        fun argumentsProvider() = Stream.of(
            Arguments.of(listOf("001", "002", "001", "004", "003"), 360),
            Arguments.of(listOf("001"), 100),
            Arguments.of(listOf("002"), 80),
            Arguments.of(listOf("003"), 50),
            Arguments.of(listOf("004"), 30),
            Arguments.of(listOf("001", "001"), 200),
            Arguments.of(listOf("001", "001", "001"), 200),
            Arguments.of(listOf("001", "002", "003", "004"), 260),
            Arguments.of(listOf("001", "002", "003", "004", "002"), 300),
            Arguments.of(listOf("001", "002", "003", "004", "002"), 300),
            Arguments.of(listOf("001"), 100),
            Arguments.of(emptyList<String>(), 0)
        )
    }
}