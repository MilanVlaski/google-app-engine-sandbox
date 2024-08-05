package com.akimi

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/")
class HelloController {

    @Get("/hello")
    fun hello(): String = "Hello, world."
}