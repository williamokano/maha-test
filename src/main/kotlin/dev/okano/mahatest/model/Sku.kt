package dev.okano.mahatest.model

data class Sku(val value: String) {
    companion object {
        fun of(value: String) = Sku(value)
    }
}