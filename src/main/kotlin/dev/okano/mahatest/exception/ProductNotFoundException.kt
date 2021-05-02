package dev.okano.mahatest.exception

import dev.okano.mahatest.model.Sku

class ProductNotFoundException(val sku: Sku) : RuntimeException("Product not found with SKU ${sku.value}")