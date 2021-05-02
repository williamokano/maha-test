package dev.okano.mahatest.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Throwable::class)
    fun handleGeneric(throwable: Throwable): ResponseEntity<Any> {
        log.error("Unhandled exception", throwable)
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleProductNotFound(exception: ProductNotFoundException): ResponseEntity<Any> {
        log.error("Failed to find product with SKU ${exception.sku}")
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }
}