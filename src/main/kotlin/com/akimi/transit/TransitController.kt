package com.akimi.transit

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import jakarta.inject.Inject

@Controller("/api")
class TransitController @Inject constructor(
    private val transitService: TransitService,
    @Value("\${wallet.poc.issuerId}") private val issuerId: String,
) {

    @Post("/createTransitObject")
    fun createTransitObject(
        @QueryValue passengerName: String,
        @QueryValue email: String,
        @QueryValue className: String,
    ): HttpResponse<Any> {
        val transitObject =
            transitService.createObject(issuerId, className, passengerName, email)
        return HttpResponse.ok(transitObject?.let { transitService.generateJWTToken(it) })
    }
}