package dev.okano.mahatest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MahaTestApplication

fun main(args: Array<String>) {
    runApplication<MahaTestApplication>(*args)
}
