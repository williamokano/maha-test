package dev.okano.mahatest.exception

import dev.okano.mahatest.model.Sku

class ProductNotFoundException(sku: Sku) : RuntimeException("Product not found with SKU ${sku.value}")